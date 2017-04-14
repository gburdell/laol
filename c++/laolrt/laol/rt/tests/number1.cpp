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
#include "laol/rt/laol.hxx"

using namespace std;
using namespace laol::rt;

#ifdef NOPE
/*
 * Simple C++ Test Suite
 */
static auto getVal(const TRcLaol& n) {
    auto p = dynamic_cast<const Number*> (n.getPtr());
    return p->isDouble() ? p->toDouble() : p->toInt();
}

void test1() {
    cout << "BEGIN: test1" << endl;
    TRcLaol n1 = new Int(1), n2 = new Double(1.23), n3 = new Int(10);
    TRcLaol sum = n1 + n2 * n3;
    cout << "sum=" << getVal(sum) << endl;
    cout << "++sum=" << getVal(++(*sum)) << endl;
    TRcLaol sum1 = sum;   //TODO: needs to be copy
    sum1 = n2;
    sum1 = new Int(1234);
    TRcLaol ar1 = new Array();
    ++(*sum1); ++(*sum1); ++(*sum1);
    cout << "sum++=" << getVal((*sum)++) << endl;
    cout << "sum=" << getVal(sum) << endl;
    cout << "END: test1" << endl;
}

void test2() {
    cout << "BEGIN: test2" << endl;
    TRcLaol d1 = new Double(1.234);
    try {
        d1 << d1;
    }    catch (const Exception& ex) {
        cout << "Caught exception: " << ex.what() << endl;
    }
    cout << "END: test2" << endl;
}

/*
 *  Here is laol:
 *      a = [1,2,3]
 *      a[-1].map{|e| e + 1}.attrOrFunc
 *  in c++/laol/rt:
 *      TRcLaol c = new Array(...?);
 *      c[-1]->call("map", [](auto e){return e + 1;}).call("attrOrFunc")
 */
#endif //NOPE

void test1() {
    LaolRef i1 = 12 + 34;
    LaolRef i2 = i1;
    LaolRef ar1 = new Array();
    i2 = ar1 << i1 << 1234; //cool: we get 1234 to LaolRef conversion!
    i1 = "foobar";
}
void test2() {
    
}
int main(int argc, char** argv) {
    test1();
    test2();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

