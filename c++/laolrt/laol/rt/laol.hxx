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
 * File:   laol.hxx
 * Author: kpfalzer
 *
 * Created on April 1, 2017, 4:53 PM
 */

#ifndef _laol_rt_laol_hxx_
#define _laol_rt_laol_hxx_

#include <exception>
#include <cmath>
#include <vector>
#include <string>
#include <map>
#include "xyzzy/assert.hxx"
#include "xyzzy/refcnt.hxx"

#define NO_COPY_CONSTRUCTORS(_t)            \
    _t(const _t&) = delete;                 \
    _t& operator=(const _t&) = delete

namespace laol {
    namespace rt {
        using std::vector;
        using std::string;

        string demangleName(const char* n);

        template<typename T>
        inline string getClassName(T p) {
            return demangleName(typeid (p).name());
        }

        inline auto toObjectId(const void* p) {
            return reinterpret_cast<unsigned long int> (p);
        }

        using xyzzy::TRcObj;
        using xyzzy::PTRcObjPtr;

        class Laol;
        typedef PTRcObjPtr<Laol> TRcLaol;

        class LaolObj;
        //Convenient type (for args) so we can pass {v1,v2,...}
        typedef const vector<LaolObj>& Args;

        class LaolObj {
        public:

            explicit LaolObj() : m_type(eNull) {
            }

            // Primitive: LaolRef lhs = val...

            template<typename T>
            LaolObj(T val) {
                set(val);
            }

            LaolObj(TRcLaol* val) {
                set(val);
            }

            LaolObj(const LaolObj& val) {
                set(val);
            }

            const LaolObj& operator=(const LaolObj& rhs) {
                cleanup();
                return set(rhs);
            }

            template<typename T>
            const LaolObj& operator=(T rhs) {
                cleanup();
                return set(rhs);
            }

            bool isNull() const {
                return (eNull == m_type);
            }

            bool asBool() const {
                return (eBool == m_type) && m_dat.u_bool;
            }
            
            // true if int variant
            bool isInt() const;
            // true if float/double variant
            bool isFloat() const;

            bool isObject() const {
                return (ePRc == m_type);
            }

            //NOTE: we're not const: since some usages changes 'this'
            //TODO: add a const version too?
            LaolObj operator<<(const LaolObj& opB);
            LaolObj operator>>(const LaolObj& opB);
            LaolObj operator+(const LaolObj& opB);

            //TPMethod
            typedef LaolObj(Laol::* TPMethod)(TRcLaol* self, Args args);

            //call method
            LaolObj operator()(const string& methodNm, Args args);
            LaolObj operator()(const string& methodNm);

            template<typename T>
            const T& asType() const {
                return dynamic_cast<const T&>(asTPRcLaol()->asT());
            }
            
            virtual ~LaolObj();

        private:

            enum EType {
                ePRc,
                eChar, eBool,
                eInt, eUInt, eLInt, eULInt,
                eFloat, eDouble,
                eNull
            };

            EType m_type;

            union DatType {
                TRcLaol* u_prc;
                char u_char;
                bool u_bool;
                int u_int;
                unsigned int u_uint;
                long int u_lint;
                unsigned long int u_ulint;
                //long long int u_llint;
                //unsigned long long int u_ullint;
                float u_float;
                double u_double;
                //long double u_ldouble;
            } m_dat;

            TRcLaol* asTPRcLaol() const {
                return m_dat.u_prc;
            }

            Laol* asTPLaol() const {
                return asTPRcLaol()->getPtr();
            }

            template<bool DO_ASSERT = true, typename OP>
            LaolObj intApply(OP op) const {
                LaolObj rval;
                switch (m_type) {
                    case eChar: rval = op(m_dat.u_char);
                        break;
                    case eInt: rval = op(m_dat.u_int);
                        break;
                    case eUInt: rval = op(m_dat.u_uint);
                        break;
                    case eLInt: rval = op(m_dat.u_lint);
                        break;
                    case eULInt: rval = op(m_dat.u_ulint);
                        break;
                    default:
                        if (DO_ASSERT) {
                            ASSERT_NEVER;
                        }
                }
                return rval;
            }

            template<bool DO_ASSERT = true, typename OP>
            LaolObj numberApply(OP op) const {
                LaolObj rval;
                if (isInt()) {
                    rval = intApply<false>(op);
                } else {
                    switch (m_type) {
                        case eFloat: rval = op(m_dat.u_float);
                            break;
                        case eDouble: rval = op(m_dat.u_double);
                            break;
                        default:
                            if (DO_ASSERT) {
                                ASSERT_NEVER;
                            }
                    }
                }
                return rval;
            }

            template<typename BINOP>
            LaolObj intBinaryOp(const LaolObj& opB, BINOP binop) {
                LaolObj rval = intApply([opB, binop](auto a) {
                    return opB.intApply([a, binop](auto b) {
                        return binop(a, b);
                    });
                });
                return rval;
            }

            template<typename BINOP>
            LaolObj numberBinaryOp(const LaolObj& opB, BINOP binop) {
                LaolObj rval = numberApply([opB, binop](auto a) {
                    return opB.numberApply([a, binop](auto b) {
                        return binop(a, b);
                    });
                });
                return rval;
            }

            void cleanup();

