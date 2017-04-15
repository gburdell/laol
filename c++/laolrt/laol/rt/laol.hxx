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

#include <vector>
#include <map>
#include <cmath>
#include "xyzzy/refcnt.hxx"
#include "laol/rt/common.hxx"

#define NO_COPY_CONSTRUCTORS(_t)            \
    _t(const _t&) = delete;                 \
    _t& operator=(const _t&) = delete

namespace laol {
    namespace rt {
        using xyzzy::TRcObj;
        using xyzzy::PTRcObjPtr;

        class Laol;
        typedef PTRcObjPtr<Laol> TRcLaol;

        class LaolRef {
        public:

            explicit LaolRef();

            // LaolRef lhs = new Type(...)
            LaolRef(Laol* val);

            LaolRef(TRcLaol* r);

            LaolRef(bool val);
            // LaolRef lhs = 47;
            LaolRef(int val);
            LaolRef(double val);
            LaolRef(const char* s);

            // LaolRef rhs = ...; LaolRef lhs = rhs;
            LaolRef(const LaolRef& rhs);

            const LaolRef& operator=(const LaolRef& rhs);

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
                eBool, eInt, eDouble,
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
                //float u_float;
                double u_double;
                //long double u_ldouble;
            } m_dat;

            TRcLaol* asTPRcLaol() {
                return m_dat.u_prc;
            }

            void cleanup();

            const LaolRef& set(Laol* rhs);
            const LaolRef& set(TRcLaol* r);
            const LaolRef& set(const LaolRef& rhs);
            const LaolRef& set(bool rhs);
            const LaolRef& set(int rhs);
            const LaolRef& set(double rhs);
            const LaolRef& set(const char* rhs);
            const LaolRef& set(char rhs);
        };

        class Laol : public TRcObj {
        public:

            explicit Laol();

            NO_COPY_CONSTRUCTORS(Laol);

            //http://stackoverflow.com/questions/8679089/c-official-operator-names-keywords
            virtual TRcLaol* left_shift(TRcLaol* self, const LaolRef& rhs);

            virtual ~Laol() = 0;

        };

        class Array : public Laol {
        public:

            explicit Array();

            NO_COPY_CONSTRUCTORS(Array);

            virtual TRcLaol* left_shift(TRcLaol* self, const LaolRef& rhs) override;

            virtual ~Array();

        private:
            std::vector<LaolRef> m_ar;
        };

    }
}

#endif /* _laol_rt_laol_hxx_ */

