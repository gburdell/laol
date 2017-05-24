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
#include "laol/rt/exception.hxx"

namespace laol {
    namespace rt {

        using std::to_string;

        Laol::METHOD_BY_NAME IArray::stMethodByName;
        Laol::METHOD_BY_NAME Array::stMethodByName;

        IArray::~IArray() {
        }

        Array::Array() {
        }

        Array::Array(Args v)
        : PTArray<LaolObj>(v) {
        }

        Array::Array(const LaolObj& v)
        : Array(v.isA<Array>() ? v.toType<Array>().m_ar : toV(v)) {
        }

        const Laol::METHOD_BY_NAME&
        IArray::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = join(stMethodByName,
                        METHOD_BY_NAME({
                    {"length", reinterpret_cast<TPMethod> (&IArray::length)},
                    {"empty?", reinterpret_cast<TPMethod> (&IArray::empty_PRED)}
                }));
            }
            return stMethodByName;
        }

        Ref
        IArray::empty_PRED(const LaolObj& self, const LaolObj& args) const {
            return LaolObj(isEmpty());
        }

        Ref
        IArray::length(const LaolObj& self, const LaolObj& args) const {
            return LaolObj(xlength());
        }

        size_t
        IArray::actualIndex(long int ix) const {
            return laol::rt::actualIndex(ix, xlength());
        }

        const Laol::METHOD_BY_NAME&
        Array::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = join(stMethodByName,
                        IArray::getMethodByName(),
                        METHOD_BY_NAME({
                    {"left_shift", reinterpret_cast<TPMethod> (&Array::left_shift)},
                    {"right_shift", reinterpret_cast<TPMethod> (&Array::right_shift)}
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

#ifdef TODO

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
#endif

        // Local iterator over int range

        template<typename FUNC>
        void iterate(const Range& rng, FUNC forEach) {
            auto i = rng.m_begin.toLongInt(), end = rng.m_end.toLongInt();
            const auto incr = (end > i) ? 1 : -1;
            while (true) {
                forEach(i);
                if (i == end) {
                    return;
                }
                i += incr;
            }
        }

        const Array::Vector 
        Array::toVector(const LaolObj& opB) {
            return opB.isA<Array>() ? opB.toType<Array>().m_ar : toV(opB);
        }

        /*
         * opB : scalar or Array of vals...
         */
        Ref
        Array::subscript(const LaolObj&, const LaolObj& opB) const {
            const Vector args = toVector(opB);
            //degenerate case of single index
            if ((1 == args.size()) && args[0].isInt()) {
                return m_ar[actualIndex(args[0].toLongInt())];
            }
            //else, build up ArrayOfRef
            ArrayOfRef *prefs = new ArrayOfRef();
            for (const LaolObj& sub : args) {
                if (sub.isInt()) {
                    *prefs << m_ar[actualIndex(sub.toLongInt())];
                } else if (sub.isA<Range>()) {
                    iterate(sub.toType<Range>(), [this, &prefs](auto i) {
                        //this-> work around gcc 5.1.0 bug
                        *prefs << m_ar[this->actualIndex(i)];
                    });
                } else {
                    ASSERT_NEVER; //todo: error
                }
            }
            return LaolObj(prefs);
        }

        Ref
        ArrayOfRef::subscript(const LaolObj& self, const LaolObj& opB) const {
            const Array::Vector args = Array::toVector(opB);
            if ((1 == args.size()) && args[0].isInt()) {
                return m_ar[actualIndex(args[0].toLongInt())];
            }
            ASSERT_NEVER; //todo
            return self; //todo
        }

    }
}
