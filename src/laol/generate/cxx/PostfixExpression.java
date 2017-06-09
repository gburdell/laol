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
import gblib.Stack;
import static gblib.Util.downCast;
import java.util.Vector;
import laol.ast.Item;
import laol.ast.ParamEle;
import laol.ast.SelectExpression;
import laol.ast.UnnamedParam;

/**
 *
 * @author gburdell
 */
public class PostfixExpression {

    public static void process(final laol.ast.PostfixExpression pfExpr, final Context ctx) {
        (new PostfixExpression(pfExpr, ctx)).process();
    }

    /*
        postfix_expression <-
            postfix_expression LBRACK select_expression RBRACK
        /   postfix_expression LPAREN param_expression_list? RPAREN block?
        /   postfix_expression dot_suffix block?
        /   postfix_expression (PLUS2 / MINUS2)
        /   primary_expression
     */
    private void process() {
        //A bit complicated since we need lookahead to detect ident.new
        final Stack<Item> stk = new Stack<>(m_pfExpr.getExprs());
        while (!stk.isEmpty()) {
            if (!isConstructor(stk)) {
                final Item item = stk.pop();
                if (item instanceof laol.ast.PostfixExpression.ArySelExpr) {
                    process(laol.ast.PostfixExpression.ArySelExpr.class.cast(item));
                } else if (item instanceof laol.ast.PostfixExpression.DotSfx) {
                    process(laol.ast.PostfixExpression.DotSfx.class.cast(item));
                }
            }
        }
    }

    /**
     * Lookahead for IDENT . new
     *
     * @param items ordered items to check.
     * @return true if constructor pattern, and also process.
     */
    private boolean isConstructor(Stack<Item> items) {
        if (2 <= items.size()) {
            if (items.get(1) instanceof laol.ast.PostfixExpression.DotSfx) {
                final laol.ast.PostfixExpression.DotSfx sfx = downCast(items.get(1));
                if (!sfx.getExpr().isNew()) {
                    return false;
                }
                assert (!sfx.getExpr().endsWithQmark());
                final laol.ast.PrimaryExpression primExpr = downCast(items.pop());
                items.pop(); //we already grabbed sfx above
                //NOTE: sfx could contain block!
                final laol.ast.ScopedName name = downCast(primExpr.getExpr());
                os().format("(new %s(", name.toString());
                if (sfx.hasBlock()) {
                    AnonymousFunctionDefn.process(sfx.getBlock(), m_ctx);
                } else if (items.peek() instanceof laol.ast.PostfixExpression.PrimExprList) {
                    final List<ParamEle> parms = downCast(items.pop());
                    Util.processAsCSV(parms, m_ctx, p->{
                        final UnnamedParam parm = downCast(p.getEle());
                        Expression.process(parm, m_ctx);
                    });
                }
                os().print("))");
                return true;
            }
        }
        return false;
    }

    private void process(final laol.ast.PostfixExpression.DotSfx expr) {
        final String suffix = expr.getExpr().getSuffix().toString();
    }

    private void process(final laol.ast.PostfixExpression.ArySelExpr expr) {
        final List<laol.ast.Expression> select = expr.getExpr().getExpressions();
        os().print('[');
        if (1 == select.size()) {
            Expression.process(select.get(0), m_ctx);
        } else {
            os().format("[new Select(%s(", Util.TO_VEC);
            Util.processAsCSV(select, m_ctx, e->Expression.process(e, m_ctx));
            os().print("))");
        }
        os().print(']');
    }

    private PrintStream os() {
        return m_ctx.os();
    }

    private PostfixExpression(final laol.ast.PostfixExpression pfExpr, final Context ctx) {
        m_pfExpr = pfExpr;
        m_ctx = ctx;
    }

    private final laol.ast.PostfixExpression m_pfExpr;
    private final Context m_ctx;
}
