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

import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import java.util.Objects;

/**
 *
 * @author gburdell
 */
public class RangeExpression extends Item {
    public RangeExpression(final laol.parser.apfe.RangeExpression decl) {
        super(decl);
        final PrioritizedChoice pc = asPrioritizedChoice();
        if (pc.getAccepted() instanceof Sequence) {
            final Sequence seq = asSequence(pc.getAccepted());
            m_left = createItem(seq, 0);
            m_right = createItem(seq, 2);
        } else {
            m_left = createItem(pc.getAccepted());
            m_right = null;
        }
    }
    
    public LorExpression left() {
        return m_left;
    }
    
    public LorExpression right() {
        return m_right;
    }
    
    /**
     * Check is really a range expression.
     * @return true if truly range expression; else just a LorExpression.
     */
    public boolean isRange() {
        return Objects.nonNull(m_right);
    }
    
    private final LorExpression m_left, m_right;
}
