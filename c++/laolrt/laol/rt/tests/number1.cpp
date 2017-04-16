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
#include "laol/rt/array.hxx"

using namespace std;
using namespace laol::rt;

void test1() {
    LaolRef i1 = 12 + 34;
    LaolRef i2 = i1;
    LaolRef ar1 = new Array();
    i2 = ar1 << i1 << (float)1234.5; //cool: we get 1234 to LaolRef conversion!
    i1 = "foobar";
    i1 = 7;
    i2 = new Array();
}
void test2() {
    
}
int main(int argc, char** argv) {
    test1();
    test2();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

