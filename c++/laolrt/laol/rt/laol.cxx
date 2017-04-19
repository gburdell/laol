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

        string 
        demangleName(const char* mangledName) {
            int status;
            //https://gcc.gnu.org/onlinedocs/libstdc++/manual/ext_demangling.html
            //Since realName is malloc, we need to free.
            char *realName = abi::__cxa_demangle(mangledName, 0, 0, &status);
            assert(0 == status);
            string s = realName;
            free(realName);
            return s;
        }

        const 
        LaolObj& LaolObj::set(const LaolObj& rhs) {
            m_type = rhs.m_type;
            switch (m_type) {
                case ePrc:
                    m_dat.u_prc = const_cast<LaolObj&> (rhs).asTPRcLaol();
                    asTPRcLaol()->incr();
                    break;
                case ePstring:
                    //copy string contents (since we dont reference count)
                    set(rhs.m_dat.u_pstring->c_str());
                    break;
                default:
                    m_dat = rhs.m_dat;
                    break;
            }
            return *this;
        }

        LaolObj 
        LaolObj::operator<<(const LaolObj& rhs) {
            LaolObj rval;
            switch (m_type) {
                case ePrc:
                    rval = asTPRcLaol()->getPtr()->left_shift(asTPRcLaol(),{rhs});
                    break;
                case eInt:
                    //todo: iff rhs is number
                default:
                    break;
            }
            return rval;
        }

        void 
        LaolObj::cleanup() {
            switch (m_type) {
                case ePrc:
                    if (asTPRcLaol()->decr()) {
                        delete m_dat.u_prc;
                        m_type = eNull;
                    }
                    break;
                case ePstring:
                    delete m_dat.u_pstring;
                    m_type = eNull;
                    break;
                default:
                    break;
            }
        }

        LaolObj 
        LaolObj::operator()(const string& methodNm, Args args) {
            LaolObj rval;
            switch (m_type) {
                case ePrc:
                {
                    TRcLaol* self = asTPRcLaol();
                    Laol* pObj = self->getPtr();
                    TPMethod pMethod = pObj->getFunc(methodNm);
                    if (nullptr != pMethod) {
                        rval = (pObj->*pMethod)(self, args);
                    } else {
                        throw std::exception(); //not implemented
                    }
                }
                    break;
                case ePstring:
                    break;
                default:
                    break;
            }
            return rval;
        }

        LaolObj 
        LaolObj::operator()(const string& methodNm) {
            return this->operator ()(methodNm, {});
        }

        LaolObj::~LaolObj() {
            cleanup();
        }

        TRcLaol*
        Laol::left_shift(TRcLaol* self, const LaolObj& rhs) {
            throw std::exception(); //no implementation
        }

        Laol::TPMethod
        Laol::getFunc(const string& methodNm) const {
            throw std::exception(); //should normally delegate to subclass
        }

        Laol::Laol() {
        }

        Laol::~Laol() {
        }

        Exception::Exception(const string& reason) {
            m_reason = "Exception: " + reason;
        }

        const char* 
        Exception::what() const noexcept {
            return m_reason.c_str();
        }

        /*static*/
        const string
        NoMethodException::REASON = "no method found";

        NoMethodException::NoMethodException(const std::type_info& obj, const string& type, const string& op)
        : Exception(
        string("no ")
        + type
        + " '"
        + op
        + "' found for '"
        + demangleName(obj.name())
        + "'"
        ) {
        }

        InvalidTypeException::InvalidTypeException(const std::type_info& found, const string& expected)
        : Exception(
        string("found '")
        + demangleName(found.name())
        + "', expected '"
        + expected
        + "'") {
        }

    }
}

