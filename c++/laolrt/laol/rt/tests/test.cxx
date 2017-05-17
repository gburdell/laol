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
 * File:   test.cxx
 * Author: kpfalzer
 *
 * Created on May 14, 2017, 7:10 PM
 */

#include <stdlib.h>
#include <iostream>
#include <array>
#include <chrono>
#include <thread>
#include "laol/rt/runtime.hxx"

using namespace std;
using namespace laol::rt;

void test1() {
    cout << "test1: (basic) BEGIN" << endl;
    const long int I1 = 2048;
    LaolObj i1 = I1;
    ASSERT_TRUE(i1.toLongInt() == I1);
    auto i6 = i1++;
    ASSERT_TRUE(i6.toLongInt() == I1);
    ASSERT_TRUE(i1.toLongInt() == I1+1);
    ASSERT_TRUE((--i1).toLongInt() == I1);
    auto i2 = i1 << 4;
    auto d1 = 1.234 + i1;
    auto d2 = d1 * 45.67 / 678 - i2 * d1;
    auto i3 = 1234567 << i1;
    auto i4 = 123 | i3 | 123;
    double d3 = 1234 % 34;
    bool b1 = !'a';
    int d4 = ~1234;
    cout << "test1: END" << endl;
}

#ifdef NOPE
void test2() {
    cout << "test1: (Array) BEGIN" << endl;
    LaolObj i1 = 12 + 34;
    LaolObj i2 = i1;
    LaolObj ar1 = new Array();
    i2 = ar1 << i1 << (float) 1234.5; //cool: we get 1234 to LaolRef conversion!
    lcout << "1: ar1=" << ar1 << lendl;
    i1 = ar1("empty?");
    i1 = 7;
    i2 = new Array();
    i1 = i2("empty?");
    ASSERT_TRUE(toBool(i1));
    ar1 << "foobar" << 23 << 1.234 << i2;
    i1 = ar1("reverse")("reverse");
    lcout << "2: ar1=" << ar1 << lendl;
    ASSERT_TRUE(toBool(ar1[0] == 46));
    cout << "test1: END" << endl;
}
#endif

int main(int argc, char** argv) {
    test1();
    //test2();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

