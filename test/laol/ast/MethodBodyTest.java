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
import laol.test.TestRunner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class MethodBodyTest extends TestRunner {

    private final String TESTS[] = {
        "1a=b",
        "2if(a)\nx=y\nelse\ny=x\nerg=43"
    };

    @Override
    public Acceptor getGrammar() {
        return new laol.parser.apfe.MethodBody();
    }

    @Override
    public String getTest(String test) {
        m_expectCnt = Integer.parseInt(test.substring(0, 1));
        return test.substring(1);
    }

    @Override
    public void generateAndTestAst(Acceptor parsed) {
        laol.ast.MethodBody dut = new laol.ast.MethodBody((laol.parser.apfe.MethodBody) parsed);
        assertTrue(m_expectCnt == dut.getBody().size());
        assertTrue(m_test.equals(m_accepted));
    }

    private int m_expectCnt = Integer.MAX_VALUE;

    @Test
    public void testMethodBody() {
        TestRunner runner = new MethodBodyTest();
        runner.runTests(TESTS);
    }
}
