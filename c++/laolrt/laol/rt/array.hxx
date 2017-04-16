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

namespace laol {
    namespace rt {

        class Array : public Laol {
        public:

            explicit Array();

            NO_COPY_CONSTRUCTORS(Array);

            virtual TRcLaol* left_shift(TRcLaol* self, const LaolRef& rhs) override;

            virtual ~Array();

        private:
            std::vector<LaolRef> m_ar;
        };

    }
}

#endif /* _laol_rt_array_hxx_ */

