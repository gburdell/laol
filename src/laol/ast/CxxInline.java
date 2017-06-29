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
public class CxxInline extends Item {
    public CxxInline(final laol.parser.apfe.CxxInline decl) {
        super(decl);
        //todo: nasty!  Sequence.toString() does not work, as it grabs 
        //other stuff past parse.  Me thinks the whole Acceptor.toString()
        //has major flaw.
        final Sequence seq = (Sequence)decl.getBaseAccepted();
        String text = seq.getTexts(0, seq.length()-1).toString();
        m_isHxx = ('h' == text.substring(0, 1).toLowerCase().charAt(0));
        m_content = text.substring(4);
    }
    
    @Override
    public String toString() {
        return m_content;
    }
    
    public boolean isHxx() {
        return m_isHxx;
    }
    
    public boolean isCxx() {
        return !isHxx();
    }
    
    private final String m_content;
    private final boolean m_isHxx;
}
