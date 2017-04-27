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

/* 
 * File:   array.hxx
 * Author: kpfalzer
 *
 * Created on April 16, 2017, 3:55 PM
 */

#ifndef _laol_rt_array_hxx_
#define _laol_rt_array_hxx_

#include <vector>
#include "laol/rt/laol.hxx"
#include "laol/rt/exception.hxx"    

namespace laol {
    namespace rt {

        class Array : public Laol {
        public:

            explicit Array();

            Array(Args v) : m_ar(v) {
            }

            Array& operator=(const Array& r) = delete;

            //operators
            virtual LaolObj left_shift(const LaolObj& self, const LaolObj& opB) const override;
            virtual LaolObj right_shift(const LaolObj& self, const LaolObj& opB) const override;
            virtual LaolObj subscript(const LaolObj& self, const LaolObj& opB) const override;

            //unique methods
            virtual LaolObj empty_PRED(const LaolObj& self, Args args) const;
            virtual LaolObj reverse(const LaolObj& self, Args args) const;
            virtual LaolObj reverse_SELF(const LaolObj& self, Args args) const;
            virtual LaolObj length(const LaolObj& self, Args args) const;
            virtual LaolObj subscript_assign(const LaolObj& self, Args args) const;

            Laol::TPMethod getFunc(const string& methodNm) const override;

            virtual ~Array();

        private:
            size_t length() const override {
                return m_ar.size();
            }

            typedef std::vector<LaolObj> Vector;

            Vector m_ar;
            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_array_hxx_ */

