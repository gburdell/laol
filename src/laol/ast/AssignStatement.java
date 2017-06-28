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
import laol.ast.etc.IStatementModifier;

/**
 *
 * @author gburdell
 */
public class AssignStatement extends Item implements IStatementModifier {

    public AssignStatement(final laol.parser.apfe.AssignStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_lhs = createItem(seq, 0);
        m_op = createItem(seq, 1);
        m_rhs = createItem(seq, 2);
        m_stmtModifier = getStatementModifier(seq, 3);
    }

    public AssignmentLhs getLhs() {
        return m_lhs;
    }

    public AssignmentOp getOp() {
        return m_op;
    }

    public AssignmentRhs getRhs() {
        return m_rhs;
    }

    @Override
    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }
    
    private final AssignmentLhs m_lhs;
    private final AssignmentOp  m_op;
    private final AssignmentRhs m_rhs;
    private final StatementModifier m_stmtModifier;
}
