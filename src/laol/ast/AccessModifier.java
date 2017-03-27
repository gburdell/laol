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

import laol.ast.etc.IModifiers;
import apfe.runtime.Marker;
import java.lang.reflect.Modifier;
import laol.parser.apfe.KPRIVATE;
import laol.parser.apfe.KPUBLIC;

/**
 *
 * @author gburdell
 */
public class AccessModifier extends Item implements IModifiers {

    public static enum EType {
        ePrivate, eProtected, ePublic;

        @Override
        public String toString() {
            return super.toString().substring(1).toLowerCase();
        }
        
        public int getModifier() {
            return (0 == compareTo(ePrivate))
                    ? Modifier.PRIVATE
                    : (0 == compareTo(ePublic))
                    ? Modifier.PUBLIC
                    : Modifier.PROTECTED;
        }
    }

    public AccessModifier(final Marker loc, final EType type) {
        super(loc);
        m_access = type;
    }

    public AccessModifier(final Marker loc) {
        this(loc, EType.ePublic);
    }

    public AccessModifier(final laol.parser.apfe.AccessModifier decl) {
        super(decl);
        final Class choice = asPrioritizedChoice()
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

    public EType getType() {
        return m_access;
    }

    @Override
    public int getModifiers() {
        return getType().getModifier();
    }
    
    @Override
    public String toString() {
        return getType().toString();
    }

    private final EType m_access;
}
