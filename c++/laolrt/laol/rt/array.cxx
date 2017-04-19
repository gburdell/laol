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

namespace laol {
    namespace rt {

        //static
        std::map<string, Array::TPMethod> Array::stMethodByName = {
            {"empty?", static_cast<TPMethod> (&Array::empty_PRED)},
            {"reverse", static_cast<TPMethod> (&Array::reverse)},
            {"reverse!", static_cast<TPMethod> (&Array::reverse_SELF)}
        };

        Array::Array() {
        }

        Array::~Array() {
        }

        Laol::TPMethod
        Array::getFunc(const string& methodNm) const {
            auto search = stMethodByName.find(methodNm);
            auto rval = (search != stMethodByName.end()) ? search->second : nullptr;
            return rval;
        }

        TRcLaol*
        Array::left_shift(TRcLaol* self, const LaolObj& rhs) {
            m_ar.push_back(rhs);
            return self;
        }

        LaolObj
        Array::empty_PRED(TRcLaol* self, Args args) {
            return m_ar.empty();
        }

        LaolObj
        Array::reverse(TRcLaol* self, Args args) {
            auto p = new Array(m_ar);
            std::reverse(std::begin(p->m_ar), std::end(p->m_ar));
            return p;
        }

        LaolObj 
        Array::reverse_SELF(TRcLaol* self, Args args) {
            std::reverse(std::begin(m_ar), std::end(m_ar));
            return self;
        }

    }
}
