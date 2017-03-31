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
package laol.rt;

import gblib.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class LaolObjectTest {

    public class A extends LaolObject {

        public ILaol f1() {
            return new LaolCharacter('A');
        }
    }

    public class B extends A {

    }

    public class C extends B {

        @Override
        public ILaol f1() {
            return new LaolCharacter('C');
        }
    }

    @Test
    public void testInheritance() {
        ILaol obj1, r1;
        LaolCharacter c1;
        {
            obj1 = new C();
            r1 = obj1.cm("f1");
            c1 = Util.downCast(r1);
            assertTrue('C' == c1.get());
        }
        {
            A asA = new C();
            obj1 = asA;
            r1 = obj1.cm("f1");
            c1 = Util.downCast(r1);
            assertTrue('C' == c1.get());
        }
        {
            obj1 = new B();
            r1 = obj1.cm("f1");
            c1 = Util.downCast(r1);
            assertTrue('A' == c1.get());
        }
    }

}
