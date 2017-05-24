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

#include "laol/rt/lambda.hxx"

namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME Lambda::stMethodByName;

        Lambda::Lambda(FUNC fn) : m_fn(fn) {
        }

        Ref
        Lambda::call(const LaolObj& self, const LaolObj& args) const {
            m_fn(args);
            return self;
        }

        const Laol::METHOD_BY_NAME& Lambda::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(stMethodByName, 
				METHOD_BY_NAME({
                    {"call", static_cast<TPMethod> (&Lambda::call)},
                }));
            }
            return stMethodByName;
        }

        Lambda::~Lambda() {

        }

    }
}
