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
        final Sequence seq = asSequence();
        m_isHxx = ('h' == seq.itemAt(1).toString().toLowerCase().charAt(0));
        StringBuilder buf = new StringBuilder();
        zeroOrMore(3).forEach(ele -> buf.append(ele.toString()));
        m_content = buf.toString();
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
