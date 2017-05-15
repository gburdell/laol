/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

#include <algorithm>
#include <sstream>
#include "laol/rt/array.hxx"
#include "laol/rt/range.hxx"
#include "laol/rt/string.hxx"


namespace laol {
    namespace rt {

        //static

        Laol::METHOD_BY_NAME Array::stMethodByName;

        Array::Array() {
        }

        Array::~Array() {
        }

        const Laol::METHOD_BY_NAME&
        Array::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = join(
                        stMethodByName,
                        Laol::getMethodByName(), METHOD_BY_NAME({
                    {"length", static_cast<TPMethod> (&Array::length)},
                    {"empty?", static_cast<TPMethod> (&Array::empty_PRED)},
                    {"reverse", static_cast<TPMethod> (&Array::reverse)},
                    {"reverse!", static_cast<TPMethod> (&Array::reverse_SELF)}
                }));
            }
            return stMethodByName;
        }

        LaolObj
        Array::left_shift(const LaolObj& self, const LaolObj& opB) const {
            unconst(this)->m_ar.push_back(opB);

            return self;
        }

        LaolObj
        Array::right_shift(const LaolObj& self, const LaolObj& opB) const {
            unconst(this)->m_ar.insert(m_ar.begin(), opB);

            return self;
        }

        LaolObj
        Array::equal(const LaolObj& self, const LaolObj& opB) const {
            LaolObj isEqual = false;
            if (opB.isA<Array>()) {
                const Vector& other = opB.toType<Array>().m_ar;
                //we cannot use std::vector== since requires 'bool LaolObj==LaolObj'
                //and we have 'LaolObj a==b' (without bool cast/operator).
                //adding bool opens pandora box for ambiguity etc...
                //TODO: could use LaolObjKey: but requires some thorough rewrite.
                const auto N = m_ar.size();
                if (N == other.size()) {
                    for (auto i = 0; i < N; i++) {
                        if ((m_ar[i] != other[i]).toBool()) {
                            return false;
                        }
                    }
                    isEqual = true;
                }
            }
            return isEqual;
        }

        LaolObj
        Array::toString(const LaolObj& self, const LaolObj&) const {
            std::ostringstream oss;
            oss << "[";
            bool doComma = false;
            for (const LaolObj& ele : m_ar) {
                if (doComma) {
                    oss << ", ";
                }
                oss << ele.toQString();
                doComma = true;
            }
            oss << "]";

            return new String(oss.str());
        }

        // Local iterator over int range

        template<typename FUNC>
        void iterate(const Range& rng, FUNC forEach) {
            auto i = rng.m_begin.toLInt(), end = rng.m_end.toLInt();
            const auto incr = (end > i) ? 1 : -1;
            while (true) {
                forEach(i);
                if (i == end) {

                    return;
                }
                i += incr;
            }
        }

        /*
         * opB : scalar or Array of vals...
         */
        LaolObj
        Array::subscript(const LaolObj&, const LaolObj& opB) const {
            const Vector& args = opB.isA<Array>() ? opB.toType<Array>().m_ar : toV(opB);
            Vector val;
            for (const LaolObj& sub : args) {
                if (sub.isInt()) {
                    val.push_back(m_ar[actualIndex(sub.toLInt())].ptr());
                } else if (sub.isObject() && sub.isA<Range>()) {
                    iterate(sub.toType<Range>(), [this, &val](auto i) {
                        //this-> work around gcc 5.1.0 bug
                        //we push *LaolObj
                        val.push_back(m_ar[this->actualIndex(i)].ptr());
                    });
                } else {
                    ASSERT_NEVER; //todo: error
                }
            }
            return (1 < val.size()) ? new Array(val) : val[0];
        }

        LaolObj
        Array::empty_PRED(const LaolObj&, const LaolObj&) const {

            return m_ar.empty();
        }

        LaolObj
        Array::reverse(const LaolObj&, const LaolObj&) const {
            auto p = new Array(m_ar);
            std::reverse(std::begin(p->m_ar), std::end(p->m_ar));

            return p;
        }

        LaolObj
        Array::reverse_SELF(const LaolObj& self, const LaolObj&) const {
            std::reverse(std::begin(unconst(this)->m_ar), std::end(unconst(this)->m_ar));

            return self;
        }

        LaolObj
        Array::length(const LaolObj&, const LaolObj&) const {

            return length();
        }

        /*
        LaolObj
        Array::subscript_assign(const LaolObj& self, const LaolObj& args) const {
            ASSERT_TRUE(args.isA<Array>());
            const Vector& vargs = args.toType<Array>().m_ar;
            if (2 != vargs.size()) {
                throw ArityException(vargs.size(), "2");
            }
            LaolObj rhs = vargs[1];
            //make index into array for easier walk
            const Vector& index = vargs[0].isA<Array>() ? vargs[0].toType<Array>().m_ar : toV(vargs[0]);
            //flatten
            vector<size_t> actualIndices;
            for (const LaolObj& sub : index) {
                if (sub.isInt()) {
                    actualIndices.push_back(actualIndex(sub.toLInt()));
                } else if (sub.isObject() && sub.isA<Range>()) {
                    iterate(sub.toType<Range>(), [this, &actualIndices](auto i) {
                        //this-> work around gcc 5.1.0 bug
                        actualIndices.push_back(this->actualIndex(i));
                    });
                } else {
                    ASSERT_NEVER; //todo: error
                }
            }
            Array* ucthis = unconst(this); //unconst
            const auto N = actualIndices.size();
            if (1 == N) {
                ucthis->m_ar[actualIndices[0]] = rhs;
            } else {
                const Vector& rhsVals = rhs.toType<Array>().m_ar;
                ASSERT_TRUE(N == rhsVals.size());
                for (auto i = 0; i < N; i++) {
                    ucthis->m_ar[actualIndices[i]] = rhsVals[i];
                }
            }
            return self;
        }
         */
    }
}
