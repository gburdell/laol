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

#include "laol/rt/range.hxx"

namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME Range::stMethodByName = {
            //todo
        };

        bool
        Range::check(Args args) {
            if ((1 > args.size()) && (2 < args.size())) {
                throw ArityException(args.size(), "1 or 2");
            }
            return true;
        }

        Range::Range(Args args)
        : m_precondition(check(args))
        , m_lo(args[0]), m_hi(args[(1 == args.size()) ? 0 : 1]) {
            ASSERT_TRUE(toBool(lo() <= hi())); //even works for -3..-1 (from end)
        }

        Laol::TPMethod
        Range::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);
        }

    }
}