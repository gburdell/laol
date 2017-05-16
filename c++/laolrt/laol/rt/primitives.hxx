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
        class Float;
        class Double;

        class Bool : public virtual Laol {
        public:
            explicit Bool(bool val) : m_val(val) {}
            
            //allow copy constructors
            const bool m_val;
        };
        
        struct INumber : public virtual Laol {

            enum EType {
                eInt, eUnsignedInt, eLongInt, eUnsignedLongInt,
                eChar,
                eFloat, eDouble
            };

            virtual EType getType() const = 0;

            LaolObj add(const LaolObj&, const LaolObj& opB) const override;
            LaolObj subtract(const LaolObj&, const LaolObj& opB) const override;
            LaolObj multiply(const LaolObj&, const LaolObj& opB) const override;
            LaolObj divide(const LaolObj&, const LaolObj& opB) const override;
            LaolObj equal(const LaolObj&, const LaolObj& opB) const override;
            LaolObj less(const LaolObj&, const LaolObj& opB) const override;
            LaolObj greater(const LaolObj&, const LaolObj& opB) const override;
        };

        template<typename T>
        class IntBase : public INumber {
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

            /* Integer only operations
             */
            LaolObj left_shift(const LaolObj&, const LaolObj& opB) const override {
                return binOp(opB, [](auto aa, auto bb) {
                    return aa << bb;
                });
            }

            LaolObj right_shift(const LaolObj&, const LaolObj& opB) const override {
                return binOp(opB, [](auto aa, auto bb) {
                    return aa >> bb;
                });
            }

            LaolObj modulus(const LaolObj&, const LaolObj& opB) const override {
                return binOp(opB, [](auto aa, auto bb) {
                    return aa % bb;
                });
            }

            const T m_val;
        };

        template<typename T>
        class FloatBase : public INumber {
        public:

            explicit FloatBase(T val) : m_val(val) {
            }

            const T m_val;
        };

#define DEFINE_INTS(_cls, _prim)                    \
    class _cls : public IntBase<_prim> {                \
    public: explicit _cls(_prim val) : IntBase(val) {}  \
    EType getType() const override {return e##_cls ;}   \
    }

        DEFINE_INTS(Int, int);
        DEFINE_INTS(UnsignedInt, unsigned int);
        DEFINE_INTS(LongInt, long int);
        DEFINE_INTS(UnsignedLongInt, unsigned long int);
        DEFINE_INTS(Char, char);
#undef DEFINE_INTS

#define DEFINE_FLOATS(_cls, _prim)                    \
    class _cls : public FloatBase<_prim> {                \
    public: explicit _cls(_prim val) : FloatBase(val) {}  \
    EType getType() const override {return e##_cls ;}   \
    }

        DEFINE_FLOATS(Float, float);
        DEFINE_FLOATS(Double, double);
#undef DEFINE_FLOATS

        // For 'builtin op LaolObj'
#define BINARY_OP(_op) \
        template<typename T> \
        inline LaolObj operator _op(T a, const LaolObj& b) { \
            return LaolObj(a).operator _op(b); \
        }

        BINARY_OP(+)
        BINARY_OP(-)
        BINARY_OP(*)
        BINARY_OP( /)
        BINARY_OP( %)
        BINARY_OP( <<)
        BINARY_OP(>>)
        BINARY_OP( ==)
        BINARY_OP( !=)
        BINARY_OP(<)
        BINARY_OP(>)

#undef BINARY_OP
    }
}

#endif /* _laol_rt_primitives_hxx_ */

