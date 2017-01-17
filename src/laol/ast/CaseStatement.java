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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import gblib.Pair;
import gblib.Util;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

/**
 *
 * @author gburdell
 */
public class CaseStatement extends Item {

    public CaseStatement(final laol.parser.apfe.CaseStatement decl) {
        super(decl);
        m_expr = createItem(1);
        Repetition rep = asRepetition(3);
        for (Acceptor whens : rep.getAccepted()) {
            addWhen(whens);
        }
        rep = asRepetition(4);
        if (0 < rep.sizeofAccepted()) {
            m_else = createItem(asSequence(rep.getOnlyAccepted()), 1);
        } else {
            m_else = null;
        }
        m_stmtModifier = getStatementModifier(asSequence(), 6);
    }

    public Expression getExpr() {
        return m_expr;
    }

    public List<WhenClause> getWhenClauses() {
        return Collections.unmodifiableList(m_alts);
    }

    public Statement getElse() {
        return m_else;
    }

    private void addWhen(final Acceptor whenClause) {
        final Sequence clause = apfe.runtime.Util.downcast(whenClause);
        final ExpressionList exprs = createItem(clause, 1);
        final Statement stmt = createItem(clause, 3);
        m_alts.add(new WhenClause(exprs, stmt));
    }

    public static final class WhenClause extends Pair<ExpressionList, Statement> {

        private WhenClause(final ExpressionList expr, final Statement stmt) {
            super(expr, stmt);
        }
    }

    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }

    private final StatementModifier m_stmtModifier;

    private final Expression m_expr;
    private final List<WhenClause> m_alts = new LinkedList<>();
    private final Statement m_else;
}
