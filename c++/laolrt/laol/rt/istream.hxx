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
 * File:   istream.hxx
 * Author: kpfalzer
 *
 * Created on May 3, 2017, 11:16 AM
 */

#ifndef _laol_rt_istream_hxx_
#define _laol_rt_istream_hxx_

#include <fstream>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {
        // Input stream interface

        class IStream : public Laol {
        public:

            virtual LaolObj empty_PRED(LaolObj& self, LaolObj& args) = 0;
            virtual LaolObj fail_PRED(LaolObj& self, LaolObj& args) = 0;
            virtual LaolObj eachLine(LaolObj& self, LaolObj& args) = 0;
            virtual LaolObj close(LaolObj& self, LaolObj& args) = 0;

            NO_COPY_CONSTRUCTORS(IStream);

            Laol::TPMethod getFunc(const string& methodNm) const override;

            virtual ~IStream() = 0;

        protected:

            IStream() {
            }

        private:
            static METHOD_BY_NAME stMethodByName;
        };

        class FileInputStream : public IStream {
        public:
            explicit FileInputStream(const LaolObj& fileName);

            virtual LaolObj empty_PRED(LaolObj& self, LaolObj& args) override;
            virtual LaolObj eachLine(LaolObj& self, LaolObj& args) override;
            virtual LaolObj fail_PRED(LaolObj& self, LaolObj& args) override;
            virtual LaolObj close(LaolObj& self, LaolObj& args) override;

            NO_COPY_CONSTRUCTORS(FileInputStream);

            virtual ~FileInputStream();
            
        private:
            void close();
            
            const string m_fileName;
            std::ifstream m_ifs;
        };
    }
}


#endif /* _laol_rt_istream_hxx_ */

