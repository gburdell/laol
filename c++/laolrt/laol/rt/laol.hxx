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

#include <string>
#include <vector>
#include <map>
#include <cmath>
#include <exception>
#include <typeinfo>
#include "xyzzy/refcnt.hxx"
#include "xyzzy/array.hxx"

#define NO_COPY_CONSTRUCTORS(_t)            \
    _t(const _t&) = delete;                 \
    _t& operator=(const _t&) = delete

namespace laol {
    namespace rt {
        using xyzzy::TRcObj;
        using xyzzy::PTRcObjPtr;
        using xyzzy::PTRcArray;
        using std::string;
        using std::round;

        static string demangleName(const std::type_info& ti);

        class Exception : public std::exception {
        public:

            explicit Exception(const string* reason) : m_reason(reason) {
            }

            //allow default copy constructors 
            
            const char* what() const override {
                return "Exception " + m_reason;
            }

        private:
            string m_reason;
        };

        class NoMethodException : public Exception {
        public:
            explicit NoMethodException(const std::type_info& obj, const string& method);

            //allow default copy constructors 
            
        };
        
        class Laol;
        typedef PTRcObjPtr<Laol> TRcLaol;

        class Laol : public TRcObj {
        public:

            explicit Laol() {
            }

            NO_COPY_CONSTRUCTORS(Laol);

            virtual ~Laol() = 0;

            //todo: ~ ! = += -= *= /= %= ˆ= &= |= >>= <<= ++ -- , ->* -> ( ) [ ]

            virtual TRcLaol operator+(const TRcLaol& b) const;
            virtual TRcLaol operator-(const TRcLaol& b) const;
            virtual TRcLaol operator*(const TRcLaol& b) const;
            virtual TRcLaol operator/(const TRcLaol& b) const;
            virtual TRcLaol operator%(const TRcLaol& b) const;
            virtual TRcLaol operator^(const TRcLaol& b) const;
            virtual TRcLaol operator&(const TRcLaol& b) const;
            virtual TRcLaol operator|(const TRcLaol& b) const;
            virtual TRcLaol operator<(const TRcLaol& b) const;
            virtual TRcLaol operator>(const TRcLaol& b) const;
            virtual TRcLaol operator<<(const TRcLaol& b) const;
            virtual TRcLaol operator>>(const TRcLaol& b) const;
            virtual TRcLaol operator==(const TRcLaol& b) const;
            virtual TRcLaol operator!=(const TRcLaol& b) const;
            virtual TRcLaol operator<=(const TRcLaol& b) const;
            virtual TRcLaol operator>=(const TRcLaol& b) const;
            virtual TRcLaol operator&&(const TRcLaol& b) const;
            virtual TRcLaol operator||(const TRcLaol& b) const;

            //typedef is 'TPFunc'
            typedef TRcLaol(Laol::* TPFunc)(const TRcLaol& args);

            TRcLaol operator()(const string& methodNm, const TRcLaol& args);

        protected:
            virtual TPFunc getFunc(const string& methodNm) const;
        };

        inline TRcLaol operator+(const TRcLaol &a, const TRcLaol &b) {
            return a->operator+(b);
        }

        inline TRcLaol operator-(const TRcLaol &a, const TRcLaol &b) {
            return a->operator-(b);
        }

        inline TRcLaol operator*(const TRcLaol &a, const TRcLaol &b) {
            return a->operator*(b);
        }

        inline TRcLaol operator/(const TRcLaol &a, const TRcLaol &b) {
            return a->operator/(b);
        }

        inline TRcLaol operator%(const TRcLaol &a, const TRcLaol &b) {
            return a->operator%(b);
        }

        inline TRcLaol operator^(const TRcLaol &a, const TRcLaol &b) {
            return a->operator^(b);
        }

        inline TRcLaol operator&(const TRcLaol &a, const TRcLaol &b) {
            return a->operator&(b);
        }

        inline TRcLaol operator|(const TRcLaol &a, const TRcLaol &b) {
            return a->operator|(b);
        }

        inline TRcLaol operator<(const TRcLaol &a, const TRcLaol &b) {
            return a->operator<(b);
        }

        inline TRcLaol operator>(const TRcLaol &a, const TRcLaol &b) {
            return a->operator>(b);
        }

        inline TRcLaol operator<<(const TRcLaol &a, const TRcLaol &b) {
            return a->operator<<(b);
        }

        inline TRcLaol operator>>(const TRcLaol &a, const TRcLaol &b) {
            return a->operator>>(b);
        }

