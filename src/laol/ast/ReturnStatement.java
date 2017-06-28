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
import java.util.Objects;
import laol.ast.etc.IStatementModifier;

/**
 *
 * @author gburdell
 */
public class ReturnStatement extends Item implements IStatementModifier {
    public ReturnStatement(final laol.parser.apfe.ReturnStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_expr = oneOrNone(seq, 1);
        m_stmtModifier = getStatementModifier(seq, 2);
        m_assignStmt = null;
    }

    public ReturnStatement(final ExpressionStatement stmt) {
        super(stmt.getParsed());
        m_expr = stmt.getExpr();
        m_stmtModifier = stmt.getStmtModifier();
        m_assignStmt = null;
    }
    
    public ReturnStatement(final AssignStatement asgn) {
        super(asgn.getParsed());
        assert(! asgn.hasStmtModifier());
        m_assignStmt = asgn;
        m_expr = null;
        m_stmtModifier = null;
    }
    
    public boolean isAssignStmt() {
        return Objects.nonNull(getAssignStmt());
    }
    
    public AssignStatement getAssignStmt() {
        return m_assignStmt;
    }
    
    public Expression getExpr() {
        return m_expr;
    }

    @Override
    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }
 
    private final AssignStatement m_assignStmt;
    private final Expression m_expr;
    private final StatementModifier m_stmtModifier;
}
