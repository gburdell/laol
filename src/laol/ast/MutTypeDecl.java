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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import gblib.Util;
import java.lang.reflect.Modifier;

/**
 *
 * @author gburdell
 */
public class MutTypeDecl extends Item implements IModifiers {

    public MutTypeDecl(final laol.parser.apfe.MutTypeDecl decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        m_isStatic = (0 < asRepetition(seq, 1).sizeofAccepted());
        m_mutability = createItem(seq, 2);
        {
            final Repetition rep = asRepetition(seq, 3);
            if (0 < rep.sizeofAccepted()) {
                m_type = createItem(rep.getOnlyAccepted(), 0);
            } else {
                m_type = null;
            }
        }
    }

    @Override
    public int getModifiers() {
        return getModifiers(
                m_access,
                m_mutability,
                m_isStatic);
    }
    
    public TypeName getType() {
        return m_type;
    }

    private final AccessModifier m_access;
    private final boolean m_isStatic;
    private final Mutability m_mutability;
    private final TypeName  m_type;
}
