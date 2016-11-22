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

/**
 *
 * @author gburdell
 */
public class ArraySelectExpression extends Item {
    public ArraySelectExpression(final laol.parser.apfe.ArraySelectExpression decl) {
        super(decl);
        final Acceptor acc = asPrioritizedChoice().getAccepted();
        if (acc instanceof laol.parser.apfe.ExpressionList) {
            m_item = createItem(acc);
        } else {
            m_item = new InclusiveRange(acc);
        }
    }
    
    public static class InclusiveRange extends Item {

        public InclusiveRange(final Acceptor exprs) {
            super(exprs);
            m_left = createItem(0);
            m_right = createItem(2);
        }
        
        private final Expression m_left, m_right;
    }
    
    private final Item m_item;
}
