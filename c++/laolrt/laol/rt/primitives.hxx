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
 * File:   primitives.hxx
 * Author: kpfalzer
 *
 * Created on May 15, 2017, 9:50 AM
 */

#ifndef _laol_rt_primitives_hxx_
#define _laol_rt_primitives_hxx_

#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {


        class Int;
        class UnsignedInt;
        class LongInt;
        class UnsignedLongInt;
        class Char;

        struct INumber {

            enum EType {
                eInt, eUnsignedInt, eLongInt, eUnsignedLongInt,
                eChar,
                eFloat, eDouble
            };

            virtual EType getType() const = 0;
        };

        template<typename T>
        class IntBase : public virtual Laol, public INumber {
        public:

            explicit IntBase(T val) : m_val(val) {
            }

            template<typename GETFN>
            LaolObj get(GETFN rcvr) const {
                return rcvr(m_val);
            }

            template<typename TB, typename BINOP>
            LaolObj binOp(const TB& opB, BINOP binop) const {
                LaolObj rval = opB.get([this, binop](auto bb) {
                    return binop(m_val, bb);
                });
                return rval;
            }

            template<typename BINOP>
            LaolObj binOp(const LaolObj& opB, BINOP binop) const {
                switch (opB.toType<INumber>().getType()) {
                    case eInt:
                        return binOp(opB.toType<Int>(), binop);
                    case eUnsignedInt:
                        return binOp(opB.toType<UnsignedInt>(), binop);
                    case eLongInt:
                        return binOp(opB.toType<LongInt>(), binop);
                    case eUnsignedLongInt:
                        return binOp(opB.toType<UnsignedLongInt>(), binop);
                    case eChar:
                        return binOp(opB.toType<Char>(), binop);
                    default:
                        ASSERT_NEVER;
                }
                return NULLOBJ;
            }

            LaolObj left_shift(const LaolObj&, const LaolObj& opB) const override {
                return binOp(opB, [](auto aa, auto bb) {
                    return aa << bb;
                });
            }

        private:
            T m_val;
        };

#define DEFINE_INT_TYPE(_cls, _prim)                    \
    class _cls : public IntBase<_prim> {                \
    public: explicit _cls(_prim val) : IntBase(val) {}  \
    EType getType() const override {return e##_cls ;}   \
    }

        DEFINE_INT_TYPE(Int, int);
        DEFINE_INT_TYPE(UnsignedInt, unsigned int);
        DEFINE_INT_TYPE(LongInt, long int);
        DEFINE_INT_TYPE(UnsignedLongInt, unsigned long int);
        DEFINE_INT_TYPE(Char, char);

#undef DEFINE_INT_TYPE
    }
}

#endif /* _laol_rt_primitives_hxx_ */

