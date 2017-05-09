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

#include "laol/rt/iterator.hxx"

namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME Iterator::stMethodByName;

        const Laol::METHOD_BY_NAME& Iterator::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(
						stMethodByName, 
                        Laol::getMethodByName(),METHOD_BY_NAME({
                    {"next?", static_cast<TPMethod> (&Iterator::next_PRED)},
                    {"next", static_cast<TPMethod> (&Iterator::next)}

                }));
            }
            return stMethodByName;
        }

        LaolObj
        Iterator::next(const LaolObj&, const LaolObj&) const {
            return unconst(this)->next();
        }

        LaolObj
        Iterator::next_PRED(const LaolObj&, const LaolObj&) const {
            return hasNext();
        }

    }
}
