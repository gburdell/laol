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

#include <vector>
#include "laol/rt/string.hxx"
#include "laol/rt/lambda.hxx"
#include "laol/rt/istream.hxx"

namespace laol {
    namespace rt {

        using std::vector;
        
        IStream::~IStream() {
        }

        //static
        Laol::METHOD_BY_NAME
        IStream::stMethodByName = {
            {"empty?", static_cast<TPMethod> (&IStream::empty_PRED)},
            {"eachLine", static_cast<TPMethod> (&IStream::eachLine)},
            {"fail?", static_cast<TPMethod> (&IStream::fail_PRED)},
            {"close", static_cast<TPMethod> (&IStream::close)}
        };

        Laol::TPMethod
        IStream::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);
        }

        FileInputStream::FileInputStream(const LaolObj& fileName)
        : m_fileName(String::toStdString(fileName)),
        m_ifs(m_fileName) {
            //todo: error on fail() ??
        }

        static void lineDone(const LaolObj& self, vector<char>& buf, const Lambda& lambda) {
            string s(buf.begin(), buf.end());
            LaolObj parm(s.c_str());
            lambda.call(self, parm);
            buf.clear();
        }

        LaolObj
        FileInputStream::eachLine(const LaolObj& self, const LaolObj& args) const {
            const Lambda& lambda = args.toType<Lambda>();
            vector<char> buf;
            char ch;
            while (!m_ifs.fail() && !m_ifs.eof()) {
                unconst(this)->m_ifs.get(ch);
                if (ch != '\n') {
                    buf.push_back(ch);
                } else {
                    lineDone(self, buf, lambda);
                }
            }
            if (! buf.empty()) {
                lineDone(self, buf, lambda);
            }
            unconst(this)->close();
            return self;
        }

        LaolObj
        FileInputStream::empty_PRED(const LaolObj& self, const LaolObj& args) const {
            return self; //todo
        }

        LaolObj
        FileInputStream::fail_PRED(const LaolObj& self, const LaolObj& args) const {
            return m_ifs.fail();
        }

        LaolObj
        FileInputStream::close(const LaolObj& self, const LaolObj& args) const {
            unconst(this)->close();
            return self;
        }

        FileInputStream::~FileInputStream() {
            close();
        }
        void FileInputStream::close() {
            m_ifs.close();
        }

    }
}
