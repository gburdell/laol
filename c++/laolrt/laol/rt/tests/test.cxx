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
#include <climits>
#include <cfloat>
#include "laol/rt/runtime.hxx"

using namespace std;
using namespace laol::rt;

void test1() {
    cout << "test1: (basic) BEGIN" << endl;
    {
#ifdef TODO
        //we cannot do: Int i1 = 0   //BAD!
        Int i1(0);
        UnsignedInt i2(12);
        // error: invalid operands to binary expression ('laol::rt::Int' and 'laol::rt::UnsignedInt')
        i1 != i2;
#endif
    }
    const long int I1 = 2048;
    LaolObj i1 = I1;
    ASSERT_TRUE(i1.toLongInt() == I1);
    auto i6 = i1++;
    ASSERT_TRUE(i6.toLongInt() == I1);
    ASSERT_TRUE(i1.toLongInt() == I1 + 1);
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

void test2() {
    cout << "test2: (Array) BEGIN" << endl;
    {
        LaolObj i1 = 987654321;
        LaolObj ar1 = new Array();
        const float F1 = 1234.5;
        auto i2 = ar1 << i1 << F1; //cool: we get 1234 to LaolRef conversion!    
        LaolObj ix0 = ar1[0];
        const int I1 = 777, I2 = 45;
        ar1[0] = I1;
        ASSERT_TRUE(ar1[0].toLongInt() == I1);
        i2 = ar1[0] + I2;
        ASSERT_TRUE(i2.toLongInt() == I1 + I2);
        ar1[0] = ar1[1];
        ASSERT_TRUE(ar1[0].toDouble() == F1);
    }
    {
        //2d arrays
        const int I1 = 1, I2 = 1234, I3 = 7, I4 = 765;
        const auto V1 = toV(9, 8, I3);
        LaolObj ar1 = new Array(toV(I1, V1, 3, 4));
        auto ix1 = ar1[0];
        ar1[0] = I2;
        ASSERT_TRUE(ix1.toLongInt() == I1);
        ASSERT_TRUE(ar1[0].toLongInt() == I2);
        auto ix2 = ar1[-3];
        auto n = ix2("length");
        ASSERT_TRUE(n.toSizet() == 3);
        ASSERT_TRUE((V1 == ix2).toBool());
        auto ix3 = ix2[-2];
        ASSERT_TRUE(ix3.toLongInt() == 8);
        ASSERT_TRUE(ar1[-3][-2].toLongInt() == 8);
        auto a11 = ar1[1][-1];
        ASSERT_TRUE(a11.toLongInt() == I3);
        auto v1 = ar1[1][-1] = I4;
        ASSERT_TRUE(a11.toLongInt() == I3);
        ASSERT_TRUE(ar1[1][-1].toLongInt() == I4);
    }
    cout << "test2: END" << endl;
}

void test3(const int NN = 10000) {
    LaolObj N(NN);
    cout << "test3: (Array-Triangle): N=" << NN << " BEGIN" << endl;
    LaolObj triangle = new Array();
    LaolObj n(0);
    for (LaolObj rowi(1); toBool(rowi <= N); ++rowi) {
        LaolObj row = new Array();
        for (LaolObj j(0); toBool(j < rowi); ++j) {
            row << ++n;
        }
        triangle << row;
        //cout << "DBG: row.length=" << row("length").toSizet() << endl;
    }
    //for (LaolObj rowi(1); toBool(rowi <= N); rowi++) {
    //    ASSERT_TRUE(toBool(triangle[rowi-1]("length") == rowi));
    //}
    cout << "test3: n=" << n.toLongInt() << " END" << endl;
}

void test3b(const int NN = 10000) {
    cout << "test3b: (Array-Triangle-C++): N=" << NN << " BEGIN" << endl;
    vector<vector<int>> triangle;
    int n = 1;
    for (int rowi = 1; rowi <= NN; rowi++) {
        vector<int> row;
        for (int j = 0; j < rowi; j++) {
            row.push_back(n++);
        }
        triangle.push_back(row);
    }
    cout << "test3b: n=" << n << " END" << endl;
}

void test4(const int NN = 10000000) {
    cout << "test4: (post-increment): N=" << NN << " BEGIN" << endl;
    LaolObj i(0);
    for (int n = 0; n < NN; n++) {
        i++;
    }
    cout << "test4: n=" << i.toLongInt() << " END" << endl;
}

class C : public Laol {
public:

    explicit C(int v = 3) : m_v1(v) {
    }

    Ref v1(const LaolObj& self, const LaolObj&) {
        return m_v1;
    }

    const METHOD_BY_NAME& getMethodByName() override;

private:
    static METHOD_BY_NAME stMethodByName;

    LaolObj m_v1;
};

Laol::METHOD_BY_NAME C::stMethodByName;

const Laol::METHOD_BY_NAME& C::getMethodByName() {
    if (stMethodByName.empty()) {
        stMethodByName = {
            {"v1", reinterpret_cast<TPMethod> (&C::v1)}
        };
    }
    return stMethodByName;
}

void test5() {
    cout << "test5: (member accessor methods): BEGIN" << endl;
    const int I1 = 10, I2 = 12, I3 = -1234;
    LaolObj c1 = new C(I1);
    auto i1 = c1("v1") + I2;
    ASSERT_TRUE(i1.toInt() == I1+I2);
    c1("v1") = I3;
    ASSERT_TRUE(I3 == c1("v1").toInt());
    cout << "test5: END" << endl;
}

int main(int argc, char** argv) {
    test1();
    test2();
    test3();
    //test3b();
    //test4();
    test5();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

