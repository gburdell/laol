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
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import gblib.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class GrammarTest {
    static {
        //dont clutter message with these
        ParseError.setSkipErrorHints("' '", "'\\t'", "'//'", "'/*'");
    }

    @Test
    public void testConstructor() {
        final String fns[] = {
            //"test/data/t2.txt",
            //"test/data/t1.txt",
            //"lib/laol/collection.laol",
            "test/data/t6.laol"
        };
        try {
            for (String fn : fns) {
                InputStream fis = new InputStream(fn);
                System.out.println("Info: " + fn);
                CharBuffer cbuf = fis.newCharBuffer();
                CharBufState.create(cbuf, true);
                laol.parser.apfe.Grammar gram = new laol.parser.apfe.Grammar();
                Acceptor acc = gram.accept();
                if (null != acc) {
                    String ss = acc.toString();
                    System.out.println("returns:\n========\n" + ss);
                }
                boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
                if (!result) {
                    ParseError.printTopMessage();
                }
                assertTrue(result);
                Grammar ast = new Grammar(gram);
            }
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
    }
    
}
