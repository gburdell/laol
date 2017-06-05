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

    private void process() {
        if (m_condExpr.hasConditional()) {

        } else {
            process(m_condExpr.getCondExpr());
        }
    }

    private void process(final RangeExpression rngExpr) {
        if (rngExpr.isRange()) {
            m_buf.append("new Range(");
        }
        process(rngExpr.left());
        if (rngExpr.isRange()) {
            m_buf.append(",");
            process(rngExpr.right());
            m_buf.append(")");
        }
    }

    private void process(final LorExpression lorExpr) {
        lorExpr.getItems().forEach(item -> {
            if (item instanceof UnaryExpression) {
                process(UnaryExpression.class.cast(item));
            } else if (item instanceof Keyword) {
                
            }
        });
    }

    private void process(final UnaryExpression uexpr) {
        boolean debug = true;
    }
    
    private ConditionalExpression(final laol.ast.ConditionalExpression item, final Context ctx) {
        m_condExpr = item;
        m_ctx = ctx;
    }

    private final laol.ast.ConditionalExpression m_condExpr;
    private final Context m_ctx;
    private final StringBuilder m_buf = new StringBuilder();
}
