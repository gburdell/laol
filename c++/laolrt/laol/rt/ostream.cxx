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

#include <iostream>
#include "laol/rt/ostream.hxx"
#include "laol/rt/string.hxx"

namespace laol {
    namespace rt {

        struct Endl : public Laol {

            Endl() {
            }

            static const LaolObj NL;

            Laol::TPMethod
            getFunc(const string& methodNm) const override {
                return Laol::getFunc(stMethodByName, methodNm);
            }

            LaolObj
            call(LaolObj& self, LaolObj& args) {
                return args("flush", NULLOBJ);
            }

            LaolObj
            toString(LaolObj& self, LaolObj& args) override {
                return NL;
            }

            NO_COPY_CONSTRUCTORS(Endl);

            static METHOD_BY_NAME stMethodByName;

        };


        //static 
        const LaolObj Endl::NL = new String("\n");

        //static
        Laol::METHOD_BY_NAME
        Endl::stMethodByName = {
            {"call", static_cast<TPMethod> (&Endl::call)},
            {"toString", static_cast<TPMethod> (&Endl::toString)}
        };

        class StdOStream : public OStream {
        public:

            explicit StdOStream(std::ostream& os)
            : m_os(os) {
            }

            LaolObj append_SELF(LaolObj& self, LaolObj& args) override {
                if (args.isObject() || args.isBool()) {
                    m_os << String::toStdString(args);
                    unconst(args).ifMethod("call", self);
                } else {
                    args.primitiveApply([this](auto val) {
                        m_os << val;
                        return val;
                    });
                }
                return self;
            }

            LaolObj
            flush(LaolObj& self, LaolObj&) override {
                flush();
                return self;
            }

            void flush() const {
                unconst(this)->m_os.flush();
            }

            NO_COPY_CONSTRUCTORS(StdOStream);

            ~StdOStream() {
                flush();
            }

        private:
            std::ostream& m_os;

            static METHOD_BY_NAME stMethodByName;

        };

        //static
        Laol::METHOD_BY_NAME
        StdOStream::stMethodByName = {
            {"flush", static_cast<TPMethod> (&StdOStream::flush)}
        };

        /*extern*/ const LaolObj lcout = new StdOStream(std::cout);
        /*extern*/ const LaolObj lcerr = new StdOStream(std::cerr);
        /*extern*/ const LaolObj lendl = new Endl();


        //static
        Laol::METHOD_BY_NAME
        OStream::stMethodByName = {
            {"append!", static_cast<TPMethod> (&OStream::append_SELF)},
            {"flush", static_cast<TPMethod> (&OStream::flush)}
        };

        LaolObj
        OStream::flush(LaolObj& self, LaolObj&) {
            return self;
        }

        Laol::TPMethod
        OStream::getFunc(const string& methodNm) const {
            return Laol::getFunc(stMethodByName, methodNm);
        }

        OStream::~OStream() {

        }

    }
}
