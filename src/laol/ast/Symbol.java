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

import apfe.runtime.Sequence;
import gblib.Util;

/**
 *
 * @author gburdell
 */
public class Symbol extends Item {
    public Symbol(final laol.parser.apfe.SYMBOL decl) {
        super(decl);
        final Sequence seq = asSequence(asPrioritizedChoice().getAccepted());
        if (seq.itemAt(1) instanceof laol.parser.IDENTNK) {
            m_symbol = getIdent(seq, 1);
        } else {
            //operator
            m_symbol = createItem(asPrioritizedChoice(seq.itemAt(1)).getAccepted());
        }
    }
    
    @Override
    public String toString() {
        assert(isIdent());
        return Util.<Ident>downCast(m_symbol).toString();
    }
    
    public boolean isIdent() {
        return (m_symbol instanceof Ident);
    }
    
    private final Item  m_symbol;
}
