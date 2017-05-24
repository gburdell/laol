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

#include "laol/rt/primitives.hxx"

namespace laol {
    namespace rt {

        unsigned int INumber::toUnsignedInt(const LaolObj& v) {
            const EType type = v.toType<INumber>().getType();
            switch (type) {
                case eChar: case eUnsignedInt:
                    unsigned int rval;
                    getIntVal(v, type, [&rval](auto x) {
                        rval = (unsigned int) x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        int INumber::toInt(const LaolObj& v) {
            const EType type = v.toType<INumber>().getType();
            switch (type) {
                case eChar: case eInt:
                    int rval;
                    getIntVal(v, type, [&rval](auto x) {
                        rval = (int) x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        long int INumber::toLongInt(const LaolObj& v) {
            const EType type = v.toType<INumber>().getType();
            switch (type) {
                case eChar: case eInt: case eUnsignedInt: case eLongInt:
                    long int rval;
                    getIntVal(v, type, [&rval](auto x) {
                        rval = (long int) x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        unsigned long int INumber::toUnsignedLongInt(const LaolObj& v) {
            const EType type = v.toType<INumber>().getType();
            switch (type) {
                case eUnsignedLongInt:
                    unsigned long int rval;
                    getIntVal(v, type, [&rval](auto x) {
                        rval = (unsigned long int) x;
                        return NULLOBJ; //unused
                    });
                    return rval;
                default:
                    break;
            }
            ASSERT_NEVER;
            return 0;
        }

        double INumber::toDouble(const LaolObj& v) {
            double rval;
            getVal(v, [&rval](auto x) {
                rval = x;
                return NULLOBJ; //unused
            });
            return rval;
        }

        float INumber::toFloat(const LaolObj& v) {
            float rval;
            getVal(v, [&rval](auto x) {
                rval = x;
                return NULLOBJ; //unused
            });
            return rval;
        }

    }
}
