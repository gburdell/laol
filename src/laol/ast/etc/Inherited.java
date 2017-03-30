/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
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
package laol.ast.etc;

import static gblib.Util.invariant;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import laol.ast.Ident;

/**
 * Wrap an ISymbol when a subclass inherits content (from super).
 * @author kpfalzer
 */
public class Inherited implements ISymbol {
    public Inherited(ISymbol fromSuper) {
        m_fromSuper = fromSuper;
        m_inheritedType = m_fromSuper.getType().clone();
        invariant(true == m_inheritedType.add(EType.eIsInherited));
        //we should not have affected super's type.
        assert (false == m_fromSuper.isInherited());
        int inheritedModifier = m_fromSuper.getModifiers();
        if (m_fromSuper.isProtected()) {
            //a protected becomes private in inherited...
            inheritedModifier |= Modifier.PRIVATE;
        }
        m_inheritedModifiers = inheritedModifier;
    }
    
    private final ISymbol m_fromSuper;
    private final EnumSet<EType> m_inheritedType;
    private final int m_inheritedModifiers;

    @Override
    public EnumSet<EType> getType() {
        return m_inheritedType;
    }

    @Override
    public boolean hasScope() {
        return m_fromSuper.hasScope();
    }

    @Override
    public int getModifiers() {
        return m_inheritedModifiers;
    }

    @Override
    public Ident getIdent() {
        return m_fromSuper.getIdent();
    }
    
    public ISymbol getSuper() {
        return m_fromSuper;
    }
}
