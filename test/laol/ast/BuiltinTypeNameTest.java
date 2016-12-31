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
public class BuiltinTypeNameTest extends TestRunner {

    private final String TESTS[] = {
        "int",
        "double",
        "bool",
        "string",
        "{ /*foo*/}",
        "[  ]",
        "?",
        "int"
    };

    @Override
    public Acceptor getGrammar() {
        return new laol.parser.apfe.BuiltinTypeName();
    }

    @Override
    public void generateAndTestAst(Acceptor parsed) {
        laol.ast.BuiltinTypeName dut = new laol.ast.BuiltinTypeName((laol.parser.apfe.BuiltinTypeName) parsed);
        assertTrue(m_test.equals(m_accepted));
        final char first = m_test.trim().toLowerCase().charAt(0);
        BuiltinTypeName.EType type;
        switch (first) {
            case 'i': type = BuiltinTypeName.EType.eInt; break;
            case 'd': type = BuiltinTypeName.EType.eDouble; break;
            case 'b': type = BuiltinTypeName.EType.eBool; break;
            case 's': type = BuiltinTypeName.EType.eString; break;
            case '{': type = BuiltinTypeName.EType.eMap; break;
            case '[': type = BuiltinTypeName.EType.eArray; break;
            case '?': type = BuiltinTypeName.EType.eUnknown; break;
            default:
                type = null;
        }
        assertTrue(type == dut.getType());
    }

    @Test
    public void testAccessModifier() {
        TestRunner runner = new BuiltinTypeNameTest();
        runner.runTests(TESTS);
    }

}


