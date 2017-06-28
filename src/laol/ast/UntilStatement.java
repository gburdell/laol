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

import apfe.runtime.Marker;
import apfe.runtime.Sequence;
import java.util.List;
import laol.ast.etc.IStatements;

/**
 *
 * @author gburdell
 */
public class UntilStatement extends Item implements IStatements {

    public UntilStatement(final laol.parser.apfe.UntilStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_expr = createItem(seq, 1);
        m_clause = createItem(seq, 2);
    }

    public StatementClause getClause() {
        return m_clause;
    }

    public Expression getExpr() {
        return m_expr;
    }

    @Override
    public List<Statement> getStatements() {
        return getClause().getStatements();
    }

    private final Expression m_expr;
    private final StatementClause m_clause;
}
