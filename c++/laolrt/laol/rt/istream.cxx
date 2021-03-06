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
#include "laol/rt/exception.hxx"

namespace laol {
    namespace rt {

        using std::vector;

        IStream::~IStream() {
        }

        //static
        Laol::METHOD_BY_NAME IStream::stMethodByName;
        Laol::METHOD_BY_NAME FileInputStream::stMethodByName;

        const Laol::METHOD_BY_NAME& 
        IStream::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = {
                    {"empty?", static_cast<TPMethod> (&IStream::empty_PRED)},
                    {"eachLine", static_cast<TPMethod> (&IStream::eachLine)},
                    {"fail?", static_cast<TPMethod> (&IStream::fail_PRED)},
                    {"close", static_cast<TPMethod> (&IStream::close)}
                };
            }
            return stMethodByName;
        }

        const Laol::METHOD_BY_NAME& 
        FileInputStream::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(stMethodByName, IStream::getMethodByName());
            }
            return stMethodByName;
        }

        FileInputStream::FileInputStream(const LaolObj& fileName)
        : m_fileName(String::toStdString(fileName)),
        m_ifs(m_fileName) {
            if (m_ifs.fail()) {
                throw FileException(m_fileName, "could not open file for read");
            }
        }

        static void lineDone(const LaolObj& self, vector<char>& buf, const Lambda& lambda) {
            string s(buf.begin(), buf.end());
            LaolObj parm(s.c_str());
            lambda.call(self, parm);
            buf.clear();
        }

        Ref
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
            if (!buf.empty()) {
                lineDone(self, buf, lambda);
            }
            unconst(this)->close();
            return self;
        }

        Ref
        FileInputStream::empty_PRED(const LaolObj& self, const LaolObj& args) const {
            return m_ifs.eof();
        }

        Ref
        FileInputStream::fail_PRED(const LaolObj& self, const LaolObj& args) const {
            return m_ifs.fail();
        }

        Ref
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
