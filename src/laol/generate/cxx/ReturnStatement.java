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
import java.util.Objects;

/**
 *
 * @author gburdell
 */
public class ReturnStatement {

    public static void process(final laol.ast.ReturnStatement item, final Context ctx) {
        final PrintStream os = ctx.cxx();
        final laol.ast.Expression expr = item.getExpr();
        if (item.hasStmtModifier()) {
            StatementModifier.process(
                    item.getStmtModifier(), 
                    ctx, __->process(os, expr, ctx)
            );
        } else if (item.isAssignStmt()) {
            os.print("return (");
            AssignStatement.process(item.getAssignStmt(), ctx);
            os.println(");");
        } else {
            process(os, expr, ctx);
        }
    }

    private static void process(final PrintStream os, final laol.ast.Expression expr, final Context ctx) {
        os.print("return ");
        if (Objects.nonNull(expr)) {
            Expression.process(expr, ctx);
        }
        os.println(" ;");
    }
}
