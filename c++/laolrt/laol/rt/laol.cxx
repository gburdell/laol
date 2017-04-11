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

        void TRcLaol::setRefCnt(const Laol* p) const {
            const_cast<Laol*>(p)->setOwner(this);
        }

        Laol::~Laol() {
        }

        Laol::TPFunc Laol::getFunc(const string& methodNm) const {
            throw NoMethodException();
        }

        static void noOperationFound(const std::type_info &obj, const string& type, const string& op) {
            string reason = "no " + type + " '" + op
                    + "' found for for '"
                    + demangleName(obj.name()) + "'";
            throw NoMethodException(reason);
        }

        TRcLaol Laol::noMethodFound(const string& methodNm) const {
            noOperationFound(typeid (*this), "method", methodNm);
            return nullptr;
        }

        TRcLaol Laol::noOperatorFound(const string& op) const {
            noOperationFound(typeid (*this), "operator", op);
            return nullptr;
        }

        // No implementation for these typeless

        TRcLaol Laol::operator~() const {
            return noOperatorFound("~");
        }

        TRcLaol Laol::operator!() const {
            return noOperatorFound("!");
        }

        TRcLaol Laol::operator++() {
            return noOperatorFound("++");
        }

        TRcLaol Laol::operator--() {
            return noOperatorFound("--");
        }

        TRcLaol Laol::operator++(int) {
            return noOperatorFound("++(int)");
        }

        TRcLaol Laol::operator--(int) {
            return noOperatorFound("--(int)");
        }

        TRcLaol Laol::operator+(const TRcLaol& b) const {
            return noOperatorFound("+");
        }

        TRcLaol Laol::operator-(const TRcLaol& b) const {
            return noOperatorFound("-");
        }

        TRcLaol Laol::operator*(const TRcLaol& b) const {
            return noOperatorFound("*");
        }

        TRcLaol Laol::operator/(const TRcLaol& b) const {
            return noOperatorFound("/");
        }

        TRcLaol Laol::operator%(const TRcLaol& b) const {
            return noOperatorFound("%");
        }

        TRcLaol Laol::operator==(const TRcLaol& b) const {
            return noOperatorFound("==");
        }

        TRcLaol Laol::operator!=(const TRcLaol& b) const {
            return noOperatorFound("!=");
        }

        TRcLaol Laol::operator<(const TRcLaol& b) const {
            return noOperatorFound("<");
        }

        TRcLaol Laol::operator>(const TRcLaol& b) const {
            return noOperatorFound(">");
        }

        TRcLaol Laol::operator<=(const TRcLaol& b) const {
            return noOperatorFound("<=");
        }

        TRcLaol Laol::operator>=(const TRcLaol& b) const {
            return noOperatorFound(">=");
        }

        TRcLaol Laol::operator&&(const TRcLaol& b) const {
            return noOperatorFound("&&");
        }

        TRcLaol Laol::operator||(const TRcLaol& b) const {
            return noOperatorFound("||");
        }

        TRcLaol Laol::operator^(const TRcLaol& b) const {
            return noOperatorFound("^");
        }

        TRcLaol Laol::operator&(const TRcLaol& b) const {
            return noOperatorFound("&");
        }

        TRcLaol Laol::operator|(const TRcLaol& b) const {
            return noOperatorFound("|");
        }

        TRcLaol Laol::operator<<(const TRcLaol& b) const {
            return noOperatorFound("<<");
        }

        TRcLaol Laol::operator>>(const TRcLaol& b) const {
            return noOperatorFound(">>");
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
            return new Int((int)*this);
            //reference object:
            //return this->getOwner();
        }

        TRcLaol Int::operator--() { //pre
            set(*this -1);
            return new Int((int)*this); //copy
            //return this->getOwner();
        }

        TRcLaol Int::operator++(int) { //post
            TRcLaol rval = new Int((int)*this);
            set(*this +1);
            return rval;
        }

        TRcLaol Int::operator--(int) { //post
            TRcLaol rval = new Int((int)*this);
            set(*this -1);
            return rval;
        }

        TRcLaol Double::operator++() { //pre
            set(*this +1);
            return new Double((double)*this); //copy
            //return this->getOwner();
        }

        TRcLaol Double::operator--() { //pre
            set(*this -1);
            return new Double((double)*this); //copy
            //return this->getOwner();
        }

        TRcLaol Double::operator++(int) { //post
            TRcLaol rval = new Double((double)*this);
            set(*this +1);
            return rval;
        }

        TRcLaol Double::operator--(int) { //post
            TRcLaol rval = new Double((double)*this);
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

        /*static*/ void Array::initStatics() {
            stFuncByName["[]"] = static_cast<TPFunc> (&Array::operator[]);
        }

        TRcLaol Array::operator[](const TRcLaol& ix) {
            return new Int((int) 0xdeadbeef); //todo
        }

        Laol::TPFunc
        Array::getFunc(const string& name) const {
            return stFuncByName[name];
        }

        Exception::Exception(const string& reason) {
            m_reason = "Exception: " + reason;
        }

        const char* Exception::what() const noexcept {
            return m_reason.c_str();
        }

        /*static*/ const string NoMethodException::REASON = "no method found";

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

