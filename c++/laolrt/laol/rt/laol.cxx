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

#include <cassert>
#include <sstream>
#include "laol/rt/laol.hxx"
#include "laol/rt/string.hxx"
#include "laol/rt/array.hxx"
#include "laol/rt/exception.hxx"

namespace laol {
    namespace rt {
        using std::to_string;

        static const LaolObj NULLOBJ;

        /*static*/
        LaolObj::LAOLOBJ_METHOD_BY_NAME LaolObj::stMethodByName = {
            {"toString", static_cast<TPLaolObjMethod> (&LaolObj::toString)},
            {"objectId", static_cast<TPLaolObjMethod> (&LaolObj::objectId)},
        };

        LaolObj
        LaolObj::objectId(const LaolObj&) const {
            ASSERT_TRUE(!isObject()); //just for primitives
            return toObjectId(this);
        }

        LaolObj
        LaolObj::toString(const LaolObj&) const {
            ASSERT_TRUE(!isObject()); //just for primitives
            std::ostringstream oss;
            LaolObj ok = numberApply<false>([&oss](auto x) {
                oss << x;
                return true;
            });
            if (ok.isNull()) {
                if (eBool == m_type) {
                    oss << (m_dat.u_bool ? "true" : "false");
                } else {
                    ASSERT_TRUE(eChar == m_type);
                    oss << '\'' << m_dat.u_char << '\'';
                }
            }
            string s = oss.str();
            return new String(s);
        }

        const LaolObj&
        LaolObj::set(Args args) {
            return set(new Array(args));
        }

        const LaolObj&
        LaolObj::set(const LaolObj& rhs) {
            m_type = rhs.m_type;
            if (isObject()) {
                TRcLaol* p = const_cast<LaolObj&> (rhs).asTPRcLaol();
                m_dat.u_prc = p;
                asTPRcLaol()->incr();
            } else {
                m_dat = rhs.m_dat;
            }
            return *this;
        }

        const LaolObj&
        LaolObj::set(const char* rhs) {
            return set(new String(rhs));
        }

        bool
        LaolObj::isInt() const {
            switch (m_type) {
                case eInt: case eLInt: case eUInt: case eULInt:
                    return true;
                default:
                    return false;
            }
        }

        unsigned long int
        LaolObj::toULInt() const {
            unsigned long int rval;
            intApply([&rval](auto val) {
                rval = (unsigned long int) val;
                return NULLOBJ;
            });
            return rval;
        }

        long int
        LaolObj::toLInt() const {
            long int rval;
            intApply([&rval](auto val) {
                rval = (long int) val;
                return NULLOBJ;
            });
            return rval;
        }

        bool
        LaolObj::isFloat() const {
            switch (m_type) {
                case eFloat: case eDouble:
                    return true;
                default:
                    return false;
            }
        }

        LaolObj
        LaolObj::operator=(const LaolObj& rhs) {
            if (isObject()) {
                return asTPLaol()->assign(*this, rhs);
            } else {
                cleanup();
                return set(rhs);
            }
        }
        
        LaolObj 
        LaolObj::subscript_assign(const LaolObj& subscript, const LaolObj& rhs) {
            ASSERT_TRUE(isObject());
            return asTPLaol()->subscript_assign(*this, toV(subscript, rhs));
        }

        LaolObj
        LaolObj::operator<<(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->left_shift(*this, opB)
                    : intBinaryOp(opB, [](auto a, auto b) {
                        return a << b;
                    });
        }

        LaolObj
        LaolObj::operator>>(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->right_shift(*this, opB)
                    : intBinaryOp(opB, [](auto a, auto b) {
                        return a >> b;
                    });
        }

        LaolObj
        LaolObj::operator+(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->add(*this, opB)
                    : numberBinaryOp(opB, [](auto a, auto b) {
                        return a + b;
                    });
        }

        LaolObj
        LaolObj::operator[](const LaolObj& opB) const {
            return asTPLaol()->subscript(*this, opB);
        }

        LaolObj
        LaolObj::operator!() const {
            return isObject()
                    ? asTPLaol()->negate(*this, NULLOBJ)
                    : !toBool();
        }

        LaolObj
        LaolObj::operator&&(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->logical_and(*this, NULLOBJ)
                    : (toBool() && opB.toBool());
        }

        LaolObj
        LaolObj::operator||(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->logical_or(*this, NULLOBJ)
                    : (toBool() || opB.toBool());
        }

        LaolObj
        LaolObj::operator<(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->less(*this, opB)
                    : primitiveBinaryOp(opB, [](auto a, auto b) {
                        return a < b;
                    });
        }

        LaolObj
        LaolObj::operator>(const LaolObj& opB) const {
            return isObject()
                    ? asTPLaol()->greater(*this, opB)
                    : primitiveBinaryOp(opB, [](auto a, auto b) {
                        return a > b;
                    });
        }

