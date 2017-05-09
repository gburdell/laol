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
 * File:   symbol.hxx
 * Author: kpfalzer
 *
 * Created on May 1, 2017, 1:54 PM
 */

#ifndef _laol_rt_symbol_hxx_
#define _laol_rt_symbol_hxx_

#include <unordered_map>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {

        /*
         * Include a :symbol, as in Ruby.
         * In Ruby, :symbol speeds up key index (maps using :symbol, instead of string).
         * It will be an interesting benchmark in Laol to see: since std::string is 
         * probably optimized...
         */
        class Symbol : public Laol {
        public:
            typedef unsigned long int VAL;

            //There are no public constructors, since we want to lookup pre-existing symbols.

            // Return existing symbol; or create new one.
            static LaolObj sym(const string& s);

            virtual LaolObj toString(const LaolObj& self, const LaolObj& args) const override;

            virtual LaolObj equal(const LaolObj& self, const LaolObj& opB) const override;

            NO_COPY_CONSTRUCTORS(Symbol);

            virtual ~Symbol();

		protected:
			virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            const VAL m_val;

            explicit Symbol() : m_val(stLastVal++) {
            }

            const string& toString() const;

            typedef std::unordered_map<string, LaolObj> MAP;
            static MAP stMap;
            static VAL stLastVal;

            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_symbol_hxx_ */

