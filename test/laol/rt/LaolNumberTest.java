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

import laol.rt.primitives.Double;
import laol.rt.primitives.Integer;
import java.util.stream.Stream;
import static laol.rt.primitives.Number.toDouble;
import static laol.rt.primitives.Number.toInteger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class LaolNumberTest {

    @Test
    public void testAddOp() {
        int i = 1;
        final int tests[][] = {{123, 456}, {java.lang.Integer.MAX_VALUE, 1}};
        Laol a;
        Laol b;
        Laol z;
        for (int test[] : tests) {
            a = new Integer(test[0]);
            b = new Integer(test[1]);
            try {
                z = a.callPublic("addOp", b); //a.addOp(b);
                assertTrue(test[0] + test[1] == toInteger(z).get());
            } catch (LaolException ex) {
                assertTrue(i == tests.length);
            }
            i++;
        }
        assertTrue(i >= tests.length);
        Integer x[] = Stream.of(1, 2, 3).map(e -> new Integer(e)).toArray(Integer[]::new);
        assertTrue(6 == toInteger(x[0].callPublic("addOp", x[1]).callPublic("addOp", x[2])).get());
        a = new Double(1.234);
        b = new Integer(4);
        assertTrue(5.234 == toDouble(a.callPublic("addOp", b)).get());
        assertTrue(5.234 == toDouble(b.callPublic("addOp", a)).get());
    }

    @Test
    public void testSubOp() {
    }

    @Test
    public void testMultOp() {
    }

    @Test
    public void testDivOp() {
    }

    @Test
    public void testPreIncrOp() {
        double ival = 1.234;
        Double instance = new Double(ival);
        instance.setMutable();
        Double expResult = new Double(ival + 1);
        Double result = (Double) instance.preIncrOp();
        assertEquals(expResult, instance);
    }
}