        LaolObj
        LaolObj::operator==(const LaolObj& opB) const {
            if (isSameObject(opB)) {
                return true;
            }
            return isObject()
                    ? asTPLaol()->equal(*this, opB)
                    : primitiveBinaryOp(opB, [](auto a, auto b) {
                        return a == b;
                    });
        }

        void
        LaolObj::cleanup() {
            if (isObject()) {
                if (asTPRcLaol()->decr()) {

                    delete m_dat.u_prc;
                }
            }
            m_type = eNull;
        }

        LaolObj
        LaolObj::operator()(const string& methodNm, const LaolObj& args) {
            LaolObj rval;
            if (isObject()) {
                Laol* pObj = asTPRcLaol()->getPtr();
                //first try subclass
                TPMethod pMethod = pObj->getFunc(methodNm);
                if (nullptr == pMethod) {
                    //then try here
                    pMethod = pObj->Laol::getFunc(methodNm);
                }
                if (nullptr != pMethod) {
                    rval = (pObj->*pMethod)(*this, args);
                } else {
                    ASSERT_NEVER; //not implemented
                }
            } else {
                auto found = stMethodByName.find(methodNm);
                if (found != stMethodByName.end()) {
                    rval = (this->*(found->second))(args);
                } else {

                    ASSERT_NEVER;
                }
            }
            return rval;
        }

        LaolObj
        LaolObj::operator()(const string& methodNm) {

            return this->operator()(methodNm, NULLOBJ);
        }

        bool
        LaolObj::isSameObject(const LaolObj& other) const {

            return (this == &other)
                    || ((isObject() && other.isObject())
                    && (asTPLaol() == other.asTPLaol()))
                    ;
        }

        LaolObj::~LaolObj() {

            cleanup();
        }

        /*static*/
        Laol::METHOD_BY_NAME Laol::stMethodByName = {
            {"toString", static_cast<TPMethod> (&Laol::toString)},
            {"objectId", static_cast<TPMethod> (&Laol::objectId)},
        };

        /*static*/
        string
        Laol::getClassName(const LaolObj& r) {
            const TRcObj& q = r.asTPRcLaol()->asT();

            return demangleName(typeid (q).name());
        }

        LaolObj
        Laol::objectId(const LaolObj&, const LaolObj&) const {
            LaolObj id = objectId();

            return id;
        }

        LaolObj
        Laol::toString(const LaolObj& self, const LaolObj&) const {
            std::ostringstream oss;
            oss << getClassName(self) << "@" << objectId();
            auto s = oss.str();

            return new String(s);
        }

        LaolObj
        Laol::assign(const LaolObj& self, const LaolObj& opB) const {
            unconst(self).cleanup();
            unconst(self).set(opB);

            return self;
        }

#define DEFINE_NO_IMPL (const LaolObj&, const LaolObj& self) const {ASSERT_NEVER; return self;}

        LaolObj Laol::left_shift DEFINE_NO_IMPL
        LaolObj Laol::right_shift DEFINE_NO_IMPL
        LaolObj Laol::add DEFINE_NO_IMPL
        LaolObj Laol::subscript DEFINE_NO_IMPL
        LaolObj Laol::equal DEFINE_NO_IMPL
        LaolObj Laol::less DEFINE_NO_IMPL
        LaolObj Laol::greater DEFINE_NO_IMPL
        LaolObj Laol::negate DEFINE_NO_IMPL
        LaolObj Laol::logical_and DEFINE_NO_IMPL
        LaolObj Laol::logical_or DEFINE_NO_IMPL
        LaolObj Laol::subscript_assign DEFINE_NO_IMPL

#undef DEFINE_NO_IMPL

        Laol::TPMethod
        Laol::getFunc(const string& methodNm) const {

            return getFunc(stMethodByName, methodNm);
        }

        /*static*/
        Laol::TPMethod
        Laol::getFunc(const METHOD_BY_NAME& methodByName, const string& methodNm) {
            auto search = methodByName.find(methodNm);
            auto rval = (search != methodByName.end()) ? search->second : nullptr;

            return rval;
        }

        size_t
        Laol::actualIndex(long int ix) const {
            long int actual = (0 <= ix) ? ix : (length() + ix);
            if ((actual >= length()) || (0 > actual)) {

                const auto n = length() - 1;
                throw IndexException(
                        to_string(ix),
                        "[" + to_string(-n) + ".." + to_string(n) + "]");
            }
            return actual;
        }

        size_t Laol::length() const {
            ASSERT_NEVER;

            return -1;
        }

        Laol::Laol() {
        }

        Laol::~Laol() {
        }
    }
}

