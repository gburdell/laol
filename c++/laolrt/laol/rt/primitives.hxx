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


        //NOTE: while we have these discrete primitive types; we do not (yet?!)
        //have proper operator overloads to allow "Int op UnsignedInt"...
        class Int;
        class UnsignedInt;
        class LongInt;
        class UnsignedLongInt;
        class Char;
        class Float;
        class Double;

        class Bool : public virtual Laol {
        public:

            explicit Bool(bool val) : m_val(val) {
            }

            virtual LaolObj negate(const LaolObj&, const LaolObj&) const override {
                return !m_val;
            }

            virtual LaolObj complement(const LaolObj&, const LaolObj&) const override {
                return ~m_val;
            }

            virtual std::ostream& print(std::ostream& os) const override {
                os << (m_val ? "true" : "false");
                return os;
            }

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

            template<typename GETFN>
            static LaolObj getIntVal(const LaolObj& op, const EType type, GETFN rcvr);

            template<typename GETFN>
            static LaolObj getIntVal(const LaolObj& op, GETFN rcvr) {
                return getIntVal(op, op.toType<INumber>().getType(), rcvr);
            }

            template<typename GETFN>
            static LaolObj getVal(const LaolObj& op, const EType type, GETFN rcvr);

            template<typename GETFN>
            static LaolObj getVal(const LaolObj& op, GETFN rcvr) {
                return getVal(op, op.toType<INumber>().getType(), rcvr);
            }

            static int toInt(const LaolObj& v);
            static unsigned int toUnsignedInt(const LaolObj& v);
            static long int toLongInt(const LaolObj& v);
            static unsigned long int toUnsignedLongInt(const LaolObj& v);
            static double toDouble(const LaolObj& v);
            static float toFloat(const LaolObj& v);
        };

        template<typename T>
        class IntBase : public INumber {
        public:

            explicit IntBase(T val) : m_val(val) {
            }

            /* Integer only operations
             */
            virtual LaolObj left_shift(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val << bb;
                });
            }

            virtual LaolObj right_shift(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val >> bb;
                });
            }

            virtual LaolObj modulus(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val % bb;
                });
            }

            virtual LaolObj logical_and(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val && bb;
                });
            }

            virtual LaolObj logical_or(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val || bb;
                });
            }

            virtual LaolObj bitwise_and(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val & bb;
                });
            }

            virtual LaolObj bitwise_or(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val | bb;
                });
            }

            virtual LaolObj bitwise_xor(const LaolObj&, const LaolObj& opB) const override {
                return getIntVal(opB, [this](auto bb) {
                    return m_val ^ bb;
                });
            }

            virtual LaolObj complement(const LaolObj&, const LaolObj&) const override {
                return ~m_val;
            }

            // Generate specializations for Int here for performance

            virtual LaolObj post_increment(const LaolObj& self, const LaolObj&) const override {
                return unconst(this)->m_val++;
            }

            virtual LaolObj post_decrement(const LaolObj& self, const LaolObj&) const override {
                return unconst(this)->m_val--;
            }

            virtual LaolObj pre_increment(const LaolObj& self, const LaolObj&) const override {
                unconst(this)->m_val = m_val + 1;
                return self;
            }

            virtual LaolObj pre_decrement(const LaolObj& self, const LaolObj&) const override {
                unconst(this)->m_val = m_val - 1;
                return self;
            }

            virtual LaolObj add(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val + bb;
                });
            }

            virtual LaolObj subtract(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val - bb;
                });
            }

            virtual LaolObj multiply(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val * bb;
                });
            }

            virtual LaolObj divide(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val / bb;
                });
            }

            virtual LaolObj equal(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val == bb;
                });
            }

            virtual LaolObj less(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val < bb;
                });
            }

            virtual LaolObj greater(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val > bb;
                });
            }

            virtual LaolObj negate(const LaolObj&, const LaolObj&) const override {
                //For an expression e, 
                //the unary expression !e is equivalent to the expression (e == 0)
                return (m_val != 0);
            }

            virtual std::ostream& print(std::ostream& os) const override {
                os << m_val;
                return os;
            }

        private:
            friend class INumber;

            T m_val;
        };

        template<typename T>
        class FloatBase : public INumber {
        public:

            explicit FloatBase(T val) : m_val(val) {
            }

            // Generate specializations for Float here for performance

            virtual LaolObj post_increment(const LaolObj& self, const LaolObj&) const override {
                return unconst(this)->m_val++;
            }

            virtual LaolObj post_decrement(const LaolObj& self, const LaolObj&) const override {
                return unconst(this)->m_val--;
            }

            virtual LaolObj pre_increment(const LaolObj& self, const LaolObj&) const override {
                unconst(this)->m_val = m_val + 1;
                return self;
            }

            virtual LaolObj pre_decrement(const LaolObj& self, const LaolObj&) const override {
                unconst(this)->m_val = m_val - 1;
                return self;
            }

            virtual LaolObj add(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val + bb;
                });
            }

            virtual LaolObj subtract(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val - bb;
                });
            }

            virtual LaolObj multiply(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val * bb;
                });
            }

            virtual LaolObj divide(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val / bb;
                });
            }

            virtual LaolObj equal(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val == bb;
                });
            }

            virtual LaolObj less(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val < bb;
                });
            }

            virtual LaolObj greater(const LaolObj&, const LaolObj& opB) const override {
                return getVal(opB, [this](auto bb) {
                    return m_val > bb;
                });
            }

            virtual LaolObj negate(const LaolObj&, const LaolObj&) const override {
                //For an expression e, 
                //the unary expression !e is equivalent to the expression (e == 0)
                return (m_val != 0);
            }

            virtual std::ostream& print(std::ostream& os) const override {
                os << m_val;
                return os;
            }

        private:
            friend class INumber;

            T m_val;
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

        template<typename GETFN>
        LaolObj
        INumber::getIntVal(const LaolObj& op, const EType type, GETFN rcvr) {
            switch (type) {
                case eInt:
                    return rcvr(op.toType<Int>().m_val);
                case eUnsignedInt:
                    return rcvr(op.toType<UnsignedInt>().m_val);
                case eLongInt:
                    return rcvr(op.toType<LongInt>().m_val);
                case eUnsignedLongInt:
                    return rcvr(op.toType<UnsignedLongInt>().m_val);
                case eChar:
                    return rcvr(op.toType<Char>().m_val);
                default:
                    ASSERT_NEVER;
            }
            return NULLOBJ;
        }

        template<typename GETFN>
        LaolObj
        INumber::getVal(const LaolObj& op, const EType type, GETFN rcvr) {
            switch (type) {
                case eFloat:
                    return rcvr(op.toType<Float>().m_val);
                case eDouble:
                    return rcvr(op.toType<Double>().m_val);
                default:
                    return getIntVal(op, type, rcvr);
            }
            return NULLOBJ;
        }
    }
}

#endif /* _laol_rt_primitives_hxx_ */

