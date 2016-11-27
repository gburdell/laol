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
package laol.test;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.ParseError;
import static gblib.Util.invariant;

/**
 *
 * @author kpfalzer
 */
public abstract class TestRunner {

    /**
     * Message test and possibly extract "goal" artifacts.
     * @param test
     * @return test to pass through to parser.
     */
    public String getTest(final String test) {
        return test;
    }
    
    public abstract Acceptor getGrammar();
    
    public abstract void generateAndTestAst(final Acceptor parsed);
    
    public String getString(final Acceptor acc) {
        String ss = acc.toString();
        return ss;
    }
    
    public void runTests(final String tests[]) {
        for (String test : tests) {
            m_test = getTest(test);
            System.out.println("Info: " + m_test);
            CharBuffer cbuf = new CharBuffer("<stdin>", m_test);
            CharBufState.create(cbuf, true);
            Acceptor gram = getGrammar();
            Acceptor acc = gram.accept();
            if (null != acc) {
                m_accepted = getString(acc);
                System.out.println("parse returns: " + m_accepted);
            }
            boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            } else {
                invariant(result);
                m_passCnt++;
                generateAndTestAst(acc);
            }
        }
        invariant(tests.length == m_passCnt);
    }
    
    protected int m_passCnt = 0;
    protected String m_test, m_accepted;
}
