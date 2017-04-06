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

        class Laol;
        
        //todo: make TRcLaol real class so we can add unary ops
        //to dont have to do (TRcLaol-obj)->operator++()
        //
        typedef PTRcObjPtr<Laol> TRcLaol;

        string demangleName(const char* n);

        template<typename T>
        inline string getClassName(T p) {
            return demangleName(typeid (p).name());
        }

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

            //allow copy constructors

        private:
            static const string REASON;
        };

        class InvalidTypeException : public Exception {
        public:

            explicit InvalidTypeException(const std::type_info& found, const string& expected);

            explicit InvalidTypeException(const std::type_info& found, const std::type_info& expected) : InvalidTypeException(found, demangleName(expected.name())) {
            }

            //allow copy constructors

        private:
            static const string REASON;
        };

        class Laol : public TRcObj {
        public:

            explicit Laol() {
            }

            NO_COPY_CONSTRUCTORS(Laol);

            virtual ~Laol() = 0;

            //operators: todo
            //= += -= *= /= %= ˆ= &= |= >>= <<= == , ->* -> ( ) [ ]

            //unary ops:
            virtual TRcLaol operator~() const;
            virtual TRcLaol operator!() const;

            //pre/post incr/decr
            virtual TRcLaol operator++(); //pre
            virtual TRcLaol operator--(); //pre
            virtual TRcLaol operator++(int); //post
            virtual TRcLaol operator--(int); //post

            //binary ops: arithmetic
            virtual TRcLaol operator+(const TRcLaol& b) const;
            virtual TRcLaol operator-(const TRcLaol& b) const;
            virtual TRcLaol operator*(const TRcLaol& b) const;
            virtual TRcLaol operator/(const TRcLaol& b) const;
            virtual TRcLaol operator%(const TRcLaol& b) const;

            //binary ops: relational and comparison
            virtual TRcLaol operator==(const TRcLaol& b) const;
            virtual TRcLaol operator!=(const TRcLaol& b) const;
            virtual TRcLaol operator<(const TRcLaol& b) const;
            virtual TRcLaol operator>(const TRcLaol& b) const;
            virtual TRcLaol operator<=(const TRcLaol& b) const;
            virtual TRcLaol operator>=(const TRcLaol& b) const;

            //binary ops: logical
            virtual TRcLaol operator&&(const TRcLaol& b) const;
            virtual TRcLaol operator||(const TRcLaol& b) const;

            //binary ops: bitwise
            virtual TRcLaol operator^(const TRcLaol& b) const;
            virtual TRcLaol operator&(const TRcLaol& b) const;
            virtual TRcLaol operator|(const TRcLaol& b) const;
            virtual TRcLaol operator<<(const TRcLaol& b) const;
            virtual TRcLaol operator>>(const TRcLaol& b) const;

            //TPFunc	
            typedef TRcLaol(Laol::* TPFunc)(const TRcLaol& args);

            TRcLaol operator()(const string& methodNm, const TRcLaol& args);

        protected:
            virtual TPFunc getFunc(const string& methodNm) const;

            TRcLaol noMethodFound(const string& methodNm) const;

            TRcLaol noOperatorFound(const string& op) const;

        };

        //create binary operations

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

        inline TRcLaol operator==(const TRcLaol &a, const TRcLaol &b) {
            return a->operator==(b);
        }

        inline TRcLaol operator!=(const TRcLaol &a, const TRcLaol &b) {
            return a->operator!=(b);
        }

        inline TRcLaol operator<(const TRcLaol &a, const TRcLaol &b) {
            return a->operator<(b);
        }

        inline TRcLaol operator>(const TRcLaol &a, const TRcLaol &b) {
            return a->operator>(b);
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

        inline TRcLaol operator^(const TRcLaol &a, const TRcLaol &b) {
            return a->operator^(b);
        }

        inline TRcLaol operator&(const TRcLaol &a, const TRcLaol &b) {
            return a->operator&(b);
        }

        inline TRcLaol operator|(const TRcLaol &a, const TRcLaol &b) {
            return a->operator|(b);
        }

        inline TRcLaol operator<<(const TRcLaol &a, const TRcLaol &b) {
            return a->operator<<(b);
        }

        inline TRcLaol operator>>(const TRcLaol &a, const TRcLaol &b) {
            return a->operator>>(b);
        }

        template<typename T>
        class Box : public virtual Laol {
        protected:

            explicit Box(const T item) : m_val(item) {
            }

            NO_COPY_CONSTRUCTORS(Box);

            virtual ~Box() {
            }

            //NOTE: keep conversion protected to mitigate unintended/implied conversions

            operator T() const {
                return m_val;
            }

            T set(const T val) {
                m_val = val;
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
        public:

            TRcLaol operator~() const override final;
            TRcLaol operator!() const override final;

            explicit Bool(bool val) : Box(val) {
            }

            NO_COPY_CONSTRUCTORS(Bool);

            ~Bool() {
            }
        };

        class Number : public virtual Laol {
        public:
            TRcLaol operator!() const override final;
            TRcLaol operator+(const TRcLaol& b) const override final;
            TRcLaol operator-(const TRcLaol& b) const override final;
            TRcLaol operator*(const TRcLaol& b) const override final;
            TRcLaol operator/(const TRcLaol& b) const override final;
            TRcLaol operator%(const TRcLaol& b) const override final;
            TRcLaol operator==(const TRcLaol& b) const override final;
            TRcLaol operator!=(const TRcLaol& b) const override final;
            TRcLaol operator<(const TRcLaol& b) const override final;
            TRcLaol operator>(const TRcLaol& b) const override final;
            TRcLaol operator<=(const TRcLaol& b) const override final;
            TRcLaol operator>=(const TRcLaol& b) const override final;
            TRcLaol operator&&(const TRcLaol& b) const override final;
            TRcLaol operator||(const TRcLaol& b) const override final;
            TRcLaol operator^(const TRcLaol& b) const override final;
            TRcLaol operator&(const TRcLaol& b) const override final;
            TRcLaol operator|(const TRcLaol& b) const override final;
            TRcLaol operator<<(const TRcLaol& b) const override final;
            TRcLaol operator>>(const TRcLaol& b) const override final;

        protected:

            explicit Number() {
            }

            NO_COPY_CONSTRUCTORS(Number);

            virtual ~Number() = 0;

            
        public:
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
            static int expectInt(const Number* n);
            int expectInt(const TRcLaol& n) const;

            static TRcLaol toNumber(int val);
            static TRcLaol toNumber(double val);
                        
        };

        class Int : public Box<int>, Number {
        public:

            TRcLaol operator~() const override final;
            TRcLaol operator++() override final; //pre
            TRcLaol operator--() override final; //pre
            TRcLaol operator++(int) override final; //post
            TRcLaol operator--(int) override final; //post

            explicit Int(int val) : Box(val) {
            }

            explicit Int(double val) : Box(std::round(val)) {
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

            TRcLaol operator++() override final; //pre
            TRcLaol operator--() override final; //pre
            TRcLaol operator++(int) override final; //post
            TRcLaol operator--(int) override final; //post

            explicit Double(double val) : Box(val) {
            }

            explicit Double(int val) : Box(val) {
            }

            NO_COPY_CONSTRUCTORS(Double);

            ~Double() {
            }

        protected:

            int toInt() const final {
                return std::round(toDouble());
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

#endif /* _laol_rt_laol_hxx_ */

