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

            virtual LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj fail_PRED(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj eachLine(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj close(const LaolObj& self, const LaolObj& args) const = 0;

            NO_COPY_CONSTRUCTORS(IStream);

            virtual ~IStream() = 0;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

            IStream() {
            }

        private:
            static METHOD_BY_NAME stMethodByName;
        };

        class FileInputStream : public IStream {
        public:
            explicit FileInputStream(const LaolObj& fileName);

            virtual LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const override;
            virtual LaolObj eachLine(const LaolObj& self, const LaolObj& args) const override;
            virtual LaolObj fail_PRED(const LaolObj& self, const LaolObj& args) const override;
            virtual LaolObj close(const LaolObj& self, const LaolObj& args) const override;

            NO_COPY_CONSTRUCTORS(FileInputStream);

            virtual ~FileInputStream();

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            void close();

            const string m_fileName;
            std::ifstream m_ifs;

            static METHOD_BY_NAME stMethodByName;
        };
    }
}


#endif /* _laol_rt_istream_hxx_ */

