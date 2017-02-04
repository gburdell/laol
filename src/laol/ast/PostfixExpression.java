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

import apfe.runtime.Acceptor;
import apfe.runtime.Sequence;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class PostfixExpression extends Item {

    public PostfixExpression(final laol.parser.apfe.PostfixExpression decl) {
        super(decl);
        Item expr;
        for (Acceptor ele : decl.getItems()) {
            final Sequence seq = asSequence(ele);
            final Acceptor first = seq.itemAt(0);
            if (first instanceof laol.parser.apfe.LBRACK) {
                expr = new ArySelExpr(seq);
            } else if (first instanceof laol.parser.apfe.LPAREN) {
                expr = new PrimExprList(seq);
            } else if (first instanceof laol.parser.apfe.DotSuffix) {
                expr = new DotSfx(seq);
            } else if (first instanceof laol.parser.apfe.PrimaryExpression) {
                expr = new PrimExpr(seq);
            } else {
                assert (1 == seq.length());
                expr = new IncDec(seq.itemAt(0));
            }
            m_exprs.add(expr);
        }
    }

    public static class ItemWithBlock extends Item {

        protected ItemWithBlock(Acceptor parsed) {
            super(parsed);
            m_seq = asSequence();
            final int n = m_seq.getAccepted().length - 1;
            m_block = oneOrNone(m_seq, n);
        }

        public Block getBlock() {
            return m_block;
        }

        protected final Sequence m_seq;

        private final Block m_block;
    }

    public static class ArySelExpr extends ItemWithBlock {

        private ArySelExpr(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(m_seq, 1);
        }

        public ArraySelectExpression getExpr() {
            return m_expr;
        }

        private final ArraySelectExpression m_expr;
    }

    public static class PrimExprList extends ItemWithBlock {

        private PrimExprList(Acceptor parsed) {
            super(parsed);
            m_expr = oneOrNone(m_seq, 1);
        }

        public ParamExpressionList getExpr() {
            return m_expr;
        }

        private ParamExpressionList m_expr;
    }

    public static class DotSfx extends ItemWithBlock {

        private DotSfx(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(m_seq, 0);
        }

        public DotSuffix getExpr() {
            return m_expr;
        }

        private final DotSuffix m_expr;
    }

    public static class IncDec extends Keyword {

        private IncDec(Acceptor parsed) {
            super(asPrioritizedChoice(parsed).getAccepted());
        }
    }

    public static class PrimExpr extends ItemWithBlock {

        private PrimExpr(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(m_seq, 0);
        }

        public PrimaryExpression getExpr() {
            return m_expr;
        }

        private final PrimaryExpression m_expr;
    }

    public List<Item> getExprs() {
        return Collections.unmodifiableList(m_exprs);
    }

    private final List<Item> m_exprs = new LinkedList<>();
}
