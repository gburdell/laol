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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;
import laol.parser.IDENT;
import laol.parser.apfe.LBRACK;

/**
 *
 * @author gburdell
 */
public class ArrayPrimary extends Item {
    public ArrayPrimary(final laol.parser.apfe.ArrayPrimary decl) {
        super(decl);
        final Sequence seq = asSequence(m_parsed.getBaseAccepted());
        if (seq.getAccepted()[0] instanceof LBRACK) {
            final Repetition items = asRepetition(seq, 1);
            if (0 < items.sizeofAccepted()) {
               m_ele = new ExpressionList(items.getOnlyAccepted());
            } else {
                m_ele = null;
            }
        } else {
            m_ele = new WordsOrSymbols(seq);
        }
    }
    
    public static class WordsOrSymbols extends Item {
        public static enum EType {eWords, eSymbols};
        
        public WordsOrSymbols(final Sequence items) {
            super(items);
            m_type = (asSequence().getText(1).charAt(0) == 'w') ? EType.eWords : EType.eSymbols;
            for (IDENT item : Util.<IDENT>extractList(asRepetition(3), 0)) {
                m_eles.add(new Ident(item));
            }
        }
        
        private final EType m_type;
        private final List<Ident>   m_eles = new LinkedList<>();
    }
    
    private final Item  m_ele;
}
