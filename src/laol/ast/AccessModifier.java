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
import laol.parser.apfe.KPRIVATE;
import laol.parser.apfe.KPUBLIC;

/**
 *
 * @author gburdell
 */
public class AccessModifier extends Item {

    public static enum EType {
        ePrivate, eProtected, ePublic
    }

    public AccessModifier(final laol.parser.apfe.AccessModifier decl) {
        m_loc = decl.getStartMark();
        final Class choice = asPrioritizedChoice(decl)
                .getAccepted()
                .getClass();
        if (KPRIVATE.class == choice) {
            m_access = EType.ePrivate;
        } else if (KPUBLIC.class == choice) {
            m_access = EType.ePublic;
        } else {
            m_access = EType.eProtected;
        }
    }

	@Override
	public Marker getLocation() {
		return m_loc;
	}

    private final EType m_access;
    private final Marker m_loc;
}
