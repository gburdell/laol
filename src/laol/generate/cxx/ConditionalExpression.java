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

import apfe.runtime.LeftRecursiveAcceptor;
import java.io.PrintStream;
import laol.ast.BinaryOp;
import laol.ast.Item;
import laol.ast.Keyword;
import laol.ast.LorExpression;
import laol.ast.RangeExpression;
import laol.ast.UnaryExpression;

/**
 *
 * @author gburdell
 */
public class ConditionalExpression {

    public static void process(final laol.ast.ConditionalExpression item, final Context ctx) {
        (new ConditionalExpression(item, ctx)).process();
    }

    private PrintStream os() {
        return m_ctx.os();
    }

    private void process() {
        if (m_condExpr.hasConditional()) {

        } else {
            process(m_condExpr.getCondExpr());
        }
    }

    private void process(final RangeExpression rngExpr) {
        if (rngExpr.isRange()) {
            os().append("new Range(");
        }
        process(rngExpr.left());
        if (rngExpr.isRange()) {
            os().append(",");
            process(rngExpr.right());
            os().append(")");
        }
    }

    private <T extends LeftRecursiveAcceptor> void process(BinaryOp.LRExpr<T> lrexpr) {
        lrexpr.getItems().forEach(item -> {
            if (item instanceof UnaryExpression) {
                process(UnaryExpression.class.cast(item));
            } else if (item instanceof Keyword) {
                process(Keyword.class.cast(item));
            } else if (item instanceof BinaryOp.LRExpr) {
                process(BinaryOp.LRExpr.class.cast(item));
            } else {
                assert (false);
            }
        });
    }

    private void process(final UnaryExpression uexpr) {
        uexpr.getItems().forEach(item -> {
            if (item instanceof UnaryExpression.UnaryOpExpr) {
                process(UnaryExpression.UnaryOpExpr.class.cast(item));
            } else {
                PostfixExpression.process(laol.ast.PostfixExpression.class.cast(item), m_ctx);
            }
        });
    }

    private void process(final UnaryExpression.UnaryOpExpr uopExpr) {
        process(uopExpr.getOp());
        process(uopExpr.getExpr());
    }
    
    private void process(final Keyword op) {
        //m_buf.append(op.getClz().getSimpleName());
        String s = op.toString();
        os().append(' ').append(s).append(' ');
    }

    private ConditionalExpression(final laol.ast.ConditionalExpression item, final Context ctx) {
        m_condExpr = item;
        m_ctx = ctx;
    }

    private final laol.ast.ConditionalExpression m_condExpr;
    private final Context m_ctx;
}
