/*
 * The MIT License
 *
 * Copyright 2017 gburdell.
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
package laol.generate.cxx;

import java.io.PrintStream;
import java.util.List;
import java.util.ListIterator;
import laol.ast.IfStatement.ExprStmt;

/**
 *
 * @author gburdell
 */
public class IfStatement {

    public static void process(final laol.ast.IfStatement item, final Context ctx) {
        (new IfStatement(item, ctx)).process();
    }

    private void process() {
        final List<ExprStmt> stmts = m_item.getClauses();
        os().print("if ");
        process(stmts.get(0), m_item.isUnless());
        for (ListIterator<ExprStmt> iter = stmts.listIterator(1); iter.hasNext();) {
            os().println(" else if ");
            process(iter.next());
        }
        if (m_item.hasElse()) {
            os().println(" else");
            Statement.process(m_item.getElse(), m_ctx);
        }
    }

    private void process(final ExprStmt condStmt, boolean isNot) {
        os().printf("(%c toBool(", isNot ? '!' : ' ');
        Expression.process(condStmt.v1, m_ctx);
        os().println("))");
        Statement.process(condStmt.v2, m_ctx);
    }

    private void process(final ExprStmt condStmt) {
        process(condStmt, false);
    }
    
    private PrintStream os() {
        return m_ctx.cxx();
    }

    private IfStatement(final laol.ast.IfStatement item, final Context ctx) {
        m_item = item;
        m_ctx = ctx;
    }

    private final laol.ast.IfStatement m_item;
    private final Context m_ctx;
}
