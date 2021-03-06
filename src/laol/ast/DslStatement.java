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
import apfe.runtime.Marker;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;
import laol.ast.etc.IStatementModifier;

/**
 *
 * @author gburdell
 */
public class DslStatement extends Item implements IStatementModifier {
    public DslStatement(final laol.parser.apfe.DslStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_name = createItem(seq, 0);
        m_params = createItem(seq, 1);
        m_stmtModifier = getStatementModifier(seq, 2);
    }

    public ScopedName getName() {
        return m_name;
    }

    public ParamExpressionList getParams() {
        return m_params;
    }

    @Override
    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }
   
    private final ScopedName    m_name;
    private final ParamExpressionList m_params;
    private final StatementModifier m_stmtModifier;
}
