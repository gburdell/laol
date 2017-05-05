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
 * File:   ostream.hxx
 * Author: kpfalzer
 *
 * Created on April 28, 2017, 7:30 PM
 */

#ifndef _laol_rt_ostream_hxx_
#define _laol_rt_ostream_hxx_

#include <fstream>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {
        // output interface

        // newline and flush (as in std::endl)
        extern const LaolObj lendl;

        class OStream : public Laol {
        public:

            //operators

            virtual LaolObj left_shift(const LaolObj& self, const LaolObj& opB) const override {
                return append_SELF(self, opB);
            }

            //unique methods
            virtual LaolObj append_SELF(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj flush(const LaolObj& self, const LaolObj& args) const;

            Laol::TPMethod getFunc(const string& methodNm) const override;

            NO_COPY_CONSTRUCTORS(OStream);

            virtual ~OStream() = 0;

        protected:

            OStream() {
            }

        private:
            static METHOD_BY_NAME stMethodByName;

        };

        class FileOutputStream : public OStream {
        public:
            explicit FileOutputStream(const LaolObj& fileName);

            virtual LaolObj fail_PRED(const LaolObj& self, const LaolObj& args) const ;
            virtual LaolObj close(const LaolObj& self, const LaolObj& args) const ;
            virtual LaolObj append_SELF(const LaolObj& self, const LaolObj& args) const override;
            virtual LaolObj flush(const LaolObj& self, const LaolObj& args) const override;

            NO_COPY_CONSTRUCTORS(FileOutputStream);

            virtual ~FileOutputStream();

            Laol::TPMethod getFunc(const string& methodNm) const override;

        private:
            void close();

            const string m_fileName;
            std::ofstream m_ofs;

            static METHOD_BY_NAME stMethodByName;
        };
    }
}

#endif /* _laol_rt_ostream_hxx_ */

