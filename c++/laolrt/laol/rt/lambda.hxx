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
 * File:   lambda.hxx
 * Author: kpfalzer
 *
 * Created on May 3, 2017, 1:13 PM
 */

#ifndef _laol_rt_lambda_hxx_
#define _laol_rt_lambda_hxx_

#include <functional>
#include "laol.hxx"

namespace laol {
    namespace rt {

        class Lambda : public Laol {
        public:
            typedef std::function<void(const LaolObj&) > FUNC;

            explicit Lambda(FUNC fn);

            virtual LaolObj call(const LaolObj& self, const LaolObj& args) const;

            NO_COPY_CONSTRUCTORS(Lambda);

            virtual ~Lambda();

		protected:
			virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            const FUNC m_fn;
            static METHOD_BY_NAME stMethodByName;
        };
    }
}

#endif /* _laol_rt_lambda_hxx_ */

