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
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 *
 * @author gburdell
 */
public class ClassMethodDeclaration extends Item implements IModifiers {

    public ClassMethodDeclaration(final laol.parser.apfe.ClassMethodDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        m_type = createItem(seq, 1);
        m_name = createItem(seq, 2);
        m_parmDecl = oneOrNone(seq, 3);
        m_retnDecl = oneOrNone(seq, 4);
        {
            final Repetition rep = asRepetition(seq, 5);
            if (0 < rep.sizeofAccepted()) {
                m_body = createItem(asSequence(rep.getOnlyAccepted()), 1);
            } else {
                m_body = null;
            }
        }
    }

    public MethodName getName() {
        return m_name;
    }

    public MethodType getType() {
        return m_type;
    }

    public boolean isAbstract() {
        return Objects.isNull(getBody());
    }

    public boolean hasBody() {
        return !isAbstract();
    }
    
    public ClassMethodBody getBody() {
        return m_body;
    }

    public MethodParamDecl getParmDecl() {
        return m_parmDecl;
    }

    public boolean hasParmDecl() {
        return isNonNull(getParmDecl());
    }

    public MethodReturnDecl getRetnDecl() {
        return m_retnDecl;
    }

    @Override
    public int getModifiers() {
        return getModifiers(m_access, Modifier.PRIVATE)
                | (m_type.isStaticDef() ? Modifier.STATIC : 0);
    }

    private final AccessModifier m_access;
    private final MethodType m_type;
    private final MethodName m_name;
    private final MethodParamDecl m_parmDecl;
    private final MethodReturnDecl m_retnDecl;
    private final ClassMethodBody m_body;

}
