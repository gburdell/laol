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
import java.util.Objects;

/**
 *
 * @author gburdell
 */
public class ConditionalExpression extends Item {

    public ConditionalExpression(final laol.parser.apfe.ConditionalExpression decl) {
        super(decl);
        final Acceptor acc = asPrioritizedChoice().getAccepted();
        if (acc.getClass() == laol.parser.apfe.RangeExpression.class) {
            m_expr = createItem(acc);
            m_ifFalse = m_ifTrue = null;
        } else {
            m_expr = createItem(acc, 0);
            m_ifTrue = createItem(acc, 2);
            m_ifFalse = createItem(acc, 4);
        }
    }

    public RangeExpression getCondExpr() {
        return m_expr;
    }

    public Expression getIfFalse() {
        return m_ifFalse;
    }

    public Expression getIfTrue() {
        return m_ifTrue;
    }

    public boolean hasConditional() {
        return Objects.nonNull(m_ifTrue);
    }
    
    private final RangeExpression m_expr;
    private final Expression m_ifTrue, m_ifFalse;
}
