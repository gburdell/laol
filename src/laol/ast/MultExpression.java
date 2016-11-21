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
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;
import static laol.ast.Item.asSequence;

/**
 *
 * @author gburdell
 */
public class MultExpression extends Item {

    public MultExpression(final laol.parser.apfe.MultExpression decl) {
        super(decl);
        Item expr;
        for (Acceptor ele : decl.getItems()) {
            if (ele instanceof Sequence) {
                final Sequence seq = asSequence(ele);
                expr = new MultOpExpr(seq);
            } else {
                expr = new UnaryExpression(Util.downcast(ele));
            }
            m_items.add(expr);
        }

    }

    public static class MultOpExpr extends Item {

        public MultOpExpr(Acceptor parsed) {
            super(parsed);
            m_op = createItem(0);
            m_expr = createItem(1);
        }

        private final MultOp    m_op;
        private final UnaryExpression   m_expr;
    }

    private final List<Item> m_items = new LinkedList<>();
}
