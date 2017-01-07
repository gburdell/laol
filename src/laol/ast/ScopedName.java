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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class ScopedName extends Item {

    public ScopedName(final laol.parser.apfe.ScopedName decl) {
        super(decl);
        final Sequence seq = asSequence();
        Repetition rep = asRepetition(seq, 0);
        m_isRooted = rep.sizeofAccepted() > 0;
        m_path.add(getIdent(seq, 1));
        m_path.addAll(zeroOrMoreIdent(asRepetition(seq, 2), 1));        
    }

    public List<Ident>  getIdents() {
        return Collections.unmodifiableList(m_path);
    }
    
    public boolean isRooted() {
        return m_isRooted;
    }
    
    /**
     * Scoped name started with '::'.
     */
    private final boolean m_isRooted;
    private final List<Ident> m_path = new LinkedList<>();
}
