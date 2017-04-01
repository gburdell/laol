/*
 * The MIT License
 *
 * Copyright 2016 gburdell.
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

import laol.rt.primitives.Array;
import laol.rt.primitives.Integer;
import org.junit.Test;
import static org.junit.Assert.*;
import laol.rt.primitives.Number;

/**
 *
 * @author gburdell
 */
public class LaolArrayTest {

    @Test
    public void testAdd() {
//        {
//            final Array dut = (new Array()).setMutable();
//            dut.add(new Integer(1234)).add(new Integer(5678));
//            assertEquals(new Integer(2), dut.size());
//        }
        //
        //Test as code generator would be
        {
            final Laol dut = (new Array()).setMutable();
            Integer ele = new Integer(1234);
            Laol res = dut
                    .callPublic("add", ele)
                    .callPublic("add", ele);
            assertTrue(2 == Number.toInteger(res.callPublic("size")).get());
        }
    }

    @Test
    public void testSet() {
    }

    @Test
    public void testGet() {
//        final Array dut = (new Array()).setMutable();
//        final Integer vals[] = {
//            new Integer(1234),
//            new Integer(5678)
//        };
//        dut.add(vals[0]).add(vals[1]);
//        Laol ix = new Integer(0);
//        Integer v1 = dut.get(ix);
//        assertEquals(vals[0], v1);
//        assertTrue(!v1.isMutable());
//        Integer v2 = dut.get(new Integer(1));
//        assertEquals(vals[1], v2);
    }

    @Test
    public void testIsEmpty() {
    }

    @Test
    public void testSize() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testEquals() {
    }

}
