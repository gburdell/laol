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
 * File:   number1.cpp
 * Author: kpfalzer
 *
 * Created on April 2, 2017, 7:10 PM
 */

#include <stdlib.h>
#include <iostream>
#include <array>
#include "laol/rt/laol.hxx"
#include "laol/rt/array.hxx"
#include "laol/rt/range.hxx"
#include "laol/rt/string.hxx"

using namespace std;
using namespace laol::rt;

void test1() {
    LaolObj i1 = 12 + 34;
    LaolObj i2 = i1;
    LaolObj ar1 = new Array();
    i2 = ar1 << i1 << (float)1234.5; //cool: we get 1234 to LaolRef conversion!
    i1 = ar1("empty?");
    i1 = 7;
    i2 = new Array();
    i1 = i2("empty?");
    ar1 << "foobar" << 23 << 1.234 << i2;
    i1 = ar1("reverse")("reverse");
    i2 = ar1("empty?");
    if (! toBool(i2)) {
        if (toBool("foobar")) {
            i2 = true;
        }
    }
    i1 = i2("toString");
    i1 = ar1("length");
    i1 = ar1("toString");
    i2 = 123456;
    i1 = 2;
    ar1 = i2 << i1 << i1;
    ar1 = ar1 + 1234.5;
    ar1 =  ar1 + 0;
    i1 = ar1("toString");
    i2 = 12;
}
void test2() {
    LaolObj s1 = "foo", s2 = "bar";
    LaolObj s3 = s1 + s2 + s1 + s2 + s2;
    LaolObj c1 = s3("at", 3);
    LaolObj i1 = 1234 + 345 % 45;
    i1 = c1 = i1;
}

void test3() {
    //subscript
    LaolObj a1 = std::array<LaolObj,2>{23,34};
    ASSERT_TRUE(a1.isA<laol::rt::Array>());
    LaolObj a2 = std::vector<LaolObj>{56,78};
    LaolObj v1 = a1[std::vector<LaolObj>{0}];
    ASSERT_TRUE(23 == v1.toLInt());
    LaolObj o1({1,2,3});
    a1 << 56 << 78;
    LaolObj rng1 = new Range({0,2});
    LaolObj rng2(rng1);
    LaolObj vv1 = toV(1,2,3);
    LaolObj sub1 = a1[rng2];
    ASSERT_TRUE(56 == sub1[-1].toLInt());
    LaolObj isEq = (sub1 == sub1);
    ASSERT_TRUE(isEq.toBool());
    ASSERT_TRUE(3 == vv1("length").toLInt());
    a1 = vv1.subscript_assign(0, 666);
    ASSERT_TRUE((a1 == toV(666,2,3)).toBool());
    vv1 = toV(11,22,33,44,55,66,77,88,99);
    ASSERT_TRUE(9 == vv1("length").toLInt());
    vv1.subscript_assign(toV(-1,-2,-3), toV('a','b','c'));
    string s = String::getString(vv1);
    s += "";
}

int main(int argc, char** argv) {
    test1();
    test2();
    test3();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

