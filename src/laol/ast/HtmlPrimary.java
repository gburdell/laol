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
import gblib.Util;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class HtmlPrimary extends Item {

    public HtmlPrimary(final laol.parser.apfe.HtmlPrimary decl) {
        super(decl);
        final Sequence seq = asSequence();
        final Repetition rep = asRepetition(seq, 1);
        m_items = new LinkedList<>();
        if (0 < rep.sizeofAccepted()) {
            for (Acceptor acc : rep.getAccepted()) {
                final Item item = createItem(asPrioritizedChoice(acc).getAccepted(), 1);
                m_items.add(item);
            }
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(m_items);
    }

    private final List<Item> m_items;
}
