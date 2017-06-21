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
import static gblib.Util.downCast;
import laol.ast.DotSuffix;
import laol.ast.Item;
import laol.ast.ParamEle;
import laol.ast.PostfixExpression.ArySelExpr;
import laol.ast.PostfixExpression.DotSfx;
import laol.ast.PostfixExpression.IncDec;
import laol.ast.PostfixExpression.PrimExpr;
import laol.ast.PostfixExpression.PrimExprList;
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
        from laol.peg:
    
        postfix_expression <-
            postfix_expression LBRACK select_expression RBRACK
        /   postfix_expression LPAREN param_expression_list? RPAREN block?
        /   postfix_expression dot_suffix block?
        /   postfix_expression (PLUS2 / MINUS2)
        /   primary_expression
     */
    private void process() {
        //A bit complicated since we need lookahead...
        while (!m_eles.isEmpty()) {
            if (!isConstructor()) {
                final Item item = m_eles.pop();
                if (item instanceof ArySelExpr) {
                    process(ArySelExpr.class.cast(item));
                } else if (item instanceof DotSfx) {
                    process(DotSfx.class.cast(item));
                } else if (item instanceof IncDec) {
                    os().print(laol.ast.Keyword.class.cast(item).toString());
                } else {
                    process(gblib.Util.<PrimExpr>downCast(item));
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
    private boolean isConstructor() {
        if (2 <= m_eles.size()) {
            if (m_eles.get(1) instanceof DotSfx) {
                final DotSfx sfx = downCast(m_eles.get(1));
                if (!sfx.getExpr().isNew()) {
                    return false;
                }
                assert (!sfx.getExpr().endsWithQmark());
                final PrimExpr primExpr = downCast(m_eles.pop());
                m_eles.pop(); //we already grabbed sfx above
                //NOTE: sfx could contain block!
                final laol.ast.ScopedName name = downCast(primExpr.getExpr().getExpr());
                os().format("(new %s(", name.toString());
                if (sfx.hasBlock()) {
                    AnonymousFunctionDefn.process(sfx.getBlock(), m_ctx);
                } else {
                    isPrimExprList();
                }
                os().print("))");
                return true;
            }
        }
        return false;
    }

    private void process(final PrimExpr expr) {
        final laol.ast.PrimaryExpression primExpr = expr.getExpr();
        if (primExpr.isSimpleName()) {
            final String name = primExpr.toSimpleName();
            if (m_ctx.isMemberName(name)) {
                boolean isLocalMemberName = m_eles.isEmpty();
                if (!isLocalMemberName) {
                    final Item peek = m_eles.peek();
                    isLocalMemberName = (peek instanceof ArySelExpr) || (peek instanceof IncDec);
                }
                if (isLocalMemberName) {
                    os().printf("m_%s", name); //use accessor
                    return;
                }
            }
        }
        PrimaryExpression.process(primExpr, m_ctx);
    }
    
    private void process(final DotSfx expr) {
        final DotSuffix suffix = expr.getExpr();
        assert (!suffix.isNew());    //do w/ isConstructor
        if (suffix.isNil()) {
            assert (suffix.endsWithQmark() && !expr.hasBlock());
            os().print(".isNull()");
        } else {
            assert (suffix.isIdent());
            final String methodName = Util.toMethodName(suffix.getSuffix());
            os().printf("(\"%s\"", methodName);
            if (expr.hasBlock()) {
                os().print(", ");
                AnonymousFunctionDefn.process(expr.getBlock(), m_ctx);
            } else {
                isPrimExprList(true, true);
            }
            os().print(")");
        }
    }

    private boolean isPrimExprList(boolean addComma, boolean toVec) {
        if (m_eles.peek() instanceof PrimExprList) {
            final PrimExprList expr = downCast(m_eles.pop());
            final List<ParamEle> parms = expr.getExpr();
            if (addComma && !parms.isEmpty()) {
                os().print(", ");
            }
            toVec &= (1 < parms.size()) || ((1 == parms.size()) && expr.hasBlock());
            if (toVec) {
                os().format("%s(", Util.TO_VEC);
            }
            Util.processAsCSV(parms, m_ctx, p -> {
                final UnnamedParam parm = downCast(p.getEle());
                Expression.process(parm, m_ctx);
            });
            if (expr.hasBlock()) {
                if (!parms.isEmpty()) {
                    os().print(", ");
                }
                AnonymousFunctionDefn.process(expr.getBlock(), m_ctx);
            }
            if (toVec) {
                os().print(")");
            }
            return true;
        }
        return false;
    }

    private boolean isPrimExprList() {
        return isPrimExprList(false, false);
    }

    private void process(final ArySelExpr expr) {
        final List<laol.ast.Expression> select = expr.getExpr().getExpressions();
        os().print('[');
        if (1 == select.size()) {
            Expression.process(select.get(0), m_ctx);
        } else {
            os().format("[new Select(%s(", Util.TO_VEC);
            Util.processAsCSV(select, m_ctx, e -> Expression.process(e, m_ctx));
            os().print("))");
        }
        os().print(']');
    }

    private PrintStream os() {
        return m_ctx.os();
    }

    private PostfixExpression(final laol.ast.PostfixExpression pfExpr, final Context ctx) {
        m_eles = new LinkedList(pfExpr.getExprs());
        m_ctx = ctx;
    }

    private final LinkedList<Item> m_eles;
    private final Context m_ctx;
}
