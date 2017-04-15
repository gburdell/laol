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

#include <cxxabi.h>
#include <cstdlib>  //free
#include <cassert>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {

        LaolRef::LaolRef()
        : m_type(eNull) {
        }

        LaolRef::LaolRef(Laol* r)
        : m_type(ePrc) {
            m_dat.u_prc = new TRcLaol(r);
        }

        LaolRef::LaolRef(TRcLaol* r)
        : m_type(ePrc) {
            r->incr();
            m_dat.u_prc = r;
        }

        LaolRef::LaolRef(int val)
        : m_type(eInt) {
            m_dat.u_int = val;
        }

        LaolRef::LaolRef(double val)
        : m_type(eDouble) {
            m_dat.u_double = val;
        }

        LaolRef::LaolRef(bool val)
        : m_type(eBool) {
            m_dat.u_bool = val;
        }

        LaolRef::LaolRef(const char* s)
        : m_type(ePstring) {
            m_dat.u_pstring = new string(s);
        }

        LaolRef::LaolRef(const LaolRef& rhs)
        : m_type(rhs.m_type) {
            switch (m_type) {
                case ePrc:
                    m_dat.u_prc = const_cast<LaolRef&> (rhs).asTPRcLaol();
                    asTPRcLaol()->incr();
                    break;
                default:
                    m_dat = rhs.m_dat;
            }
        }

        LaolRef LaolRef::operator<<(const LaolRef& rhs) {
            LaolRef rval;
            switch (m_type) {
                case ePrc:
                    rval = asTPRcLaol()->getPtr()->left_shift(asTPRcLaol(), rhs);
                    break;
                case eInt:
                    //todo: iff rhs is number
                default:
                    break;
            }
            return rval;
        }

        const LaolRef& LaolRef::operator=(const LaolRef& rhs) {
            switch (m_type) {
                case ePrc:
                    if (asTPRcLaol()->decr()) {
                        delete m_dat.u_prc;
                    }
                    break;
                case ePstring:
                    delete m_dat.u_pstring;
                    break;
                default:
                    break;
            }
            m_type = rhs.m_type;
            switch (m_type) {
                case ePrc:
                    m_dat.u_prc = const_cast<LaolRef&> (rhs).asTPRcLaol();
                    asTPRcLaol()->incr();
                    break;
                default:
                    m_dat = rhs.m_dat;
            }
            return *this;
        }

        LaolRef::~LaolRef() {
            switch (m_type) {
                case ePrc:
                    if (asTPRcLaol()->decr()) {
                        delete m_dat.u_prc;
                    }
                    break;
                case ePstring:
                    delete m_dat.u_pstring;
                    break;
                default:
                    break;
            }
        }

        TRcLaol*
        Laol::left_shift(TRcLaol* self, const LaolRef& rhs) {
            throw std::exception();
        }

        Laol::Laol() {
        }

        Laol::~Laol() {
        }

        Array::Array() {
        }

        Array::~Array() {
        }

        TRcLaol*
        Array::left_shift(TRcLaol* self, const LaolRef& rhs) {
            m_ar.push_back(rhs);
            return self;
        }


    }
}

