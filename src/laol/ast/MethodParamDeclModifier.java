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
import apfe.runtime.Sequence;
import java.lang.reflect.Modifier;

/**
 * Method parameter declaration. By default, a method parameter is PUBLIC in
 * constructor declaration; scope does not apply in a class method (other than
 * constructor).
 *
 * @author gburdell
 */
public class MethodParamDeclModifier extends Item implements IModifiers {

    public MethodParamDeclModifier(final laol.parser.apfe.MethodParamDeclModifier decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        m_mutability = oneOrNone(seq, 1);
    }

    @Override
    public int getModifiers() {
        return getModifiers(
                m_access, Modifier.PUBLIC,
                m_mutability, 0
        );
    }

    private final AccessModifier m_access;
    private final Mutability m_mutability;
}
