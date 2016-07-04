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
package laol.ast;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author gburdell
 */
public class NumberTest {

    public NumberTest() {
    }

    private static void runTest(final String s2) {
        //
        Class cls;
        switch (s2.charAt(0)) {
            case 'B':
                cls = Number.Based.class;
                break;
            case 'F':
                cls = Number.Float.class;
                break;
            default:
                cls = Number.Integer.class;
        }
        String s1 = s2.substring(3);
        CharBuffer cbuf = new CharBuffer("<none>", s1);
        CharBufState st = CharBufState.create(cbuf, true);
        laol.parser.apfe.Number gram = new laol.parser.apfe.Number();
        Acceptor acc = gram.accept();
        assertNotNull(acc);
        System.out.print(": parse OK");
        Number dut = new Number(gram);
        assertTrue(cls == dut.getVal().getClass());
        System.out.print(": type OK");
        String t1 = dut.toString();
        assertTrue(s1.equals(t1));
        System.out.println(": match OK");
    }

    @Test
    public void testNumberConstructor() {
        String dat[] = new String[]{
            "B=>'b0001_1",
            "B=>'habd_098",
            "B=>8'habd_098",
            "I=>123_456",
            "B=>32'd123_456",
            "F=>123.456e-09",
            "F=>0.123",
            "F=>1.234e+4",
            "F=>1_23_.4e0_9"
        };
        for (String s : dat) {
            System.out.print("Test: " + s);
            runTest(s);
        }
    }
}
