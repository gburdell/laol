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

import static gblib.Util.downCast;
import static java.util.Objects.isNull;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class LaolMapTest {

    @Test
    public void testSet() {
        LaolMap dut = (new LaolMap()).setMutable();
        LaolString key = new LaolString("foo");
        LaolInteger val = new LaolInteger(1234);
        dut.set(key, val);
        LaolInteger from = downCast(dut.get(key));
        assertEquals(val, from);
        key = new LaolString("bar");
        from = downCast(dut.get(key));;
        assertTrue(isNull(from));
        {
            LaolDouble key2 = new LaolDouble(1.234);
            LaolString val2 = new LaolString("5.678");
            dut.set(key2, val2);
            LaolString from2 = downCast(dut.get(key2));
            assertEquals(from2, val2);
        }
        {
            Laol dutObj = dut;
            Laol key2 = new LaolString("key2");
            double d = 9876.4321;
            Laol val2 = new LaolDouble(d);
            Laol rval = dutObj.callPublic("set", key2, val2);
            assertEquals(val2, rval);
            rval = dutObj.callPublic("get", key2);
            assertEquals(val2, rval);
            String s = val2.toS().get();
            assertEquals(Double.toString(d), s);
        }
        {
            final int N = 2000;
            LaolMap map = new LaolMap().setMutable();
            Laol key3;
            Laol val3;
            for (int i = 0; i < N; i++) {
                key3 = new LaolInteger(i);
                val3 = new LaolString(key3.toS());
                map.callPublic("set", key3, val3);
                assertEquals(val3, map.callPublic("get", key3));
            }
        }
    }

    @Test
    public void testGet() {
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
