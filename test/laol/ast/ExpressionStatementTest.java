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
public class ExpressionStatementTest extends TestRunner {

    private final String TESTS[] = {
        "severity.toString.capitalize + \": \" + message",
        "foo.bar++ unless f==bar;",
        "bar[:foo][7].goop({a:5}) + 1",
        "fn(a:4,f:5,g[:bar]) << 45"
    };

    @Override
    public Acceptor getGrammar() {
        return new laol.parser.apfe.ExpressionStatement();
    }

    @Override
    public void generateAndTestAst(Acceptor parsed) {
        laol.ast.ExpressionStatement dut = new laol.ast.ExpressionStatement((laol.parser.apfe.ExpressionStatement) parsed);
        assertTrue(m_test.equals(m_accepted));
    }

    @Test
    public void testAccessModifier() {
        TestRunner runner = new ExpressionStatementTest();
        runner.runTests(TESTS);
    }

}
