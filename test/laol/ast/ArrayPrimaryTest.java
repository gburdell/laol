/*
 * The MIT License
 *
 * Copyright 2016 kpfalzer.
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
import apfe.runtime.ParseError;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class ArrayPrimaryTest {
    
    private final String TESTS[] = {
        "3[1,2,3]",
        "4%w{abc def ghi jkl}"
    };

    //static so we can easily inspect
    private static laol.ast.ArrayPrimary dut = null;

    @Test
    public void testArrayPrimary() {
        int passCnt = 0;
        for (String test : TESTS) {
            final int expectCnt = Integer.parseInt(test.substring(0, 1));
            test = test.substring(1);
            System.out.println("Info: " + test);
            CharBuffer cbuf = new CharBuffer("<stdin>", test);
            CharBufState.create(cbuf, true);
            laol.parser.apfe.ArrayPrimary gram = new laol.parser.apfe.ArrayPrimary();
            Acceptor acc = gram.accept();
            if (null != acc) {
                String ss = acc.toString();
                System.out.println("parse returns: " + ss);
            }
            boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            } else {
                assertTrue(result);
                passCnt++;
                dut = new laol.ast.ArrayPrimary((laol.parser.apfe.ArrayPrimary) acc);
                ArrayPrimary.EType type = dut.getType();
                List<Item> eles = dut.getElements();
                assertTrue(expectCnt == eles.size());
            }
        }
        assertTrue(TESTS.length == passCnt);
    }
}
