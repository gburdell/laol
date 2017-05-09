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
#include <chrono>
#include <thread>
#include "xyzzy/date.hxx"
#include "laol/rt/runtime.hxx"

using namespace std;
using namespace laol::rt;

void test1() {
    LaolObj i1 = 12 + 34;
    LaolObj i2 = i1;
    LaolObj ar1 = new Array();
    i2 = ar1 << i1 << (float) 1234.5; //cool: we get 1234 to LaolRef conversion!
    i1 = ar1("empty?");
    i1 = 7;
    i2 = new Array();
    i1 = i2("empty?");
    ar1 << "foobar" << 23 << 1.234 << i2;
    i1 = ar1("reverse")("reverse");
    i2 = ar1("empty?");
    if (!toBool(i2)) {
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
    ar1 = ar1 + 0;
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
    LaolObj a1 = std::array<LaolObj, 2>{23, 34};
    ASSERT_TRUE(a1.isA<laol::rt::Array>());
    LaolObj a2 = std::vector<LaolObj>{56, 78};
    LaolObj v1 = a1[std::vector<LaolObj>{0}
    ];
    ASSERT_TRUE(23 == v1.toLInt());
    LaolObj o1({1, 2, 3});
    a1 << 56 << 78;
    LaolObj rng1 = new Range({0, 2});
    LaolObj rng2(rng1);
    LaolObj vv1 = toV(1, 2, 3);
    LaolObj sub1 = a1[rng2];
    ASSERT_TRUE(56 == sub1[-1].toLInt());
    LaolObj isEq = (sub1 == sub1);
    ASSERT_TRUE(isEq.toBool());
    ASSERT_TRUE(3 == vv1("length").toLInt());
    a1 = vv1.subscript_assign(0, 666);
    ASSERT_TRUE((a1 == toV(666, 2, 3)).toBool());
    vv1 = toV(11, 22, 33, 44, 55, 66, 77, 88, 99);
    ASSERT_TRUE(9 == vv1("length").toLInt());
    vv1.subscript_assign(toV(-1, -2, -3), toV('a', 'b', 'c'));
    string s = String::toStdString(vv1);
    s += "";
    lcout << vv1 << lendl << 1234 << lendl;
}

void test4() {
    LaolObj m1 = new Map({
        {"a", 123},
        {"b", toV('c', 'd')},
        {"c", new Map(
            {
                {1, 2},
                {3, 4}
            })}
    });
    LaolObj v1 = m1["a"];
    ASSERT_TRUE((v1 == 123).toBool());
    lcout << m1 << lendl;
    m1("merge!", new Map({
        {"c", toV(1, 2, 3, 4)}
    }));
    lcout << "after m1.merge!: " << m1 << lendl;
    LaolObj m2 = m1("merge", new Map({
        {"e", 8967.345},
        {"foobar", "abcdef"}
    }));
    lcout << "after m1.merge" << lendl << "m1: " << m1 << lendl << "m2: " << m2 << lendl;
    LaolObj m3 = m1 + m2;
    lcout << "m3.size=" << m3("size") << lendl;
    m3("subscript_assign", toV("e", "was float: now abcdef"));
    lcout << "m3: " << m3 << lendl;
}

void test5() {
    {
        LaolObj sym1 = Symbol::sym("foobar");
        LaolObj sym2 = Symbol::sym("foobar");
    }
    LaolObj sym3 = Symbol::sym("foobar");
    LaolObj ar1 = new Array();
    lcout << "sym3: " << sym3 << lendl;
    for (auto i = 0; i < 1000; i++) {
        ar1 << sym3;
    }
    lcout << "ar1[123] == ar1[678]: " << (ar1[123] == ar1[678]) << lendl;
}

void test6() {
    LaolObj fis = new FileInputStream("/Users/kpfalzer/projects/laol/test/data/t1.laol");
    lcout << "fis.fail?: " << fis("fail?") << lendl;
    LaolObj rval;
    bool empty;
    LaolObj lineCount = 0;
    fis("eachLine", new Lambda([&](auto v) {
        rval = v;
        empty = toBool(rval("empty?"));
        if (!toBool(empty)) {
            lineCount++;
        }
    }));
    fis("close");
}

/* An Example
 *  Laol                || C++
 * ===================================================
 *  c1 = C1.new(v1,v2)  || LaolObj c1 = new C1(v2,v2);
 *  a = c1.a1           || LaolObj a = c1("a1");
 *  b = c1.a2 = [1,2,3] || LaolObj b = c1("a2=", toV(1,2,3));
 *  c1.a2[-1] = 123      || c1("a2")("subscript_assign", toV(-1, 123));
 *  def incr() {...} .   || ...
 */
class C1 : public Laol {
public:

    explicit C1(const LaolObj& a1, const LaolObj& a2)
    : m_a1(a1), m_a2(a2) {
    }

    LaolObj a1(const LaolObj& self, const LaolObj& args) const {
        return m_a1;
    }

    LaolObj a1_assign(const LaolObj& self, const LaolObj& args) const {
        return unconst(this)->m_a1 = args;
    }

    LaolObj a2(const LaolObj& self, const LaolObj& args) const {
        return m_a2;
    }

    LaolObj a2_assign(const LaolObj& self, const LaolObj& args) const {
        return unconst(this)->m_a2 = args;
    }

    /*
     * def incr {return a1 = a1 + 1}
     */
    LaolObj incr(const LaolObj& self, const LaolObj& args) const {
        return self("a1=", self("a1") + 1);
    }

    const Laol::METHOD_BY_NAME& getMethodByName() override {
        if (stMethodByName.empty()) {
            stMethodByName = Laol::join(Laol::getMethodByName(),{
                {"a1", static_cast<TPMethod> (&C1::a1)},
                {"a1=", static_cast<TPMethod> (&C1::a1_assign)},
                {"a2", static_cast<TPMethod> (&C1::a2)},
                {"a2=", static_cast<TPMethod> (&C1::a2_assign)},
                {"incr", static_cast<TPMethod> (&C1::incr)}
            });
        }
        return stMethodByName;
    }

protected:
    static METHOD_BY_NAME stMethodByName;

private:
    LaolObj m_a1, m_a2;
};

//static
Laol::METHOD_BY_NAME C1::stMethodByName;

struct C2 : public C1 {

    C2()
    : C1(123, 456) {
    }

};

void test7() {
    LaolObj c1 = new C1(123, 345);
    LaolObj a = c1("a1");
    LaolObj b = c1("a2=", toV(1, 2, 3));
    c1("a2")("subscript_assign", toV(-1, 123));
    lcout << "test7: c1.a2=" << c1("a2") << lendl
            << "test7: c1.incr: " << c1("incr") << lendl
            << "test7: c1.incr: " << c1("incr") << lendl;
    LaolObj c2 = new C2();
    lcout << "test7: c2.a1=" << c2("a1");
    c2("incr");
    lcout << ", c1.incr()=" << c2("a1") << lendl;
}

void test8() {
    //color codes: http://www.cplusplus.com/forum/beginner/1640/
    char blue[] = {0x1b, '[', '1', ';', '3', '4', 'm', 0};
    char normal[] = {0x1b, '[', '0', ';', '3', '9', 'm', 0};
    cout << "test8: should be blue: "
            << blue << "is this blue?" << normal << endl;
}

//TODO: this issue is no longer true: since we use reinterpret-cast!
//However, need to make sure base-class methodByName created before...
//
//NOTE: here B1 and B2 are pure interfaces; thus, not derived from Laol.
// Cannot do partial interface, since would require derived from 'virtual Laol'
// and we get: error: conversion from pointer to member of class 'B1' 
// to pointer to member of class 'laol::rt::Laol' via virtual base 'laol::rt::Laol' 
// is not allowed
#ifdef NOPE

struct B1 {//: public Laol {
    virtual LaolObj m1(const LaolObj& self, const LaolObj& args) const = 0;
    virtual ~B1() = 0;

};

struct B2 {//: public Laol {
    virtual LaolObj m2(const LaolObj& self, const LaolObj& args) const = 0;
    virtual ~B2() = 0;

};

B1::~B1() {
}

B2::~B2() {
}

struct D1 : public Laol, B1, B2 {

    LaolObj m1(const LaolObj& self, const LaolObj& args) const override {
        return args + 20;
    }

    LaolObj m2(const LaolObj& self, const LaolObj& args) const override {
        return args + 99;
    }

    virtual ~D1() {
    }

    Laol::TPMethod
    getFunc(const string& methodNm) const override {
        if (stMethodByName.empty()) {
            stMethodByName = {
                {"m1", static_cast<TPMethod> (&D1::m1)},
                {"m2", static_cast<TPMethod> (&D1::m2)}
            };
        }
        return Laol::getFunc(stMethodByName, methodNm);
    }

    static METHOD_BY_NAME stMethodByName;
};
Laol::METHOD_BY_NAME D1::stMethodByName;

void test9() {
    LaolObj d1 = new D1();
    d1("m1", 100);
    d1("m2", 123456);
}
#else

void test9() {
}
#endif //NOPE

void test10() {
    cout << "Current time: " << xyzzy::Date::timeToString() << endl;
    LaolObj t1 = new Timer();
    for (auto i = 0; i < 32; i++) {
        std::this_thread::sleep_for(std::chrono::seconds(2));
        lcout << "elapsed: " << t1("elapsed") << ": " << t1("hhmmss") << " (hh:mm:ss)" << lendl;
    }
}

struct A1 {
};

struct A2 {
};

struct A3 {

    A1 operator[](int i) {
        return a1;
    }

    A2 operator[](int i) const {
        return a2;
    }
    
    A1 a1; A2 a2;
};

template<typename T>
int vat(const A1& a1, T v) {
    return v;
}
template<typename T, typename... Args>
int vat(const A1& a1, T first, Args... args) {
    auto r = first + vat(a1, args...);
    return r;
}

void test11() {
    A3 a3;
    const A3 a3c;
    A1 a1;
    a3[45] = a1;
    A2 a2 = ((const A3&)a3)[123];
    a2 = a3c[456];
    auto v = vat(a1, 1,2,3,4,5);
    v += 0;
}

int main(int argc, char** argv) {
    test1();
    test2();
    test3();
    test4();
    test5();
    test6();
    test7();
    test8();
    test9();
    test11();
    test10();
    cout << "END: all tests" << endl;
    return (EXIT_SUCCESS);
}

