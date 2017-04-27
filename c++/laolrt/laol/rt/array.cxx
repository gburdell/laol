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
#include "laol/rt/array.hxx"
#include "laol/rt/range.hxx"

namespace laol {
    namespace rt {

        //static
        Laol::METHOD_BY_NAME Array::stMethodByName = {
            {"length", static_cast<TPMethod> (&Array::length)},
            {"empty?", static_cast<TPMethod> (&Array::empty_PRED)},
            {"reverse", static_cast<TPMethod> (&Array::reverse)},
            {"reverse!", static_cast<TPMethod> (&Array::reverse_SELF)},
            {"subscript", static_cast<TPMethod> (&Array::subscript)}
        };

        Array::Array() {
        }

        Array::~Array() {
        }

        Laol::TPMethod
        Array::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);
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

        /*
         * args : scalar or Array of vals...
         */
        LaolObj
        Array::subscript(const LaolObj& self, const LaolObj& args) const {
            Vector here;
            for (const LaolObj& sub : args) {
                if (sub.isInt()) {
                    here.push_back(m_ar[actualIndex(sub.toLInt())]);
                } else if (sub.isObject() && sub.isA<Range>()) {
                    bool debug = true;
                } else {
                    ASSERT_NEVER; //todo: error
                }
            }
            return new Array(here);
        }

        LaolObj
        Array::empty_PRED(const LaolObj&, Args) const {
            return m_ar.empty();
        }

        LaolObj
        Array::reverse(const LaolObj&, Args) const {
            auto p = new Array(m_ar);
            std::reverse(std::begin(p->m_ar), std::end(p->m_ar));
            return p;
        }

        LaolObj
        Array::reverse_SELF(const LaolObj& self, Args) const {
            std::reverse(std::begin(unconst(this)->m_ar), std::end(unconst(this)->m_ar));
            return self;
        }

        LaolObj 
        Array::length(const LaolObj&, Args) const {
            return length();
        }


        LaolObj Array::subscript_assign(const LaolObj& self, Args args) const {
            return self;
        }
    }
}
