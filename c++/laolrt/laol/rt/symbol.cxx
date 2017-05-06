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

#include "laol/rt/string.hxx"
#include "laol/rt/symbol.hxx"

namespace laol {
    namespace rt {

        /*static*/ Symbol::MAP Symbol::stMap;
        /*static*/ Symbol::VAL Symbol::stLastVal = 0;

        //static
        Laol::METHOD_BY_NAME Symbol::stMethodByName;

        LaolObj
        Symbol::sym(const string& s) {
            LaolObj obj;
            auto found = stMap.find(s);
            if (found == stMap.end()) {
                obj = new Symbol();
                stMap[s] = obj;
                // removed reference here/static: so we delete when no more external references
                obj.decrRefCnt();
            } else {
                obj = found->second;
            }
            return obj;
        }

        LaolObj
        Symbol::toString(const LaolObj&, const LaolObj&) const {
            return new String(":" + toString());
        }

        LaolObj
        Symbol::equal(const LaolObj&, const LaolObj& opB) const {
            /* we never get here: since LaolObj default == checks for
             * objects same: which we will have.
             * Anyway, we'll put in the code but preface w/ assert to see if 
             * we ever get here.
             */
            ASSERT_NEVER;
            return opB.isA<Symbol>() ? (m_val == opB.toType<Symbol>().m_val) : false;
        }

        /*
         * We don't store key with value, so to convert, we look for value and
         * return key.  
         * TODO: Costly performance here.
         */
        const string&
        Symbol::toString() const {
            for (auto& keyVal : stMap) {
                if (keyVal.second.toType<Symbol>().m_val == m_val) {
                    return keyVal.first;
                }
            }
            throw std::logic_error("unexpected");
        }

        Laol::TPMethod
        Symbol::getFunc(const string& methodNm) const {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(Laol::stMethodByName,{
                    {"toString", static_cast<TPMethod> (&Symbol::toString)},
                    {"equal", static_cast<TPMethod> (&Symbol::equal)}
                });
            }
            return Laol::getFunc(stMethodByName, methodNm);
        }

        Symbol::~Symbol() {
            //delete from table
            stMap.erase(toString());
        }

    }
}
