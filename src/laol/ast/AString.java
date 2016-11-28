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
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import gblib.Util;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class AString extends Item {

    /**
     * A simple string.
     */
    public static class S extends Item {

        public S(final Acceptor parsed) {
            this(parsed, parsed.toString());
        }

        public S(final Acceptor parsed, final String str) {
            super(parsed);
            m_val = str;
        }

        private final String m_val;
    }

    public AString(final Acceptor acc) {
        super(acc);
        m_items.add(new S(acc));
    }

    public AString(final laol.parser.apfe.STRING decl) {
        super(decl);
        final PrioritizedChoice pc = asPrioritizedChoice();
        final Sequence items = pc.getAccepted();
        switch (pc.whichAccepted()) {
            case 0:
                // StringItem*
                m_items.addAll(zeroOrMore(items, 1));
                break;
            case 1:
                final int n = m_parsed.toString().length();
                if (2 < n) {
                    final String s = m_parsed.toString().substring(1, n - 1);
                    m_items.add(new S(decl, s));
                }
                break;
            default:
                Util.invariant(false);
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(m_items);
    }
    
    public boolean isEmpty() {
        return getItems().isEmpty();
    }
    
    private final List<Item> m_items = new LinkedList<>();
}
