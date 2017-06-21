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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class VarDeclStatement {

    public static void process(final laol.ast.VarDeclStatement item, final Context ctx) {
        final PrintStream os = ctx.os();
        //LHS could be a member initializer
        if (item.isAssign()) {
            final LinkedList<String> lhsNames = new LinkedList(item.getLhsNames());
            final laol.ast.AssignmentRhs rhs = item.getRhs();
            assert(rhs.isExprList()); //todo: other types
            final LinkedList<laol.ast.Expression> rhsExpr = new LinkedList(rhs.asExprList().getExpressions());
            assert(lhsNames.size() == rhsExpr.size());
            final String equalOp = item.getOp().getOp().toString();
            while (! lhsNames.isEmpty()) {
                final String lhsName = lhsNames.pop();
                if (ctx.isMemberName(lhsName)) {
                    os.printf("m_%s", lhsName);
                } else {
                    os.printf("LaolObj %s", lhsName);
                }
                os.printf(" %s ", equalOp);
                Expression.process(rhsExpr.pop(), ctx);
                os.println(" ;");
            }
        } else {
            //do nothing: since all the varDecl were already done during
            //constructors: i.e., all are members
        }
    }
}
