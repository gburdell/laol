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
import java.util.function.Consumer;
import laol.ast.Keyword;

/**
 *
 * @author gburdell
 */
public class StatementModifier {

    public static void process(
            final laol.ast.StatementModifier item,
            final Context ctx,
            final Consumer<Void> body) {
        (new StatementModifier(item, ctx, body)).process();
    }

    private void process() {
        final Keyword kwrd = m_stmtModifier.getType();
        if (kwrd.getClz().equals(laol.parser.apfe.KUNTIL.class)) {
            os().println("do {");
            m_body.accept(null);
            //Expression.process(m_item.getExpr(), m_ctx);
            os().printf("} while (toBool(");
            Expression.process(m_stmtModifier.getExpr(), m_ctx);
            os().println("));");
        } else {
            os().printf("%s (toBool(", kwrd.toString().toLowerCase());
            Expression.process(m_stmtModifier.getExpr(), m_ctx);
            os().println(")) {");
            m_body.accept(null);
            //Expression.process(m_item.getExpr(), m_ctx);
            os().println("}");
        }

    }

    private PrintStream os() {
        return m_ctx.cxx();
    }

    private StatementModifier(final laol.ast.StatementModifier item,
            final Context ctx,
            final Consumer<Void> body) {
        m_stmtModifier = item;
        m_ctx = ctx;
        m_body = body;
    }

    private final laol.ast.StatementModifier m_stmtModifier;
    private final Context m_ctx;
    private final Consumer<Void> m_body;
}
