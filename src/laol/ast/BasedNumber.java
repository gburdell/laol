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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class BasedNumber extends Item {
    public BasedNumber(final laol.parser.apfe.BasedNumber decl) {
        super(decl);
        Sequence seq = asSequence();
        Repetition rep = asRepetition(seq, 0);
        if (0 < rep.sizeofAccepted()) {
            m_size = Integer.parseInt(rep.toString().replace("_", ""));
        } else {
            m_size = -1;
        }
        seq = asPrioritizedChoice(seq.itemAt(3)).getAccepted();
        m_base = Character.toUpperCase(seq.itemAt(0).toString().charAt(0));
        m_val = seq.itemAt(1).toString();
    }
    
    public boolean hasSize() {
        return m_size >= 0;
    }
    
    public int getSize() {
        return m_size;
    }

    public char getBase() {
        return m_base;
    }

    public String getVal() {
        return m_val;
    }
       
    private final int   m_size;
    private final char  m_base;
    private final String    m_val;
}
