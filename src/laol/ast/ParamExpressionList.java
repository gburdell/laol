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

/**
 *
 * @author gburdell
 */
public class ParamExpressionList extends Item {
    public ParamExpressionList(final laol.parser.apfe.ParamExpressionList decl) {
        super(decl);
        final Acceptor choice = asPrioritizedChoice().getBaseAccepted();
        if (choice instanceof Sequence) {
            m_unnamed = createItem(choice, 0);
            m_named = createItem(choice, 1);
        } else if (choice instanceof laol.parser.apfe.UnnamedParam) {
            m_unnamed = createItem(choice);
            m_named = null;
        } else {
            m_named = createItem(choice);
            m_unnamed = null;
        }
     }
    
    private final UnnamedParam  m_unnamed;
    private final NamedParam m_named;
}
