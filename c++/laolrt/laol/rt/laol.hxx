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
#include "xyzzy/refcnt.hxx"

#define NO_COPY_CONSTRUCTORS(_t)            \
    _t(const _t&) = delete;                 \
    _t& operator=(const _t&) = delete

namespace laol {
    namespace rt {
        using std::string;
        
        string demangleName(const char* n);

        template<typename T>
        inline string getClassName(T p) {
            return demangleName(typeid (p).name());
        }
        
        using xyzzy::TRcObj;
        using xyzzy::PTRcObjPtr;

        class Laol;
        typedef PTRcObjPtr<Laol> TRcLaol;

        class LaolRef {
        public:

            explicit LaolRef() : m_type(eNull) {
            }

            // Primitive: LaolRef lhs = val...

            template<typename T>
            LaolRef(T val) {
                set(val);
            }

            // LaolRef lhs = new Type(...)

            LaolRef(Laol* val) {
                set(val);
            }

            LaolRef(TRcLaol* val) {
                set(val);
            }

            // LaolRef rhs = ...; LaolRef lhs = rhs;

            LaolRef(const LaolRef& val) {
                set(val);
            }

            const LaolRef& operator=(const LaolRef& rhs) {
                cleanup();
                return set(rhs);
            }

            template<typename T>
            const LaolRef& operator=(T rhs) {
                cleanup();
                return set(rhs);
            }

            bool isNull() const {
                return (eNull == m_type);
            }

            //NOTE: we're not const: since Array usage changes 'this'
            //TODO: add a const version too?
            LaolRef operator<<(const LaolRef& rhs);

            virtual ~LaolRef();

        private:

            enum EType {
                ePrc,
                ePstring,
                eChar, eBool, eInt,
                eFloat, eDouble,
                eNull
            };

            EType m_type;

            union {
                TRcLaol* u_prc;
                string* u_pstring;
                char u_char;
                bool u_bool;
                int u_int;
                //todo: unsigned int u_uint;
                //long int u_lint;
                //unsigned long int u_ulint;
                //long long int u_llint;
                //unsigned long long int u_ullint;
                float u_float;
                double u_double;
                //long double u_ldouble;
            } m_dat;

            TRcLaol* asTPRcLaol() {
                return m_dat.u_prc;
            }

            void cleanup();

            template<typename SF>
            const LaolRef& set(EType type, SF setFn) {
                m_type = type;
                setFn();
                return *this;
            }

            const LaolRef& set(Laol* rhs) {
                return set(ePrc, [this, rhs]() {
                    m_dat.u_prc = new TRcLaol(rhs);
                });
            }

            const LaolRef& set(TRcLaol* rhs) {
                return set(ePrc, [this, rhs]() {
                    rhs->incr();
                    m_dat.u_prc = rhs;
                });
            }
            const LaolRef& set(const LaolRef& rhs);

            const LaolRef& set(bool rhs) {
                return set(eBool, [this, rhs]() {
                    m_dat.u_bool = rhs;
                });
            }

            const LaolRef& set(char rhs) {
                return set(eChar, [this, rhs]() {
                    m_dat.u_char = rhs;
                });
            }

            const LaolRef& set(int rhs) {
                return set(eInt, [this, rhs]() {
                    m_dat.u_int = rhs;
                });
            }

            const LaolRef& set(double rhs) {
                return set(eDouble, [this, rhs]() {
                    m_dat.u_double = rhs;
                });
            }

            const LaolRef& set(float rhs) {
                return set(eFloat, [this, rhs]() {
                    m_dat.u_float = rhs;
                });
            }

            const LaolRef& set(const char* rhs) {
                return set(ePstring, [this, rhs]() {
                    m_dat.u_pstring = new string(rhs);
                });
            }

        };

        class Laol : public TRcObj {
        public:

            explicit Laol();

            NO_COPY_CONSTRUCTORS(Laol);

            //http://stackoverflow.com/questions/8679089/c-official-operator-names-keywords
            virtual TRcLaol* left_shift(TRcLaol* self, const LaolRef& rhs);

            virtual ~Laol() = 0;

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

