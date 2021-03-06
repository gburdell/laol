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
import apfe.runtime.Marker;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class MethodReturnDecl extends Item {
    public MethodReturnDecl(final laol.parser.apfe.MethodReturnDecl decl) {
        super(decl);
        final Sequence seq = asSequence(asPrioritizedChoice().getAccepted());
        if (seq.itemAt(1) instanceof laol.parser.apfe.TypeName) {
            m_ele = createItem(seq, 1);
        } else {
            m_ele = new MultiReturn(seq);
        }
    }
    
    public static class MultiReturn extends Item {
        
        public MultiReturn(Sequence parsed) {
            super(parsed);
            m_eles.add(createItem(parsed, 2));
            m_eles.addAll(zeroOrMore(asRepetition(parsed, 3), 1));
        }

        public List<MethodReturnEle> getEles() {
            return m_eles;
        }
        
        private final List<MethodReturnEle>   m_eles = new LinkedList<>();
    }

    public Item getEle() {
        return m_ele;
    }
    
    private final Item  m_ele;
}
