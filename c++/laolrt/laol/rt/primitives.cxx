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

        template<typename T, typename U>
        U* pVal(const LaolObj& opx) {
            return const_cast<U*> (&(opx.toType<T>().m_val));
        }

        template<typename GETOP>
        static LaolObj getOpx(const LaolObj& opx, GETOP rcvr) {
            static LaolObj UNUSED;
            switch (opx.toType<INumber>().getType()) {
                case INumber::eInt:
                    return rcvr(pVal<Int, int>(opx));
                case INumber::eUnsignedInt:
                    return rcvr(pVal<UnsignedInt, unsigned int>(opx));
                case INumber::eLongInt:
                    return rcvr(pVal<LongInt, long int>(opx));
                case INumber::eUnsignedLongInt:
                    return rcvr(pVal<UnsignedLongInt, unsigned long int>(opx));
                case INumber::eChar:
                    return rcvr(pVal<Char, char>(opx));
                case INumber::eFloat:
                    return rcvr(pVal<Float, float>(opx));
                case INumber::eDouble:
                    return rcvr(pVal<Double, double>(opx));
                default:
                    ASSERT_NEVER;
            }
            return UNUSED;
        }

        template<typename BINOP>
        static LaolObj binOp(const LaolObj& self, const LaolObj& opB, BINOP binop) {
            return getOpx(self, [opB, binop](auto a) {
                return getOpx(opB, [a, binop](auto b) {
                    return binop(*a, *b);
                });
            });
        }

        unsigned int INumber::toUnsignedInt(const LaolObj& v) {
            switch (v.toType<INumber>().getType()) {
                case eChar: case eUnsignedInt:
                    unsigned int rval;
                    getOpx(v, [&rval](auto x) {
                        rval = (unsigned int) *x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        int INumber::toInt(const LaolObj& v) {
            switch (v.toType<INumber>().getType()) {
                case eChar: case eInt:
                    int rval;
                    getOpx(v, [&rval](auto x) {
                        rval = (int) *x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        long int INumber::toLongInt(const LaolObj& v) {
            switch (v.toType<INumber>().getType()) {
                case eChar: case eInt: case eUnsignedInt: case eLongInt:
                    long int rval;
                    getOpx(v, [&rval](auto x) {
                        rval = (long int) *x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        unsigned long int INumber::toUnsignedLongInt(const LaolObj& v) {
            switch (v.toType<INumber>().getType()) {
                case eUnsignedLongInt:
                    unsigned long int rval;
                    getOpx(v, [&rval](auto x) {
                        rval = (unsigned long int) *x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        double INumber::toDouble(const LaolObj& v) {
            double rval;
            getOpx(v, [&rval](auto x) {
                rval = *x;
                return NULLOBJ; //unused
            });
            return rval;
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
        INumber::equal(const LaolObj& self, const LaolObj& opB) const {
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

        LaolObj
        INumber::negate(const LaolObj& self, const LaolObj&) const {
            return getOpx(self, [](auto a) {
                //For an expression e, 
                //the unary expression !e is equivalent to the expression (e == 0)
                return (a == 0);
            });
        }

        LaolObj
        INumber::post_decrement(const LaolObj& self, const LaolObj&) const {
            return getOpx(self, [](auto a) {
                auto now = *a;
                *a -= 1;
                return now;
            });
        }

        LaolObj
        INumber::post_increment(const LaolObj& self, const LaolObj&) const {
            return getOpx(self, [](auto a) {
                auto now = *a;
                *a += 1;
                return now;
            });
        }

        LaolObj
        INumber::pre_decrement(const LaolObj& self, const LaolObj&) const {
            return getOpx(self, [](auto a) {
                return *a -= 1;
            });
        }

        LaolObj
        INumber::pre_increment(const LaolObj& self, const LaolObj&) const {
            return getOpx(self, [](auto a) {
                return *a += 1;
            });
        }

    }
}
