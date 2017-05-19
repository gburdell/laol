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
 * File:   range.hxx
 * Author: kpfalzer
 *
 * Created on April 25, 2017, 11:19 AM
 */

#ifndef _laol_rt_range_hxx_
#define _laol_rt_range_hxx_

#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {

        /*
         * Bounded, inclusive range: [begin..end].
         * No restriction on domain or order: begin>end or begin<end.
         */
        class Range : public Laol {
        public:
            Range(Args args);

            virtual ~Range() {
            }

            const LaolObj m_begin, m_end;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            const bool m_precondition;

            static bool check(Args args);

            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_range_hxx_ */

