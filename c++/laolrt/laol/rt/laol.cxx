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
        /*extern*/ const LaolObj TRUE(new Bool(true));
        /*extern*/ const LaolObj FALSE(new Bool(false));

        /*extern*/
        size_t
        actualIndex(long int ix, size_t length) {
            long int actual = (0 <= ix) ? ix : (length + ix);
            if ((actual >= length) || (0 > actual)) {
                const auto n = length - 1;
                throw IndexException(
                        to_string(ix),
                        "[" + to_string(-n) + ".." + to_string(n) + "]");
            }
            return actual;
        }

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

        LaolObj::LaolObj(bool rhs) : LaolObj(rhs ? TRUE : FALSE) {
        }

        LaolObj::LaolObj(const char* rhs) : LaolObj(new String(rhs)) {
        }

        LaolObj::LaolObj(const LaolObj& rhs) : m_obj(rhs.m_obj) {
        }

        LaolObj::LaolObj(const Ref& r) : LaolObj(static_cast<const LaolObj&> (*(r.m_ref))) {
        }

        const LaolObj&
        LaolObj::set(Args args) {
            m_obj = new Array(args);
            return *this;
        }

        //todo: just from assign

        const LaolObj&
        LaolObj::set(const LaolObj& rhs) {
            TRcLaol &rhsRef = unconst(rhs).asTRcLaol();
            m_obj = rhsRef;
            return *this;
        }

        void
        LaolObj::decrRefCnt() {
            unconst(this)->asTPRcLaol()->decr();
        }

        bool
        LaolObj::isBool() const {
            return this->isA<Bool>();
        }

        bool
        LaolObj::isFloat() const {
            if (!isA<INumber>()) {
                return false;
            }
            switch (toType<INumber>().getType()) {
                case INumber::eFloat: //fall through
                case INumber::eDouble:
                    return true;
                default:
                    return false;
            }
        }

        bool
        LaolObj::isInt() const {
            if (!isA<INumber>()) {
                return false;
            }
            switch (toType<INumber>().getType()) {
                case INumber::eFloat: //fall through
                case INumber::eDouble:
                    return false;
                default:
                    return true;
            }
        }

        bool
        LaolObj::toBool() const {
            ASSERT_TRUE(isA<Bool>());
            return toType<Bool>().m_val;
        }

        int
        LaolObj::toInt() const {
            return INumber::toInt(*this);
        }

        long int
        LaolObj::toLongInt() const {
            return INumber::toLongInt(*this);
        }

        unsigned int
        LaolObj::toUnsignedInt() const {
            return INumber::toUnsignedInt(*this);
        }

        unsigned long int
        LaolObj::toUnsignedLongInt() const {
            return INumber::toUnsignedLongInt(*this);
        }

        size_t
        LaolObj::toSizet() const {
            return toUnsignedLongInt();
        }

        double
        LaolObj::toDouble() const {
            return INumber::toDouble(*this);
        }

        float
        LaolObj::toFloat() const {
            return INumber::toFloat(*this);
        }

        string
        LaolObj::toQString() const {
            return "todo";
        }

        string
        LaolObj::toStdString() const {
            return "todo";
        }

        LaolObj
        LaolObj::operator=(const LaolObj& rhs) {
            if (this == &rhs) {
                return *this;
            }
            if (isNull()) {
                m_obj = rhs.m_obj;
                return *this;
            }
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

        Ref
        LaolObj::operator[](const LaolObj& opB) const {
            return asTPLaol()->subscript(*this, opB);
        }

        LaolObj
        LaolObj::operator!() const {
            return asTPLaol()->negate(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator~() const {
            return asTPLaol()->complement(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator&(const LaolObj& opB) const {
            return asTPLaol()->bitwise_and(*this, opB);
        }

        LaolObj
        LaolObj::operator|(const LaolObj& opB) const {
            return asTPLaol()->bitwise_or(*this, opB);
        }

        LaolObj
        LaolObj::operator^(const LaolObj& opB) const {
            return asTPLaol()->bitwise_xor(*this, opB);
        }

        LaolObj
        LaolObj::operator++(int) const { //post-increment
            return asTPLaol()->post_increment(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator--(int) const { //post-increment
            return asTPLaol()->post_decrement(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator++() const {
            return asTPLaol()->pre_increment(*this, NULLOBJ);
        }

        LaolObj
        LaolObj::operator--() const {
            return asTPLaol()->pre_decrement(*this, NULLOBJ);
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

        Ref
        LaolObj::operator()(const string& methodNm, const LaolObj& args, bool mustFind) const {
            Ref rval;
            Laol* pObj = asTPLaol();
            //first try subclass
            FIXME!
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

        Ref
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

        LaolObj
        LaolObj::hashCode() const {
            return asTPLaol()->hashCode();
        }

        LaolObj
        LaolObj::objectId() const {
            return asTPLaol()->objectId();
        }

        LaolObj
        LaolObj::toString() const {
            return asTPLaol()->toString();
        }

        //todo: change to virtual/override of LaolObj operatpr=(...) ???

        const Ref& Ref::operator=(const LaolObj& rhs) {
            m_ref->LaolObj::operator=(rhs);
            return *this;
        }

        const Ref& Ref::operator=(const Ref& r) {
            if (m_ref != r.m_ref) {
                if (nullptr != m_ref) {
                    m_ref->LaolObj::operator=(*(r.m_ref));
                } else {
                    m_ref = r.m_ref;
                }
            }
            return *this;
        }

        Ref
        Ref::operator[](const LaolObj& subscript) const {
            if (!m_ref->isA<ArrayOfRef>()) {
                return m_ref->LaolObj::operator[](subscript);
            } else {
                ASSERT_NEVER;
                //todo: we dont need this???
                const ArrayOfRef& refs = m_ref->toType<ArrayOfRef>();
                return refs.subscript(*this, subscript);
            }
        }

        /*static*/
        const Laol::METHOD_BY_NAME Laol::stMethodByName = {};

        const Laol::METHOD_BY_NAME& Laol::getMethodByName() {
            return stMethodByName;
        }

        /*static*/
        string
        Laol::getClassName(const LaolObj & r) {
            const TRcObj& q = r.asTPRcLaol()->asT();
            return demangleName(typeid (q).name());
        }

        string
        Laol::getClassName() const {
            return demangleName(typeid (*this).name());
        }

        LaolObj
        Laol::objectId() const {
            auto id = toObjectId(this);
            return id;
        }

        LaolObj
        Laol::toString() const {
            std::ostringstream oss;
            oss << getClassName() << "@" << toObjectId(this);
            auto s = oss.str();
            return new String(s);
        }

        LaolObj
        Laol::hashCode() const {
            return objectId();
        }

        LaolObj
        Laol::assign(const LaolObj& self, const LaolObj & rhs) const {
            unconst(self).set(rhs);
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

        //LaolObj Laol::subscript DEFINE_NO_IMPL
        Ref Laol::subscript(const LaolObj&, const LaolObj & self) const {
            ASSERT_NEVER;
            return NULLOBJ;
        }

        LaolObj Laol::equal DEFINE_NO_IMPL
        LaolObj Laol::less DEFINE_NO_IMPL
        LaolObj Laol::greater DEFINE_NO_IMPL
        LaolObj Laol::negate DEFINE_NO_IMPL
        LaolObj Laol::complement DEFINE_NO_IMPL
        LaolObj Laol::logical_and DEFINE_NO_IMPL
        LaolObj Laol::logical_or DEFINE_NO_IMPL
        LaolObj Laol::bitwise_and DEFINE_NO_IMPL
        LaolObj Laol::bitwise_or DEFINE_NO_IMPL
        LaolObj Laol::bitwise_xor DEFINE_NO_IMPL
        LaolObj Laol::post_increment DEFINE_NO_IMPL
        LaolObj Laol::post_decrement DEFINE_NO_IMPL
        LaolObj Laol::pre_increment DEFINE_NO_IMPL
        LaolObj Laol::pre_decrement DEFINE_NO_IMPL

#undef DEFINE_NO_IMPL

        Laol::TPMethod
        Laol::getFunc(const string & methodNm) const {
            return getFunc(unconst(this)->getMethodByName(), methodNm);
        }

        /*static*/
        Laol::TPMethod
        Laol::getFunc(const METHOD_BY_NAME& methodByName, const string & methodNm) {
            auto search = methodByName.find(methodNm);
            auto rval = (search != methodByName.end()) ? search->second : nullptr;

            return rval;
        }

        Laol::Laol() {
        }

        Laol::~Laol() {
        }
    }
}

