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
public class StatementTest extends TestRunner {

    private final String TESTS[] = {
        "dsl_ok p2, p2",  //method call ambiguous w/ var-decl
        "dsl_like p1, p2, a: :bar, b: foo, c: \"dog\"",
        "foo(bar: val, b: 34)",
        "dog(b: 34)",
        "foo.bar;",
        "a=b",
        "1+2^3-4",
        "total = by_sys.values.map{|h| h.values}.flatten.reduce(0){|m,o| m += o[:resource]}"
    };

    @Override
    public Acceptor getGrammar() {
        return new laol.parser.apfe.Statement();
    }

    @Override
    public void generateAndTestAst(Acceptor parsed) {
        laol.ast.Statement dut = new laol.ast.Statement((laol.parser.apfe.Statement) parsed);
        assertTrue(m_test.equals(m_accepted));
    }

    @Test
    public void testAccessModifier() {
        TestRunner runner = new StatementTest();
        runner.runTests(TESTS);
    }

}


