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

import apfe.runtime.Marker;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;

/**
 *
 * @author gburdell
 */
public class LhsDecl extends Item {

    public LhsDecl(final laol.parser.apfe.LhsDecl decl) {
        m_loc = decl.getStartMark();
        final Sequence items = asSequence(decl);
        Repetition rep = Util.extractEle(items, 0);
        if (1 == rep.sizeofAccepted()) {
            m_access = new AccessModifier(rep.getOnlyAccepted());
        }
        if (1 == Util.<Repetition>extractEle(items, 1).sizeofAccepted()) {
            m_isStatic = true;
        }
        rep = Util.extractEle(items, 2);
        if (1 == rep.sizeofAccepted()) {
            m_mutability = new Mutability(rep.getOnlyAccepted());
        }
    }

    @Override
    public Marker getLocation() {
        return m_loc;
    }

    private final Marker m_loc;
    private boolean m_isStatic = false;
    private Mutability m_mutability = null;
    private AccessModifier m_access = null;
}
