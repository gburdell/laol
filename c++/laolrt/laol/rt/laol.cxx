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

        string demangleName(const char* mangledName) {
            int status;
            //https://gcc.gnu.org/onlinedocs/libstdc++/manual/ext_demangling.html
            //Since realName is malloc, we need to free.
            char *realName = abi::__cxa_demangle(mangledName, 0, 0, &status);
            assert(0 == status);
            string s = realName;
            free(realName);
            return s;
        }

        LaolRef::LaolRef()
        : m_type(eUnused) {
        }

        LaolRef::LaolRef(Laol* r)
        : m_type(ePrc) {
            m_dat.u_prc = new TRcLaol(r);
        }

        LaolRef::LaolRef(TRcLaol& r)
        : m_type(ePrc) {
            r.incr();
            m_dat.u_prc = &r;
        }

        LaolRef::LaolRef(int val)
        : m_type(eInt) {
            m_dat.u_int = val;
        }

        LaolRef::LaolRef(double val)
        : m_type(eDouble) {
            m_dat.u_double = val;
        }

        LaolRef::LaolRef(const LaolRef& rhs)
        : m_type(rhs.m_type) {
            switch (m_type) {
                case ePrc:
                    m_dat.u_prc = &const_cast<LaolRef&>(rhs).asLaol();
                    asLaol().incr();
                    break;
                default:
                    m_dat = rhs.m_dat;
            }
        }

        LaolRef LaolRef::operator<<(const LaolRef& rhs) {
            LaolRef rval;
            switch (m_type) {
                case ePrc:
                    rval = asLaol()->left_shift(asLaol(), rhs);
                    break;
                default:
                    break;
            }
            return rval;
        }

        LaolRef::~LaolRef() {
            switch (m_type) {
                case ePrc:
                    if (asLaol().decr()) {
                        delete m_dat.u_prc;
                    }
                    break;
                default:
                    break;
            }
        }

        Laol::~Laol() {
        }

    }
}

