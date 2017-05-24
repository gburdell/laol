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
#include "laol/rt/timer.hxx"
#include "laol/rt/string.hxx"

namespace laol {
    namespace rt {

        /*static*/
        Laol::METHOD_BY_NAME ITimer::stMethodByName;

        const Laol::METHOD_BY_NAME&
        ITimer::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = {
                    {"hhmmss", reinterpret_cast<TPMethod> (&ITimer::hhmmss)},
                    {"elapsed", reinterpret_cast<TPMethod> (&ITimer::elapsed)},
                    {"time", reinterpret_cast<TPMethod> (&ITimer::time)}
                };
            }
            return stMethodByName;
        }

        ITimer::~ITimer() {
        }

        /*static*/
        Laol::METHOD_BY_NAME Timer::stMethodByName;

        Timer::Timer() {
            std::time(&m_start);
        }

        auto
        Timer::elapsed() const {
            time_t now;
            std::time(&now);
            return difftime(now, m_start);
        }

        Ref
        Timer::hhmmss(const LaolObj& self, const LaolObj&) const {
            static const int SECS_PER_MIN = 60;
            static const int SECS_PER_HOUR = 60 * SECS_PER_MIN;
            static const int SECS_PER_DAY = 24 * SECS_PER_HOUR;
            static char buf[128];
            int dd, hh, mm, ss = elapsed();
            dd = ss / SECS_PER_DAY;
            ss -= (dd * SECS_PER_DAY);
            hh = ss / SECS_PER_HOUR;
            ss -= (hh * SECS_PER_HOUR);
            mm = ss / SECS_PER_MIN;
            ss -= (mm * SECS_PER_MIN);
            if (0 < dd) {
                snprintf(buf, sizeof (buf), "%02d:%02d:%02d:%02d", dd, hh, mm, ss);
            } else {
                snprintf(buf, sizeof (buf), "%02d:%02d:%02d", hh, mm, ss);
            }
            return LaolObj(new String(buf));
        }

        Ref
        Timer::time(const LaolObj&, const LaolObj&) const {
            return LaolObj(std::time(0));
        }

        Ref
        Timer::elapsed(const LaolObj&, const LaolObj&) const {
            return LaolObj(elapsed());
        }

        const Laol::METHOD_BY_NAME&
        Timer::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(stMethodByName,
                        ITimer::getMethodByName());
            }
            return stMethodByName;
        }

        Timer::~Timer() {
        }

    }
}
