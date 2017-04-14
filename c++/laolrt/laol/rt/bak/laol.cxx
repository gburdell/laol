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

        LaolRef::LaolRef(Laol* p)
        : m_type(ePrc) {
            m_dat.u_prc = new TRcLaol(p);
        }

        LaolRef::LaolRef(const string& s)
        : m_type(ePstring) {
            m_dat.u_pstring = new string(s);
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
                    m_dat.u_prc = new TRcLaol(rhs.m_dat.u_prc->getPtr());
                    break;
                case ePstring:
                    m_dat.u_pstring = new string(*rhs.m_dat.u_pstring);
                    break;
                default:
                    m_dat = rhs.m_dat;
            }
        }

        LaolRef::~LaolRef() {
            switch (m_type) {
                case ePrc:
                    delete m_dat.u_prc;
                    break;
                case ePstring:
                    delete m_dat.u_pstring;
                    break;
                default:
                    break;
            }
        }

        LaolRef LaolRef::operator<<(const LaolRef& rhs) {
            LaolRef rval;
            switch (m_type) {
                case ePrc:
                    rval = m_dat.u_prc->getPtr()->operator<<(rhs);
                    break;
                default:
                    break;
            }
            return rval;
        }
        
        Laol::~Laol() {}

        LaolRef Array::operator<<(const LaolRef& data) {
            m_ar.push_back(data);
            return this;
        }

        //=====PREVIOUS HERE====
