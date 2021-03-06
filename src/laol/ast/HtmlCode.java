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
import java.util.List;

/**
 *
 * @author gburdell
 */
public class HtmlCode extends Item {
    public HtmlCode(final laol.parser.apfe.HtmlCode decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_tagName = getIdent(seq, 0);
        Repetition rep = asRepetition(seq, 1);
        if (0 < rep.sizeofAccepted()) {
            rep = asRepetition(asSequence(rep.getOnlyAccepted()), 1);
            if (0 < rep.sizeofAccepted()) {
                m_parms = createItem(rep.getOnlyAccepted());
            } else {
                m_parms = null;
            }
        } else {
            m_parms = null;
        }
        m_content = zeroOrMore(seq, 3);
    }

    public List<HtmlCodeContent> getContent() {
        return Collections.unmodifiableList(m_content);
    }

    public ParamExpressionList getParms() {
        return m_parms;
    }

    public Ident getTagName() {
        return m_tagName;
    }
    
    
    private final Ident m_tagName;
    private final ParamExpressionList   m_parms;
    private final List<HtmlCodeContent> m_content;
}
