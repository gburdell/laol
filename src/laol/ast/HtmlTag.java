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
public class HtmlTag extends Item {

    public HtmlTag(final laol.parser.apfe.HtmlTag decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_tagOpen = getIdent(seq, 1);
        m_attrs = zeroOrMore(seq, 2);
        m_content = zeroOrMore(seq, 4);
        final Repetition rep = asRepetition(seq, 5);
        m_tagClose = (0 < rep.sizeofAccepted())
                ? getIdent(rep.getOnlyAccepted(), 1)
                : null;
    }

    public List<HtmlAttribute> getAttrs() {
        return Collections.unmodifiableList(m_attrs);
    }

    public List<HtmlTagContent> getContent() {
        return Collections.unmodifiableList(m_content);
    }

    public Ident getTagClose() {
        return m_tagClose;
    }

    public Ident getTagOpen() {
        return m_tagOpen;
    }

    private final Ident m_tagOpen, m_tagClose;
    private final List<HtmlAttribute> m_attrs;
    private final List<HtmlTagContent> m_content;
}
