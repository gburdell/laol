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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class SassContent extends Item {
    public SassContent(final laol.parser.apfe.SassContent decl) {
        super(decl);
        final Acceptor choice = asPrioritizedChoice().getBaseAccepted();
        if (choice instanceof Sequence) {
            final Repetition rep = asRepetition(asSequence(choice), 1);
            List<SassContent> items = new LinkedList<>();
            for (Acceptor content : Util.extractList(rep, 1)) {
              items.add(createItem(content));  
            }
            m_content = items;
        } else if (choice instanceof laol.parser.apfe.InlineEval) {
            m_content = createItem(choice);
        } else if (choice instanceof laol.parser.apfe.STRING) {
            m_content = new AString(Util.downcast(choice));
        } else {
            m_content = new AChar(Util.extractEle(asSequence(choice), 1));
        }
    }

    /**
     * Content can be one of:
     * - List of SassContent
     * - InlineEval
     * - AString
     * _ AChar
     */
    private final Object    m_content;
}