            template<typename SF>
            const LaolObj& set(EType type, SF setFn) {
                m_type = type;
                setFn();
                return *this;
            }

            const LaolObj& set(Laol* rhs) {
                return set(ePRc, [this, rhs]() {
                    m_dat.u_prc = new TRcLaol(rhs);
                });
            }

            const LaolObj& set(TRcLaol* rhs) {
                return set(ePRc, [this, rhs]() {
                    rhs->incr();
                    m_dat.u_prc = rhs;
                });
            }
            const LaolObj& set(const LaolObj& rhs);

            const LaolObj& set(bool rhs) {
                return set(eBool, [this, rhs]() {
                    m_dat.u_bool = rhs;
                });
            }

            const LaolObj& set(char rhs) {
                return set(eChar, [this, rhs]() {
                    m_dat.u_char = rhs;
                });
            }

            const LaolObj& set(int rhs) {
                return set(eInt, [this, rhs]() {
                    m_dat.u_int = rhs;
                });
            }

            const LaolObj& set(unsigned int rhs) {
                return set(eUInt, [this, rhs]() {
                    m_dat.u_uint = rhs;
                });
            }

            const LaolObj& set(long int rhs) {
                return set(eLInt, [this, rhs]() {
                    m_dat.u_lint = rhs;
                });
            }

            const LaolObj& set(unsigned long int rhs) {
                return set(eULInt, [this, rhs]() {
                    m_dat.u_ulint = rhs;
                });
            }

            const LaolObj& set(double rhs) {
                return set(eDouble, [this, rhs]() {
                    m_dat.u_double = rhs;
                });
            }

            const LaolObj& set(float rhs) {
                return set(eFloat, [this, rhs]() {
                    m_dat.u_float = rhs;
                });
            }

            const LaolObj& set(const char* rhs);

            //Methods to cover primitive types.
            //Note: We dont have a 'self' argument: since
            //not applicable to primitives types.
            LaolObj toString(Args);
            LaolObj objectId(Args);
            
            // For primitive operations (no 'self' here).
            typedef LaolObj(LaolObj::* TPLaolObjMethod)(Args args);
            typedef std::map<string, TPLaolObjMethod> LAOLOBJ_METHOD_BY_NAME;
            static LAOLOBJ_METHOD_BY_NAME stMethodByName;
        };
        
        // Use toBool in 'if (expr)' -> 'if (toBool(expr))' in transpiler (laol -> c++).
        // Note: if 'operator bool()' defined, opens pandora box (of ambiguities)
        // and breaks the more natural primitive conversions: LaolObj(T val)...
        template<typename T>
        inline auto toBool(const T& expr) {
            return expr;
        }
        
        template<>
        inline auto toBool<LaolObj>(const LaolObj& expr) {
            return expr.asBool();
        }

        // Base class for any object

        class Laol : public TRcObj {
        public:

            explicit Laol();

            NO_COPY_CONSTRUCTORS(Laol);

            //operators (mapped to mnemonic): so we can pass in 'self'
            //http://stackoverflow.com/questions/8679089/c-official-operator-names-keywords
            virtual LaolObj left_shift(TRcLaol* self, const LaolObj& opB);
            virtual LaolObj right_shift(TRcLaol* self, const LaolObj& opB);
            virtual LaolObj add(TRcLaol* self, const LaolObj& opB);

            //Map special methods for call-by-method too
            virtual LaolObj toString(TRcLaol*, Args);
            virtual LaolObj objectId(TRcLaol*, Args);

            virtual ~Laol() = 0;

            typedef LaolObj::TPMethod TPMethod;

            virtual TPMethod getFunc(const string& methodNm) const;

            static string getClassName(const TRcLaol* p);

        protected:
            typedef std::map<string, TPMethod> METHOD_BY_NAME;
            static TPMethod getFunc(const METHOD_BY_NAME& methodByName, const string& methodNm);

        private:

            auto objectId() const {
                return toObjectId(this);
            }

            static METHOD_BY_NAME stMethodByName;
        };

        class Exception : public std::exception {
        public:
            explicit Exception(const string& reason);

            //allow copy constructors

            virtual const char* what() const noexcept;

        private:
            string m_reason;
        };

        class NoMethodException : public Exception {
        public:

            explicit NoMethodException()
            : Exception(REASON) {
            }

            explicit NoMethodException(const string& reason)
            : Exception(reason) {
            }

            explicit NoMethodException(const std::type_info &obj, const string& type, const string& op);

            explicit NoMethodException(const std::type_info &obj, const string& op)
            : NoMethodException(obj, "method", op) {
            }

            //allow copy constructors

        private:
            static const string REASON;
        };

        class NoOperatorException : public NoMethodException {
        public:

            explicit NoOperatorException(const std::type_info &obj, const string& op)
            : NoMethodException(obj, "operator", op) {
            }

            //allow copy constructors
        };

        class InvalidTypeException : public Exception {
        public:

            explicit InvalidTypeException(const std::type_info& found, const string& expected);

            explicit InvalidTypeException(const std::type_info& found, const std::type_info& expected) : InvalidTypeException(found, demangleName(expected.name())) {
            }

            //allow copy constructors
        };

    }
}

#endif /* _laol_rt_laol_hxx_ */

