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
import java.lang.reflect.Modifier;
import laol.parser.apfe.KCONST;

/**
 *
 * @author gburdell
 */
public class Mutability extends Item implements IModifiers {

    public static enum EType {
        eConst, eVar
    }

    public Mutability(final laol.parser.apfe.Mutability decl) {
        super(decl);
        final Class choice = asPrioritizedChoice()
                .getAccepted()
                .getClass();
        if (KCONST.class == choice) {
            m_type = EType.eConst;
        } else {
            m_type = EType.eVar;
        }
    }

    public EType getType() {
        return m_type;
    }

    @Override
    public int getModifiers() {
        return isConst() ? Modifier.FINAL : 0;
    }

    public boolean isConst() {
        return m_type == EType.eConst;
    }

    public boolean isVar() {
        return m_type == EType.eVar;
    }

    private final EType m_type;

}
