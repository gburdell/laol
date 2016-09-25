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
            if (ele instanceof Sequence) {
                final Sequence seq = asSequence(ele);
                final Acceptor first = seq.getAccepted()[0];
                if (first instanceof laol.parser.apfe.LBRACK) {
                    expr = new ArySelExpr(seq);
                } else if (first instanceof laol.parser.apfe.LPAREN) {
                    expr = new PrimExprList(seq);
                } else if (first instanceof laol.parser.apfe.DotSuffix) {
                    expr = new DotSfx(seq);
                } else {
                    expr = new PrimExpr(seq);
                }
            } else {
                expr = new IncDec(ele);
            }
            m_exprs.add(expr);
        }
    }

    private static class ItemWithBlock extends Item {
        
        protected ItemWithBlock(Acceptor parsed) {
            super(parsed);
            final int n = asSequence().getAccepted().length - 1;
            m_block = oneOrNone(n);
        }
        
        private final Block m_block;
    }
    
    private static class ArySelExpr extends ItemWithBlock {

        public ArySelExpr(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(1);
        }
        
        private final ArraySelectExpression m_expr;
    }

    private static class PrimExprList extends ItemWithBlock {

        public PrimExprList(Acceptor parsed) {
            super(parsed);
            m_expr = oneOrNone(1);
        }
        
        private ParamExpressionList m_expr;
    }

    private static class DotSfx extends ItemWithBlock {

        public DotSfx(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(0);
        }
        
        private final DotSuffix   m_expr;
    }

    private static class IncDec extends Item {

        public IncDec(Acceptor parsed) {
            super(parsed);
            m_op = m_parsed.getBaseAccepted().getClass();
        }
        
        private final Class m_op;
    }

    private static class PrimExpr extends ItemWithBlock {

        public PrimExpr(Acceptor parsed) {
            super(parsed);
            m_expr = createItem(0);
        }
        
        private final PrimaryExpression m_expr;
    }

    private List<Item>  m_exprs = new LinkedList<>();
}
