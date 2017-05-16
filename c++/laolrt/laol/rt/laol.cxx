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
#include "laol/rt/primitives.hxx"

namespace laol {
    namespace rt {
        using std::to_string;

        /*extern*/ const LaolObj NULLOBJ;

        LaolObj::LaolObj(Args args) {
            set(args);
        }

        LaolObj::LaolObj(int rhs) : LaolObj(new Int(rhs)) {
        }

        LaolObj::LaolObj(unsigned int rhs) : LaolObj(new UnsignedInt(rhs)) {
        }

        LaolObj::LaolObj(long int rhs) : LaolObj(new LongInt(rhs)) {
        }

        LaolObj::LaolObj(unsigned long int rhs) : LaolObj(new UnsignedLongInt(rhs)) {
        }

        LaolObj::LaolObj(char rhs) : LaolObj(new Char(rhs)) {
        }
        
        LaolObj::LaolObj(double rhs) : LaolObj(new Double(rhs)) {
        }
        
        LaolObj::LaolObj(float rhs) : LaolObj(new Float(rhs)) {
        }

        LaolObj::LaolObj(bool rhs) : LaolObj(new Bool(rhs)) {
        }

        LaolObj::LaolObj(const char* rhs) : LaolObj(new String(rhs)) {
        }

        const LaolObj&
        LaolObj::set(Args args) {
            //todo m_obj = new Array(args);
            m_isRef = false;
            return *this;
        }

        const LaolObj&
        LaolObj::set(const LaolObj& rhs) {
            m_obj = unconst(rhs).asTRcLaol();
            m_isRef = false;
            return *this;
        }

        void
        LaolObj::decrRefCnt() {
            unconst(this)->asTPRcLaol()->decr();
        }

        bool 
        LaolObj::isBool() const {
            return false; //todo
        }

        bool 
        LaolObj::isFloat() const {
            return false; //todo
        }

        bool 
        LaolObj::isInt() const {
            return false; //todo
        }
        
        bool 
        LaolObj::toBool() const {
            return false; //todo
        }
        long int 
        LaolObj::toLInt() const {
            return 0; //todo
        }
        string 
        LaolObj::toQString() const {
            return "todo";
        }
        string 
        LaolObj::toStdString() const {
            return "todo";
        }

        unsigned long int 
        LaolObj::toULInt() const {
            return 0;//todo
        }

        
        LaolObj
        LaolObj::operator=(const LaolObj& rhs) {
            return asTPLaol()->assign(*this, rhs);
        }

        LaolObj
        LaolObj::operator<<(const LaolObj& opB) const {
            return asTPLaol()->left_shift(*this, opB);
        }

        LaolObj
        LaolObj::operator>>(const LaolObj& opB) const {
            return asTPLaol()->right_shift(*this, opB);
        }

        LaolObj
        LaolObj::operator+(const LaolObj& opB) const {
            return asTPLaol()->add(*this, opB);
        }
        
        LaolObj
        LaolObj::operator-(const LaolObj& opB) const {
            return asTPLaol()->subtract(*this, opB);
        }
        
        LaolObj
        LaolObj::operator*(const LaolObj& opB) const {
            return asTPLaol()->multiply(*this, opB);
        }
        
        LaolObj
        LaolObj::operator/(const LaolObj& opB) const {
            return asTPLaol()->divide(*this, opB);
        }

        LaolObj
        LaolObj::operator%(const LaolObj& opB) const {
            return asTPLaol()->modulus(*this, opB);
        }

        //todo: test if consecutive: a[][][]... work???

        LaolObj
        LaolObj::operator[](const LaolObj& opB) const {
            return asTPLaol()->subscript(*this, opB);
        }

        LaolObj
        LaolObj::operator!() const {
            return asTPLaol()->negate(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator++(int) const { //post-increment
            return asTPLaol()->post_increment(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator&&(const LaolObj& opB) const {
            return asTPLaol()->logical_and(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator||(const LaolObj& opB) const {
            return asTPLaol()->logical_or(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator<(const LaolObj& opB) const {
            return asTPLaol()->less(*this, opB);
        }

        LaolObj
        LaolObj::operator>(const LaolObj& opB) const {
            return asTPLaol()->greater(*this, opB);
        }

        LaolObj
        LaolObj::operator==(const LaolObj& opB) const {
            if (isSameObject(opB)) {
                return true;
            }
            return asTPLaol()->equal(*this, opB);
        }

        LaolObj
        LaolObj::operator()(const string& methodNm, const LaolObj& args, bool mustFind) const {
            LaolObj rval;
            Laol* pObj = asTPLaol();
            //first try subclass
            TPMethod pMethod = pObj->getFunc(methodNm);
            if (nullptr != pMethod) {
                //then try here
                pMethod = pObj->Laol::getFunc(methodNm);
            }
            if (nullptr != pMethod) {
                rval = (pObj->*pMethod)(*this, args);
            } else if (mustFind) {
                ASSERT_NEVER; //not implemented
            }
            return rval;
        }

        LaolObj
        LaolObj::operator()(const string& methodNm) const {
            return this->operator()(methodNm, NULLOBJ);
        }

        bool
        LaolObj::isSameObject(const LaolObj& other) const {
            return (this == &other)
                    || (asTPLaol() == other.asTPLaol())
                    ;
        }

        LaolObj::~LaolObj() {
        }

        /*static*/
        const Laol::METHOD_BY_NAME
        Laol::stMethodByName = {
            {"toString", static_cast<TPMethod> (&Laol::toString)},
            {"objectId", static_cast<TPMethod> (&Laol::objectId)},
            {"hashCode", static_cast<TPMethod> (&Laol::objectId)}
        };

        const Laol::METHOD_BY_NAME&
        Laol::getMethodByName() {
            return stMethodByName;
        }

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
        Laol::hashCode(const LaolObj&, const LaolObj&) const {
            return objectId(NULLOBJ, NULLOBJ);
        }

        LaolObj
        Laol::assign(const LaolObj& self, const LaolObj& opB) const {
            unconst(self).set(opB);
            return self;
        }

#define DEFINE_NO_IMPL (const LaolObj&, const LaolObj& self) const {ASSERT_NEVER; return self;}

        LaolObj Laol::left_shift DEFINE_NO_IMPL
        LaolObj Laol::right_shift DEFINE_NO_IMPL
        LaolObj Laol::add DEFINE_NO_IMPL
        LaolObj Laol::subtract DEFINE_NO_IMPL
        LaolObj Laol::multiply DEFINE_NO_IMPL
        LaolObj Laol::divide DEFINE_NO_IMPL
        LaolObj Laol::modulus DEFINE_NO_IMPL
        LaolObj Laol::subscript DEFINE_NO_IMPL
        LaolObj Laol::equal DEFINE_NO_IMPL
        LaolObj Laol::less DEFINE_NO_IMPL
        LaolObj Laol::greater DEFINE_NO_IMPL
        LaolObj Laol::negate DEFINE_NO_IMPL
        LaolObj Laol::logical_and DEFINE_NO_IMPL
        LaolObj Laol::logical_or DEFINE_NO_IMPL
        LaolObj Laol::post_increment DEFINE_NO_IMPL

#undef DEFINE_NO_IMPL

        Laol::TPMethod
        Laol::getFunc(const string& methodNm) const {
            return getFunc(unconst(this)->getMethodByName(), methodNm);
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

