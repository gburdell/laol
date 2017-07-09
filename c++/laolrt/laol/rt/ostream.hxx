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
            virtual Ref print(const LaolObj& self, const LaolObj& args) const = 0;
            virtual Ref println(const LaolObj& self, const LaolObj& args) const;
            virtual Ref append_SELF(const LaolObj& self, const LaolObj& args) const;
            virtual Ref flush(const LaolObj& self, const LaolObj& args) const = 0;

            NO_COPY_CONSTRUCTORS(OStream);

            virtual ~OStream() = 0;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

            OStream() {
            }

        private:
            static METHOD_BY_NAME stMethodByName;

        };

        class FileOutputStream : public OStream {
        public:
            explicit FileOutputStream(const LaolObj& fileName);

            virtual Ref fail_PRED(const LaolObj& self, const LaolObj& args) const;
            virtual Ref close(const LaolObj& self, const LaolObj& args) const;
            virtual Ref print(const LaolObj& self, const LaolObj& args) const override;
            virtual Ref flush(const LaolObj& self, const LaolObj& args) const override;

            NO_COPY_CONSTRUCTORS(FileOutputStream);

            virtual ~FileOutputStream();

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            void close();

            const string m_fileName;
            std::ofstream m_ofs;

            static METHOD_BY_NAME stMethodByName;
        };
    }
}

#endif /* _laol_rt_ostream_hxx_ */

