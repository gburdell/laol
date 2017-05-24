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

#include <algorithm>
#include "laol/rt/string.hxx"

namespace laol {
    namespace rt {
        using std::to_string;

        //static
        Laol::METHOD_BY_NAME String::stMethodByName;

        /*static*/
        string
        String::toStdString(const LaolObj& from, bool quote) {
            LaolObj z = from("toString");
            if (z.isNull()) {
                return "";
            }
            const String& s = z.toType<String>();
            string ss = s.m_str;
            if (quote && from.isA<String>()) {
                ss = '"' + ss + '"';
            }
            return ss;
        }

        String::~String() {
        }

        const Laol::METHOD_BY_NAME&
        String::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(stMethodByName,
						METHOD_BY_NAME({
                    {"length", static_cast<TPMethod> (&String::length)},
                    {"empty?", static_cast<TPMethod> (&String::empty_PRED)},
                    {"reverse", static_cast<TPMethod> (&String::reverse)},
                    {"reverse!", static_cast<TPMethod> (&String::reverse_SELF)},
                    {"append", static_cast<TPMethod> (&String::append)},
                    {"append!", static_cast<TPMethod> (&String::append_SELF)},
                    {"prepend", static_cast<TPMethod> (&String::prepend)},
                    {"prepend!", static_cast<TPMethod> (&String::prepend_SELF)},
                    {"at", static_cast<TPMethod> (&String::at)},
                    {"iterator", static_cast<TPMethod> (&String::at)}
                }));
            }
            return stMethodByName;
        }

        Ref
        String::length(const LaolObj&, const LaolObj&) const {
            auto n = length();
            return LaolObj(n);
        }

        Ref
        String::empty_PRED(const LaolObj&, const LaolObj&) const {
            return LaolObj(m_str.empty());
        }

        Ref
        String::reverse(const LaolObj&, const LaolObj&) const {
            auto p = new String(m_str);
            std::reverse(std::begin(p->m_str), std::end(p->m_str));
            return LaolObj(p);
        }

        Ref
        String::reverse_SELF(const LaolObj& self, const LaolObj&) const {
            std::reverse(std::begin(unconst(this)->m_str), std::end(unconst(this)->m_str));
            return self;
        }

        Ref
        String::append(const LaolObj& self, const LaolObj& args) const {
            string r = m_str + toStdString(args);
            return LaolObj(new String(r));
        }

        Ref
        String::append_SELF(const LaolObj& self, const LaolObj& args) const {
            unconst(this)->m_str += toStdString(args);
            return self;
        }

        Ref
        String::prepend(const LaolObj& self, const LaolObj& args) const {
            string r = toStdString(args) + m_str;
            return LaolObj(new String(r));
        }

        Ref
        String::prepend_SELF(const LaolObj& self, const LaolObj& args) const {
            unconst(this)->m_str = toStdString(args) + m_str;
            return self;
        }

        //We're already a string!

        LaolObj
        String::toString() const {
            return LaolObj(this);
        }

        Ref
        String::at(const LaolObj&, const LaolObj& args) const {
            auto c = m_str.at(actualIndex(args.toLongInt(), m_str.size()));
            return LaolObj(c);
        }

        Ref
        String::iterator(const LaolObj& self, const LaolObj& args) const {
            return self; //todo
        }
    }
}

