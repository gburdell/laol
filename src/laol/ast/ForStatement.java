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

import apfe.runtime.Sequence;
import java.util.List;
import laol.ast.etc.IStatements;

/**
 *
 * @author gburdell
 */
public class ForStatement extends Item implements IStatements {

    public ForStatement(final laol.parser.apfe.ForStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_varNm = getIdent(seq, 1);
        m_bounds = createItem(seq, 3);
        m_stmtClause = createItem(seq, 4);
    }

    public EnumerableExpression getBounds() {
        return m_bounds;
    }

    public StatementClause getStmtClause() {
        return m_stmtClause;
    }

    @Override
    public List<Statement> getStatements() {
        return getStmtClause().getStatements();
    }

    public Ident getVarNm() {
        return m_varNm;
    }

    private final Ident m_varNm;
    private final EnumerableExpression m_bounds;
    private final StatementClause m_stmtClause;
}
