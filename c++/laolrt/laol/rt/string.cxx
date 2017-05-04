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

namespace laol {
    namespace rt {
        using std::to_string;

        //static
        Laol::METHOD_BY_NAME
        String::stMethodByName = {
            {"length", static_cast<TPMethod> (&String::length)},
            {"empty?", static_cast<TPMethod> (&String::empty_PRED)},
            {"reverse", static_cast<TPMethod> (&String::reverse)},
            {"reverse!", static_cast<TPMethod> (&String::reverse_SELF)},
            {"append", static_cast<TPMethod> (&String::append)},
            {"append!", static_cast<TPMethod> (&String::append_SELF)},
            {"prepend", static_cast<TPMethod> (&String::prepend)},
            {"prepend!", static_cast<TPMethod> (&String::prepend_SELF)},
            {"toString", static_cast<TPMethod> (&String::toString)},
            {"at", static_cast<TPMethod> (&String::at)},
            {"iterator", static_cast<TPMethod> (&String::at)},
            {"equal", static_cast<TPMethod> (&String::equal)},
            {"less", static_cast<TPMethod> (&String::less)},
            {"greater", static_cast<TPMethod> (&String::greater)}
        };

        /*static*/
        string
        String::toStdString( LaolObj& from, bool quote) {
            LaolObj x = from;
            LaolObj z = x("toString");
            const String& s = z.toType<String>();
            string ss = s.m_str;
            if (quote && from.isA<String>()) {
                ss = '"' + ss + '"';
            }
            return ss;
        }

        String::~String() {
        }

        Laol::TPMethod
        String::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);
        }

        LaolObj String::length(LaolObj&, LaolObj&) {
            auto n = length();
            return n;
        }

        LaolObj
        String::empty_PRED(LaolObj&, LaolObj&) {
            return m_str.empty();
        }

        LaolObj
        String::reverse(LaolObj&, LaolObj&) {
            auto p = new String(m_str);
            std::reverse(std::begin(p->m_str), std::end(p->m_str));
            return p;
        }

        LaolObj
        String::reverse_SELF(LaolObj& self, LaolObj&) {
            std::reverse(std::begin(unconst(this)->m_str), std::end(unconst(this)->m_str));
            return self;
        }

        LaolObj
        String::append(LaolObj& self, LaolObj& args) {
            string r = m_str + toStdString(args);
            return new String(r);
        }

        LaolObj
        String::append_SELF(LaolObj& self, LaolObj& args) {
            unconst(this)->m_str += toStdString(args);
            return self;
        }

        LaolObj
        String::prepend(LaolObj& self, LaolObj& args) {
            string r = toStdString(args) + m_str;
            return new String(r);
        }

        LaolObj
        String::prepend_SELF(LaolObj& self, LaolObj& args) {
            unconst(this)->m_str = toStdString(args) + m_str;
            return self;
        }

        //We're already a string!

        LaolObj
        String::toString(LaolObj& self, LaolObj&) {
            return self;
        }

        LaolObj
        String::at(LaolObj&, LaolObj& args) {
            //todo index-exception
            return m_str.at(actualIndex(args.toLInt()));
        }

        LaolObj
        String::iterator(LaolObj& self, LaolObj& args) {
            return self; //todo
        }
    }
}

