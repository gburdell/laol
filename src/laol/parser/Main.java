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
package laol.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import apfe.runtime.State;
import laol.parser.apfe.Grammar;

/**
 *
 * @author kpfalzer
 */
public class Main {

    public static void main(String argv[]) {
        try {
            for (String fn : argv) {
                InputStream fis = new InputStream(fn);
                System.out.println("Info: " + fn);
                CharBuffer cbuf = fis.newCharBuffer();
                CharBufState.create(cbuf, true);
                Grammar gram = new Grammar();
                Acceptor acc = gram.accept();
                if (null != acc) {
                    String ss = acc.toString();
                    System.out.println("returns:\n========\n" + ss);
                }
                boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
                if (!result) {
                    ParseError.printTopMessage();
                }
                gblib.Util.assertTrue(result, "unexpected");
            }
        } catch (Exception ex) {
            gblib.Util.abnormalExit(ex);
        }
        if (null != State.getTheOne()) {
            final long stats[] = State.getTheOne().getMemoizeStats();
            final double hitPcnt = (100.0 * stats[0]) / stats[1];
            System.out.printf("Memoize hit rate: %.1f%%\n", hitPcnt);
        }
    }

}
