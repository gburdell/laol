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

#include "laol/rt/primitives.hxx"

namespace laol {
    namespace rt {

        template<typename GETOP>
        static LaolObj getOpx(const LaolObj& opx, GETOP rcvr) {
            static LaolObj UNUSED;
            switch (opx.toType<INumber>().getType()) {
                case INumber::eInt:
                    return rcvr(opx.toType<Int>().m_val);
                case INumber::eUnsignedInt:
                    return rcvr(opx.toType<UnsignedInt>().m_val);
                case INumber::eLongInt:
                    return rcvr(opx.toType<LongInt>().m_val);
                case INumber::eUnsignedLongInt:
                    return rcvr(opx.toType<UnsignedLongInt>().m_val);
                case INumber::eChar:
                    return rcvr(opx.toType<Char>().m_val);
                case INumber::eFloat:
                    return rcvr(opx.toType<Float>().m_val);
                case INumber::eDouble:
                    return rcvr(opx.toType<Double>().m_val);
                default:
                    ASSERT_NEVER;
            }
            return UNUSED;
        }
        
        template<typename BINOP>
        static LaolObj binOp(const LaolObj& self, const LaolObj& opB, BINOP binop) {
            return getOpx(self, [opB, binop](auto a) {
                return getOpx(opB, [a, binop](auto b) {
                    return binop(a, b);
                });
            });
        }
        
        LaolObj
        INumber::add(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a + b;
            });
        }

        LaolObj
        INumber::subtract(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a - b;
            });
        }
        
        LaolObj
        INumber::divide(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a / b;
            });
        }

        LaolObj
        INumber::multiply(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a * b;
            });
        }

        LaolObj
        INumber::equal(const LaolObj& self , const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a == b;
            });
        }

        LaolObj
        INumber::greater(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a > b;
            });
        }

        LaolObj
        INumber::less(const LaolObj& self, const LaolObj& opB) const {
            return binOp(self, opB, [](auto a, auto b) {
                return a < b;
            });
        }

    }
}
