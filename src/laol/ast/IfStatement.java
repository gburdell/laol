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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class IfStatement extends Item {

    public IfStatement(final laol.parser.apfe.IfStatement decl) {
        super(decl);
        m_if = (asPrioritizedChoice(0).getAccepted().getClass() == laol.parser.apfe.KIF.class);
        add(asSequence(), 1);
        for (Acceptor elseIfs : asRepetition(3).getAccepted()) {
            add(asSequence(elseIfs), 1);
        }
        final Repetition els = asRepetition(4);
        m_else = (0 < els.sizeofAccepted())
                ? createItem(els.getOnlyAccepted(), 1)
                : null;
    }

    private void add(final Sequence seq, final int ix) {
        m_clauses.add(new ExprStmt(seq, 1));
    }

    public class ExprStmt extends Pair<Expression, Statement> {

        private ExprStmt(final Sequence seq, final int ix) {
            super(createItem(seq, ix), createItem(seq, ix + 1));
        }
    }

    public List<ExprStmt> getClauses() {
        return Collections.unmodifiableList(m_clauses);
    }

    public Statement geElse() {
        return m_else;
    }

    public boolean isIf() {
        return m_if;
    }
    
    /**
     * True on if-statement; false on unless-statement.
     */
    private final boolean m_if;
    private final List<ExprStmt> m_clauses = new LinkedList<>();
    private final Statement m_else;
}
