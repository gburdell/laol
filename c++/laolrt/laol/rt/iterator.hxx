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
 * File:   iterator.hxx
 * Author: kpfalzer
 *
 * Created on April 23, 2017, 3:55 PM
 */

#ifndef _laol_rt_iterator_hxx_
#define _laol_rt_iterator_hxx_

#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {
        // Iterator interface

		//NOTE/TODO:
		//this is not used except in String: which is ifdef'd out.
		//need to rethink this: even Array dont use!

        class Iterator : public virtual Laol {
        public:
            //allow copy constructors

            //unique methods
            virtual Ref next_PRED(const LaolObj&, const LaolObj&) const;
            virtual Ref next(const LaolObj&, const LaolObj&) const;
            virtual Ref foreach(const LaolObj& self, const LaolObj& lambda) const = 0;
            
            virtual ~Iterator() {
            };

		protected:
			virtual const METHOD_BY_NAME& getMethodByName() override;

            // Implementation for builtins: String, Array, ...
            virtual Ref hasNext() const = 0;

            virtual Ref xnext() = 0;

        private:
            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_iterator_hxx_ */

