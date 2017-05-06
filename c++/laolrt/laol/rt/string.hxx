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

/* 
 * File:   string.hxx
 * Author: kpfalzer
 *
 * Created on April 20, 2017, 3:55 PM
 */

#ifndef _laol_rt_string_hxx_
#define _laol_rt_string_hxx_

#include <string>
#include <map>
#include "laol/rt/laol.hxx"
#include "laol/rt/iterator.hxx"

namespace laol {
    namespace rt {

        class String : public Laol {
        public:

            explicit String(const char* p) : m_str(p) {
            }

            explicit String(const string& s) : m_str(s) {
            }


            String& operator=(const String&) = delete;

            //operators

            virtual LaolObj equal(const LaolObj&, const LaolObj& opB) const override {
                return m_str == toStdString(opB);
            }

            virtual LaolObj less(const LaolObj&, const LaolObj& opB) const override {
                return m_str < toStdString(opB);
            }

            virtual LaolObj greater(const LaolObj&, const LaolObj& opB) const override {
                return m_str > toStdString(opB);
            }

            virtual LaolObj left_shift(const LaolObj& self, const LaolObj& opB) const override {
                return append_SELF(self, opB);
            }

            virtual LaolObj right_shift(const LaolObj& self, const LaolObj& opB) const override {
                return prepend_SELF(self, opB);
            }

            virtual LaolObj add(const LaolObj& self, const LaolObj& opB) const override {
                return append(self, opB);
            }

            //unique methods
            virtual LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj reverse(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj reverse_SELF(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj length(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj append(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj append_SELF(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj prepend(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj prepend_SELF(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj iterator(const LaolObj& self, const LaolObj& args) const;
            //at(i) can take negative index too (-n is "n" from end, as in Ruby)
            virtual LaolObj at(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj toString(const LaolObj& self, const LaolObj& args) const override;

            Laol::TPMethod getFunc(const string& methodNm) const override;

            // Get primitive std::string from 'from'
            static string toStdString(const LaolObj& from, bool quote= false);

            virtual ~String();

            // NOTE: this iterator works for reading/sourcing (rhs) values.
            // TODO: modify for use in assign/lhs.

            class Iterator : public laol::rt::Iterator {
            public:
                explicit Iterator(string& ref, long int start, size_t len);

                //allow copy constructors

                LaolObj hasNext() const override;
                LaolObj next() override;

            private:
                size_t m_curr, m_len;
                string& m_str;
            };
            
        protected:
            static METHOD_BY_NAME stMethodByName; 

        private:

            size_t length() const override {
                return m_str.length();
            }

            string m_str;
        };

    }
}

#endif /* _laol_rt_string_hxx_ */

