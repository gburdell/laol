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
 * File:   timer.hxx
 * Author: kpfalzer
 *
 * Created on May 8, 2017, 11:33 AM
 */

#ifndef _laol_rt_timer_hxx_
#define _laol_rt_timer_hxx_

#include <ctime>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {
        //NOTE: intentionally verbose to flush through feasibility

        class ITimer : public virtual Laol {
        public:
            virtual Ref hhmmss(const LaolObj& self, const LaolObj& args) const = 0;
            virtual Ref elapsed(const LaolObj& self, const LaolObj& args) const = 0;
            virtual Ref time(const LaolObj& self, const LaolObj& args) const = 0;

            virtual ~ITimer() = 0;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            static METHOD_BY_NAME stMethodByName;
        };

        class Timer : public virtual Laol, public ITimer {
        public:
            explicit Timer();

            virtual Ref hhmmss(const LaolObj& self, const LaolObj& args) const override;
            virtual Ref elapsed(const LaolObj& self, const LaolObj& args) const override;
            virtual Ref time(const LaolObj& self, const LaolObj& args) const override;

            //allow copy constructors

            virtual ~Timer();

        protected:
            virtual const METHOD_BY_NAME& getMethodByName() override;

        private:
            auto elapsed() const;

            time_t m_start;

            static METHOD_BY_NAME stMethodByName;
        };
    }
}

#endif /* _laol_rt_timer_hxx_ */

