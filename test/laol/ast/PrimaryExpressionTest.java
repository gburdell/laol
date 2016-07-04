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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class PrimaryExpressionTest {

    private static void runTest(final String s1) {
        CharBuffer cbuf = new CharBuffer("<none>", s1);
        CharBufState.create(cbuf, true);
        laol.parser.apfe.PrimaryExpression gram = new laol.parser.apfe.PrimaryExpression();
        Acceptor acc = gram.accept();
        assertNotNull(acc);
        System.out.print(": parse OK");
        PrimaryExpression dut = new PrimaryExpression(gram);
        /*
        assertTrue(cls == dut.getVal().getClass());
        System.out.print(": type OK");
        String t1 = dut.toString();
        assertTrue(s1.equals(t1));
         */
        System.out.println(": match TBD");
    }

    @Test
    public void testPrimaryExpression() {
        String dat[] = new String[]{
            "'b0001_1",
            "nil?",
            "var_name?",
            "_here_is_another___",
            "a_bang !"
        };
        for (String s : dat) {
            System.out.print("Test: " + s);
            runTest(s);
        }
    }
}
