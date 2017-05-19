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
 * File:   array.hxx
 * Author: kpfalzer
 *
 * Created on April 16, 2017, 3:55 PM
 */

#ifndef _laol_rt_array_hxx_
#define _laol_rt_array_hxx_

#include <vector>
#include "laol/rt/laol.hxx"
#include "laol/rt/exception.hxx"    

namespace laol {
    namespace rt {

        // Base/interface of arrays

        class IArray : public virtual Laol {
        public:

            /*operators need to be defined in subclass
            virtual Ref subscript(const LaolObj& self, const LaolObj& opB) const = 0;
            virtual LaolObj equal(const LaolObj& self, const LaolObj& opB) const = 0;
            virtual LaolObj toString(const LaolObj& self, const LaolObj& args) const = 0;
            */

            //unique methods

            virtual LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const;
            virtual LaolObj reverse(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj reverse_SELF(const LaolObj& self, const LaolObj& args) const = 0;
            virtual LaolObj length(const LaolObj& self, const LaolObj& args) const;

            //single element access via 0-origin ix
            virtual Ref operator[](size_t ix) const = 0;
            virtual size_t xlength() const = 0;
            virtual bool isEmpty() const = 0;

            size_t actualIndex(long int ix) const;

            virtual ~IArray() = 0;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:

            static METHOD_BY_NAME stMethodByName;
        };

        template<typename T>
        class PTArray : public IArray {
        public:

            explicit PTArray() {
            }

            PTArray& operator=(const PTArray& r) = delete;

            //operators

            virtual LaolObj equal(const LaolObj&, const LaolObj& opB) const override {
                LaolObj isEqual = false;
                if (opB.isA<IArray>()) {
                    const IArray& other = opB.toType<IArray>();
                    const auto N = xlength();
                    if (N == other.xlength()) {
                        for (auto i = 0; i < N; i++) {
                            if ((m_ar[i] != other[i]).toBool()) {
                                return false;
                            }
                        }
                        isEqual = true;
                    }
                }
                return isEqual;
            }

            virtual LaolObj toString(const LaolObj& self, const LaolObj& args) const override {
                return "!!TODO!!";
            }

            virtual LaolObj reverse(const LaolObj&, const LaolObj&) const override {
                auto p = new PTArray<T>(m_ar);
                std::reverse(std::begin(p->m_ar), std::end(p->m_ar));
                return p;
            }

            virtual LaolObj reverse_SELF(const LaolObj& self, const LaolObj&) const override {
                std::reverse(std::begin(unconst(this)->m_ar), std::end(unconst(this)->m_ar));
                return self;
            }

            virtual bool isEmpty() const override {
                return m_ar.empty();
            }

            virtual size_t xlength() const override {
                return m_ar.size();
            }

            virtual Ref operator[](size_t ix) const override {
                return m_ar[ix];
            }
            
            virtual ~PTArray() {
            }

            typedef std::vector<T> Vector;

            Vector& operator<<(const T& item) {
                m_ar.push_back(item);
                return m_ar;
            }

        protected:

            PTArray(const Vector& from) : m_ar(from) {
            }

            Vector m_ar;
        };

        // Array of LaolObj

        class Array : public PTArray<LaolObj> {
        public:

            explicit Array();

            explicit Array(const LaolObj& v);
            
            Array(Args v);

            Array& operator=(const Array& r) = delete;

            //operators
            virtual Ref subscript(const LaolObj& self, const LaolObj& opB) const override;

            virtual LaolObj left_shift(const LaolObj& self, const LaolObj& opB) const override;
            virtual LaolObj right_shift(const LaolObj& self, const LaolObj& opB) const override;

            virtual ~Array() {
            }

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            static METHOD_BY_NAME stMethodByName;
        };

        // Array of Ref

        class ArrayOfRef : public PTArray<Ref> {
        public:

            explicit ArrayOfRef() {
            }

            NO_COPY_CONSTRUCTORS(ArrayOfRef);

            //operators
            virtual Ref subscript(const LaolObj& self, const LaolObj& opB) const override;
        };

    }
}

#endif /* _laol_rt_array_hxx_ */

