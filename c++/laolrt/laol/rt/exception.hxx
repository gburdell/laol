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
 * File:   exception.hxx
 * Author: kpfalzer
 *
 * Created on April 25, 2017, 5:56 PM
 */

#ifndef _laol_rt_exception_hxx_
#define _laol_rt_exception_hxx_

#include <exception>
#include <string>

namespace laol {
    namespace rt {

        using std::string;

        string demangleName(const char* n);

        class Exception : public std::exception {
        public:
            explicit Exception(const string& reason);

            //allow copy constructors

            virtual const char* what() const noexcept;

        private:
            string m_reason;
        };

        class NoMethodException : public Exception {
        public:

            explicit NoMethodException()
            : Exception(REASON) {
            }

            explicit NoMethodException(const string& reason)
            : Exception(reason) {
            }

            explicit NoMethodException(const std::type_info &obj, const string& type, const string& op);

            explicit NoMethodException(const std::type_info &obj, const string& op)
            : NoMethodException(obj, "method", op) {
            }

            //allow copy constructors

        private:
            static const string REASON;
        };

        class NoOperatorException : public NoMethodException {
        public:

            explicit NoOperatorException(const std::type_info &obj, const string& op)
            : NoMethodException(obj, "operator", op) {
            }

            //allow copy constructors
        };

        class InvalidTypeException : public Exception {
        public:

            explicit InvalidTypeException(const std::type_info& found, const string& expected);

            explicit InvalidTypeException(const std::type_info& found, const std::type_info& expected) : InvalidTypeException(found, demangleName(expected.name())) {
            }

            //allow copy constructors
        };

        class IndexException : public Exception {
        public:

            explicit IndexException(const string& found, const string& expected);

            //allow copy constructors
        };
        
        class ArityException : public Exception {
        public:
            explicit ArityException(long int found, const string& expected);
            
            //allow copy constructors
        };
    }
}


#endif /* _laol_rt_exception_hxx_ */

