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
import java.util.List;
import laol.test.TestRunner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class LhsRefTest extends TestRunner {
    private final String TESTS[] = {
        "2abc[123]",
        "2defx[45..67]",
        "2ghi.foobar",
        "3jkl.goop++",
        "1eek",
        "7a[4][a..b].beek++[123].fleep"
    };

    @Override
    public Acceptor getGrammar() {
        return new laol.parser.apfe.LhsRef();
    }

    @Override
    public String getTest(String test) {
        m_expectCnt = Integer.parseInt(test.substring(0, 1));
        return test.substring(1);
    }

    @Override
    public String getString(final Acceptor acc) {
        final laol.parser.apfe.LhsRef lhs = (laol.parser.apfe.LhsRef)acc;
        return lhs.getItems().get(0).toString();
    }

    @Override
    public void generateAndTestAst(Acceptor parsed) {
        laol.ast.LhsRef dut = new laol.ast.LhsRef((laol.parser.apfe.LhsRef) parsed);
        assertTrue(m_expectCnt == dut.getItems().size());
        assertTrue(m_test.equals(m_accepted));
    }

    private int m_expectCnt = Integer.MAX_VALUE;

    @Test
    public void testLhsRef() {
        TestRunner runner = new LhsRefTest();
        runner.runTests(TESTS);
    }
}