        inline TRcLaol operator==(const TRcLaol &a, const TRcLaol &b) {
            return a->operator==(b);
        }

        inline TRcLaol operator!=(const TRcLaol &a, const TRcLaol &b) {
            return a->operator!=(b);
        }

        inline TRcLaol operator<=(const TRcLaol &a, const TRcLaol &b) {
            return a->operator<=(b);
        }

        inline TRcLaol operator>=(const TRcLaol &a, const TRcLaol &b) {
            return a->operator>=(b);
        }

        inline TRcLaol operator&&(const TRcLaol &a, const TRcLaol &b) {
            return a->operator&&(b);
        }

        inline TRcLaol operator||(const TRcLaol &a, const TRcLaol &b) {
            return a->operator||(b);
        }

        template<class T>
        class Box : public virtual Laol {
        protected:

            explicit Box(const T item) : m_val(item) {
            }

            NO_COPY_CONSTRUCTORS(Box);

            virtual ~Box() {
            }

            operator T() const {
                return m_val;
            }

        private:
            T m_val;
        };

        class String : public Box<string> {
        public:

            explicit String(const string& val) : Box(val) {
            }

            NO_COPY_CONSTRUCTORS(String);

            ~String() {
            }
        };

        class Bool : public Box<bool> {

            explicit Bool(bool val) : Box(val) {
            }

            NO_COPY_CONSTRUCTORS(Bool);

            ~Bool() {
            }
        };

        class Number : public virtual Laol {
        public:
            TRcLaol operator+(const TRcLaol& b) const override final;
            TRcLaol operator-(const TRcLaol& b) const override final;
            TRcLaol operator*(const TRcLaol& b) const override final;
            TRcLaol operator/(const TRcLaol& b) const override final;
            TRcLaol operator%(const TRcLaol& b) const override final;
            TRcLaol operator^(const TRcLaol& b) const override final;
            TRcLaol operator&(const TRcLaol& b) const override final;
            TRcLaol operator|(const TRcLaol& b) const override final;
            TRcLaol operator<(const TRcLaol& b) const override final;
            TRcLaol operator>(const TRcLaol& b) const override final;
            TRcLaol operator<<(const TRcLaol& b) const override final;
            TRcLaol operator>>(const TRcLaol& b) const override final;
            TRcLaol operator==(const TRcLaol& b) const override final;
            TRcLaol operator!=(const TRcLaol& b) const override final;
            TRcLaol operator<=(const TRcLaol& b) const override final;
            TRcLaol operator>=(const TRcLaol& b) const override final;
            TRcLaol operator&&(const TRcLaol& b) const override final;
            TRcLaol operator||(const TRcLaol& b) const override final;

        protected:

            explicit Number() {
            }

            NO_COPY_CONSTRUCTORS(Number);

            virtual ~Number() = 0;

            virtual bool isInt() const final {
                return !isDouble();
            }

            virtual bool isDouble() const = 0;

        public:
            virtual int toInt() const = 0;
            virtual double toDouble() const = 0;

        private:
            static auto normalize(const Number* n);
            auto normalize(const TRcLaol& n) const;

            static TRcLaol toNumber(int val);
            static TRcLaol toNumber(double val);
        };

        class Int : public Box<int>, Number {
        public:

            explicit Int(int val) : Box(val) {
            }

            explicit Int(double val) : Box(round(val)) {
            }

            NO_COPY_CONSTRUCTORS(Int);

            ~Int() {
            }

        protected:

            int toInt() const final {
                return this->operator int();
            }

            double toDouble() const final {
                return toInt();
            }

            bool isDouble() const final {
                return false;
            }

        };

        class Double : public Box<double>, Number {
        public:

            explicit Double(double val) : Box(val) {
            }

            explicit Double(int val) : Box(val) {
            }

            NO_COPY_CONSTRUCTORS(Double);

            ~Double() {
            }

        protected:

            int toInt() const final {
                return round(toDouble());
            }

            double toDouble() const final {
                return this->operator double();
            }

            bool isDouble() const final {
                return true;
            }
        };

        class Array : public Laol {
        public:

            explicit Array() {
            }

            TRcLaol operator[](const TRcLaol& ix);

            TPFunc getFunc(const string& name) const final;

            NO_COPY_CONSTRUCTORS(Array);

            virtual ~Array() {
            }

        private:
            std::vector<TRcLaol> m_ar;
            static std::map<string, TPFunc> stFuncByName;
            static void initStatics(); //initialize stFuncByName.
        };
    }
}

#endif //_laol_rt_laol_hxx_

