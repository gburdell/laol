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
#include <cstdlib> //free
#include <cassert>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {

        /*static*/
        string demangleName(const std::type_info& ti) {
            //https://gcc.gnu.org/onlinedocs/libstdc++/manual/ext_demangling.html
            char *realname;
            realname = abi::__cxa_demangle(ti.name(), 0, 0, &status);
            assert(0 == status);
            string s = realname;
            free(realname);
            return s;
        }

        NoMethodException::NoMethodException(const std::type_info& obj, const string& method)
        : Exception(string("no method '") + method + "' defined for '" + demangleName(ti) + "'") {

        }

        template<typename T>
        static void noOperatorMethodException(const T& m, const string& op) {
            
        }
        
        Laol::~Laol() {
        }

        Laol::TPFunc Laol::getFunc(const string& methodNm) const {
            return (TPFunc) 0; //todo: not implemented...
        }

        TRcLaol Laol::operator+(const TRcLaol& b) const {
            return new Int((int) 0xdeafdeef); //todo: do not implement
        }

        TRcLaol Laol::operator*(const TRcLaol& b) const {
            return new Int((int) 0xdeafdeef); //todo: do not implement
        }

        TRcLaol
        Laol::operator()(const string& name, const TRcLaol& args) {
            return (this->*getFunc(name))(args);
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
            auto zz = normalize(this) % normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator^(const TRcLaol& b) const {
            auto zz = normalize(this) ^ normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator&(const TRcLaol& b) const {
            auto zz = normalize(this) & normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator|(const TRcLaol& b) const {
            auto zz = normalize(this) | normalize(b);
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

        TRcLaol Number::operator<<(const TRcLaol& b) const {
            auto zz = normalize(this) << normalize(b);
            return toNumber(zz);
        }

        TRcLaol Number::operator>>(const TRcLaol& b) const {
            auto zz = normalize(this) >> normalize(b);
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

    }
}

