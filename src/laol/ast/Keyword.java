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

/**
 * Convenience class for wrapping a primitive or keyword. Use this instead of
 * Class alias, since we can then add convenience methods here.
 *
 * @author gburdell
 */
public class Keyword extends Item {

    public Keyword(final Acceptor kwrd) {
        super(kwrd);
        m_cls = kwrd.getClass();
    }

    public Class getClz() {
        return m_cls;
    }

    @Override
    public String toString() {
        //todo: looks a bit dicey here...
        //want to look at other patterns/usages of Keyword
        String s;
        try {
            s = asPrioritizedChoice().getAccepted().getBaseAccepted().toString();
        } catch (ClassCastException ex) {
            s = getParsed().getBaseAccepted().toString();
        }
        return s;
    }

    private final Class m_cls;
}
