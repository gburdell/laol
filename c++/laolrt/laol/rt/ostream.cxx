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
#include "laol/rt/exception.hxx"

namespace laol {
    namespace rt {

        struct Endl : public Laol {

            Endl() {
            }

            static const LaolObj NL;

            Ref call(const LaolObj& self, const LaolObj& args) const {
                return unconst(args).operator()("flush", NULLOBJ);
            }

            LaolObj toString() const override {
                return NL;
            }

            const METHOD_BY_NAME& getMethodByName() override {
                if (stMethodByName.empty()) {
                    stMethodByName = Laol::join(stMethodByName,
                            METHOD_BY_NAME({
                        {"call", static_cast<TPMethod> (&Endl::call)}
                    }));
                }
                return stMethodByName;
            }

            NO_COPY_CONSTRUCTORS(Endl);

            static METHOD_BY_NAME stMethodByName;

        };


        //static 
        const LaolObj Endl::NL = new String("\n");

        //static
        Laol::METHOD_BY_NAME Endl::stMethodByName;

        class StdOStream : public OStream {
        public:

            explicit StdOStream(std::ostream& os)
            : m_os(os) {
            }

            Ref print(const LaolObj& self, const LaolObj& args) const override {
                /*todo
                if (args.isBool()) {
                    m_os << String::toStdString(args);
                    unconst(args).ifMethod("call", self);
                } else {
                    args.primitiveApply([this](auto val) {
                        m_os << val;
                        return val;
                    });
                }
                 */
                return self;
            }

            Ref
            flush(const LaolObj& self, const LaolObj&) const override {
                flush();
                return self;
            }

            void flush() const {
                m_os.flush();
            }

            NO_COPY_CONSTRUCTORS(StdOStream);

            ~StdOStream() {
                flush();
            }

        private:
            std::ostream& m_os;

        };

        /*extern*/ const LaolObj lcout = new StdOStream(std::cout);
        /*extern*/ const LaolObj lcerr = new StdOStream(std::cerr);
        /*extern*/ const LaolObj lendl = new Endl();

        //static
        Laol::METHOD_BY_NAME OStream::stMethodByName;

        Ref
        OStream::append_SELF(const LaolObj& self, const LaolObj& args) const {
            return print(self, args);
        }

        Ref
        OStream::println(const LaolObj& self, const LaolObj& args) const {
            print(self, args);
            print(self, Endl::NL);
            return flush(self, NULLOBJ);
        }

        const Laol::METHOD_BY_NAME&
        OStream::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = {
                    {"append!", static_cast<TPMethod> (&OStream::append_SELF)},
                    {"flush", static_cast<TPMethod> (&OStream::flush)},
                    {"print", static_cast<TPMethod> (&OStream::print)},
                    {"println", static_cast<TPMethod> (&OStream::println)}
                };
            }
            return stMethodByName;
        }

        OStream::~OStream() {
        }

        //static
        Laol::METHOD_BY_NAME FileOutputStream::stMethodByName;

        const Laol::METHOD_BY_NAME&
        FileOutputStream::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(
                        stMethodByName,
                        OStream::getMethodByName(), METHOD_BY_NAME({
                    {"close", static_cast<TPMethod> (&FileOutputStream::close)},
                    {"fail_PRED", static_cast<TPMethod> (&FileOutputStream::fail_PRED)}
                }));
            }
            return stMethodByName;
        }

        FileOutputStream::FileOutputStream(const LaolObj& fileName)
        : m_fileName(String::toStdString(fileName)),
        m_ofs(m_fileName) {
            if (m_ofs.fail()) {
                throw FileException(m_fileName, "could not open file for write");
            }
        }

        Ref
        FileOutputStream::print(const LaolObj& self, const LaolObj& args) const {
            if (args.isBool()) {
                unconst(this)->m_ofs << String::toStdString(args);
                unconst(args).ifMethod("call", self);
            } else {
                /*todo
                args.primitiveApply([this](auto val) {
                    unconst(this)->m_ofs << val;
                    return val;
                });
                 */;
            }
            return self;
        }

        Ref
        FileOutputStream::close(const LaolObj& self, const LaolObj&) const {
            unconst(this)->close();
            return self;
        }

        Ref
        FileOutputStream::fail_PRED(const LaolObj&, const LaolObj&) const {
            return m_ofs.fail();
        }

        Ref
        FileOutputStream::flush(const LaolObj& self, const LaolObj&) const {
            unconst(this)->m_ofs.flush();
            return self;
        }

        void
        FileOutputStream::close() {
            m_ofs.close();
        }

        FileOutputStream::~FileOutputStream() {
            close();
        }

    }
}