#ifdef SKIP

        /*
        void TRcLaol::setSelf(const Laol* p) const {
            const_cast<Laol*> (p)->setSelf(this);
        }
         */
        Laol::~Laol() {
        }

        Laol::TPFunc Laol::getFunc(const string& methodNm) const {
            throw NoMethodException();
        }

        const TRcLaol& Laol::getSelf() const {
            throw NoMethodException(typeid (*this), "getSelf");
        }

        void Laol::setSelf(const TRcLaol* p) {
            ; //do nothing
        }

        LaolWithSelf::LaolWithSelf() {
        }

        LaolWithSelf::~LaolWithSelf() {
        }

        // No implementation for these typeless

        /**TODO
        TRcLaol Laol::operator=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "=");
        }

        TRcLaol Laol::operator+=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "+=");
        }

        TRcLaol Laol::operator-=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "-=");
        }

        TRcLaol Laol::operator*=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "*=");
        }

        TRcLaol Laol::operator/=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "/+");
        }

        TRcLaol Laol::operator%=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "%=");
        }

        TRcLaol Laol::operator^=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "^=");
        }

        TRcLaol Laol::operator&=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "&=");
        }

        TRcLaol Laol::operator|=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "|=");
        }

        TRcLaol Laol::operator>>=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), ">>=");
        }

        TRcLaol Laol::operator<<=(const TRcLaol& rhs) {
            throw NoOperatorException(typeid (*this), "<<=");
        }
         */

        TRcLaol Laol::operator~() const {
            throw NoOperatorException(typeid (*this), "~");
        }

        TRcLaol Laol::operator!() const {
            throw NoOperatorException(typeid (*this), "!");
        }

        TRcLaol Laol::operator++() {
            throw NoOperatorException(typeid (*this), "++");
        }

        TRcLaol Laol::operator--() {
            throw NoOperatorException(typeid (*this), "--");
        }

        TRcLaol Laol::operator++(int) {
            throw NoOperatorException(typeid (*this), "++(int)");
        }

        TRcLaol Laol::operator--(int) {
            throw NoOperatorException(typeid (*this), "--(int)");
        }

        TRcLaol Laol::operator+(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "+");
        }

        TRcLaol Laol::operator-(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "-");
        }

        TRcLaol Laol::operator*(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "*");
        }

        TRcLaol Laol::operator/(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "/");
        }

        TRcLaol Laol::operator%(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "%");
        }

        TRcLaol Laol::operator==(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "==");
        }

        TRcLaol Laol::operator!=(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "!=");
        }

        TRcLaol Laol::operator<(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "<");
        }

        TRcLaol Laol::operator>(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), ">");
        }

        TRcLaol Laol::operator<=(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "<=");
        }

        TRcLaol Laol::operator>=(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), ">=");
        }

        TRcLaol Laol::operator&&(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "&&");
        }

        TRcLaol Laol::operator||(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "||");
        }

        TRcLaol Laol::operator^(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "^");
        }

        TRcLaol Laol::operator&(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "&");
        }

        TRcLaol Laol::operator|(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "|");
        }

        TRcLaol Laol::operator<<(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), "<<");
        }

        TRcLaol Laol::operator>>(const TRcLaol& b) const {
            throw NoOperatorException(typeid (*this), ">>");
        }

        TRcLaol
        Laol::operator()(const string& name, const TRcLaol& args) {
            TRcLaol method = (this->*getFunc(name))(args);
            if (method.isNull()) {
                string msg = getClassName(this)
                        + ": " + name + ": method not found";
                throw NoMethodException(msg);
            }
            return method;
        }

        Number::~Number() {
        }

        TRcLaol Number::toNumber(int val) {
            return new Int(val);
        }

        TRcLaol Number::toNumber(double val) {
            return new Double(val);
        }

        auto Number::normalize(const Number* n) {
            return n->isDouble() ? n->toDouble() : n->toInt();
        }

        auto Number::normalize(const TRcLaol& n) const {
            return normalize(dynamic_cast<const Number*> (n.getPtr()));
        }

        int Number::expectInt(const Number* n) {
            if (!n->isInt()) {
                throw InvalidTypeException(typeid (*n), typeid (Int));
            }
            return n->toInt();
        }

        int Number::expectInt(const TRcLaol& n) const {
            return expectInt(dynamic_cast<const Number*> (n.getPtr()));
        }

        TRcLaol Number::operator+(const TRcLaol& b) const {
            auto zz = normalize(this) + normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator-(const TRcLaol& b) const {
            auto zz = normalize(this) - normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator*(const TRcLaol& b) const {
            auto zz = normalize(this) * normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator/(const TRcLaol& b) const {
            auto zz = normalize(this) / normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator%(const TRcLaol& b) const {
            auto zz = expectInt(this) % expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator==(const TRcLaol& b) const {
            auto zz = normalize(this) == normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator!=(const TRcLaol& b) const {
            auto zz = normalize(this) != normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator<(const TRcLaol& b) const {
            auto zz = normalize(this) < normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator>(const TRcLaol& b) const {
            auto zz = normalize(this) > normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator<=(const TRcLaol& b) const {
            auto zz = normalize(this) <= normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator>=(const TRcLaol& b) const {
            auto zz = normalize(this) >= normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator&&(const TRcLaol& b) const {
            auto zz = normalize(this) && normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator||(const TRcLaol& b) const {
            auto zz = normalize(this) || normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator^(const TRcLaol& b) const {
            auto zz = expectInt(this) ^ expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator&(const TRcLaol& b) const {
            auto zz = expectInt(this) & expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator|(const TRcLaol& b) const {
            auto zz = expectInt(this) | expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator<<(const TRcLaol& b) const {
            auto zz = expectInt(this) << expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator>>(const TRcLaol& b) const {
            auto zz = expectInt(this) >> expectInt(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator!() const {
            auto zz = !normalize(this);
            return new Bool(zz);
        }

        TRcLaol Int::operator~() const {
            int zz = *this;
            return new Int(~zz);
        }

        TRcLaol Int::operator++() { //pre
            set(*this +1);
            //copy
            return new Int((int) * this);
            //reference object:
            //return this->getSelf();
        }

        TRcLaol Int::operator--() { //pre
            set(*this -1);
            return new Int((int) * this); //copy
            //return this->getSelf();
        }

        TRcLaol Int::operator++(int) { //post
            TRcLaol rval = new Int((int) * this);
            set(*this +1);
            return rval;
        }

        TRcLaol Int::operator--(int) { //post
            TRcLaol rval = new Int((int) * this);
            set(*this -1);
            return rval;
        }

        TRcLaol Double::operator++() { //pre
            set(*this +1);
            return new Double((double) * this); //copy
            //return this->getSelf();
        }

        TRcLaol Double::operator--() { //pre
            set(*this -1);
            return new Double((double) * this); //copy
            //return this->getSelf();
        }

        TRcLaol Double::operator++(int) { //post
            TRcLaol rval = new Double((double) * this);
            set(*this +1);
            return rval;
        }

        TRcLaol Double::operator--(int) { //post
            TRcLaol rval = new Double((double) * this);
            set(*this -1);
            return rval;
        }

        TRcLaol Bool::operator~() const {
            bool zz = *this;
            return new Bool(~zz);
        }

        TRcLaol Bool::operator!() const {
            bool zz = *this;
            return new Bool(!zz);
        }

        /*static*/ std::map<string, Laol::TPFunc> Array::stFuncByName;

        /*TODO
        //static 
        void 
        Array::initStatics() {
            stFuncByName["[]"] = static_cast<TPFunc> (&Array::operator[]);
        }
         */

        TRcLaol Array::operator[](const TRcLaol& ix) {
            return new Int((int) 0xdeadbeef); //todo
        }

        Laol::TPFunc
        Array::getFunc(const string& name) const {
            return stFuncByName[name];
        }
#endif //SKIP

        Exception::Exception(const string& reason) {
            m_reason = "Exception: " + reason;
        }

        const char* Exception::what() const noexcept {
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

