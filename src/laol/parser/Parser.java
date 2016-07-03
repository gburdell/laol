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
package laol.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.ParseError;
import apfe.runtime.ScannerState;
import apfe.runtime.State;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import laol.parser.apfe.Grammar;

/**
 *
 * @author gburdell
 */
public class Parser {

    public boolean parse(final String... fileNames) {
        for (String fn : fileNames) {
            try {
                LaolScanner toks = LaolScanner.create(fn);
                State st = ScannerState.create(toks);
                Grammar gram = new Grammar();
                Acceptor acc = gram.accept();
                if (null != acc) {
                    String ss = acc.toString();
                    System.out.println("returns:\n========\n" + ss);
                }
                boolean result = (null != acc) && State.getTheOne().isEOF();
                if (!result) {
                    ParseError.printTopMessage();
                    m_errCnt++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                m_errCnt++;
            }
        }
        return (1 > m_errCnt);
    }

    private int m_errCnt = 0;

    public static void main(final String argv[]) {
        Parser p = new Parser();
        p.parse(argv);
    }
}
