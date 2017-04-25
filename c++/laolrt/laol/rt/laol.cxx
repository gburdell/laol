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
#include <sstream>
#include "laol/rt/laol.hxx"
#include "laol/rt/string.hxx"
#include "laol/rt/array.hxx"

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

        /*static*/
        LaolObj::LAOLOBJ_METHOD_BY_NAME LaolObj::stMethodByName = {
            {"toString", static_cast<TPLaolObjMethod> (&LaolObj::toString)},
            {"objectId", static_cast<TPLaolObjMethod> (&LaolObj::objectId)},
        };

        LaolObj
        LaolObj::objectId(Args) {
            ASSERT_TRUE(!isObject()); //just for primitives
            return toObjectId(this);
        }

        LaolObj
        LaolObj::toString(Args) {
            ASSERT_TRUE(!isObject()); //just for primitives
            std::ostringstream oss;
            LaolObj ok = numberApply<false>([&oss](auto x) {
                oss << x;
                return true;
            });
            if (ok.isNull()) {
                ASSERT_TRUE(eBool == m_type);
                oss << (m_dat.u_bool ? "true" : "false");
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

        static const LaolObj UNUSED;

        unsigned long int
        LaolObj::asULInt() const {
            unsigned long int rval;
            intApply([&rval](auto val) {
                rval = (unsigned long int) val;
                return UNUSED;
            });
            return rval;
        }

        long int
        LaolObj::asLInt() const {
            long int rval;
            intApply([&rval](auto val) {
                rval = (long int) val;
                return UNUSED;
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

        /*
         * TODO: consider:
         * irb(main):001:0> a = [1,2,3,4]
         * => [1, 2, 3, 4]
         * irb(main):007:0> b = a[1..2] = %w{a b}
         * => ["a", "b"]
         * irb(main):008:0> b
         * => ["a", "b"]
         * irb(main):009:0> a
         * => [1, "a", "b", 4]
         *
         * NOTES:
         * 1) don't forget initializer constructor: 'LaolObj a = b[1..2]'
         *    where rhs is slice/iterator
         * 2) Consider soln: a class with iterator also has method
         *    called slice which returns value after applying iterator.
         * 3) proxy, as in: http://stackoverflow.com/questions/5762042/const-overloaded-operator-function-and-its-invocation
         * 4) Remember: rhs as slice/iterator evaluates to (const) value
         * 5) lhs as slice/iterator applies rhs could be slice/iterator or NOT/composite);
         *    but then lhs value could be chained (i.e., evaluated), as in:
         *    a = b[1..2] = c[3..4]
         * 6) perhaps as simple as defining 'operator LaolObj()' on iterator, to get its value?
         */
        LaolObj
        LaolObj::operator=(const LaolObj& rhs) {
            if (isObject()) {
                asTPLaol()->assign(*this,{rhs});
                //TODO: see consider above.  'return *this' is NOT correct!
                return *this;
            } else {
                cleanup();
                return set(rhs);
            }
        }

        LaolObj
        LaolObj::operator<<(const LaolObj& opB) {
            return isObject()
                    ? asTPLaol()->left_shift(*this,{opB})
            : intBinaryOp(opB, [](auto a, auto b) {
                return a << b;
            });
        }

        LaolObj
        LaolObj::operator>>(const LaolObj& opB) {
            return isObject()
                    ? asTPLaol()->right_shift(*this,{opB})
            : intBinaryOp(opB, [](auto a, auto b) {
                return a >> b;
            });
        }

        LaolObj
        LaolObj::operator+(const LaolObj& opB) {
            return isObject()
                    ? asTPLaol()->add(*this,{opB})
            : numberBinaryOp(opB, [](auto a, auto b) {
                return a + b;
            });
        }

        LaolObj LaolObj::operator[](const LaolObj& opB) {
            ASSERT_TRUE(isObject());
            return asTPLaol()->subscript(*this,{opB});
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
        LaolObj::operator()(const string& methodNm, Args args) {
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
            return this->operator()(methodNm,{});
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
        Laol::objectId(LaolObj&, Args) {
            LaolObj id = objectId();
            return id;
        }

        LaolObj
        Laol::toString(LaolObj& self, Args) {
            std::ostringstream oss;
            oss << getClassName(self) << "@" << objectId();
            auto s = oss.str();
            return new String(s);
        }

        LaolObj
        Laol::left_shift(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::right_shift(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::add(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::assign(LaolObj& self, const LaolObj& opB) {
            self.cleanup();
            self.set(opB);
            return self;
        }

        LaolObj
        Laol::subscript(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::equal(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::less(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::greater(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

        LaolObj
        Laol::negate(LaolObj& self, const LaolObj& opB) {
            ASSERT_NEVER; //no implementation
            return self;
        }

#define DEFINE_NO_IMPL (LaolObj&, const LaolObj& self) {ASSERT_NEVER; return self;}

        LaolObj Laol::logical_and DEFINE_NO_IMPL
        LaolObj Laol::logical_or  DEFINE_NO_IMPL
        
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

        IndexException::IndexException(const string& found, const string& expected)
        : Exception(
        string("invalid index.  Found '")
        + found
        + "', expected '"
        + expected
        + "'"
        ) {
        }

    }
}

