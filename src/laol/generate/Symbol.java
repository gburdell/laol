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
package laol.generate;

import gblib.Util;
import laol.ast.ClassDeclaration;
import laol.ast.IName;
import laol.ast.Item;
import laol.ast.MethodDeclaration;
import laol.ast.VarDeclStatement;

/**
 *
 * @author kpfalzer
 */
public abstract class Symbol {

    public static enum EType {
        eClass, eMethod, eLocalVar
    }

    protected Symbol(final Item namedItem) {
        m_item = namedItem;
        assert (m_item instanceof IName);
    }

    public abstract EType getType();

    public IName getIName() {
        return Util.downCast(m_item);
    }

    private final Item m_item;

    public static class Class extends Symbol {

        public Class(final ClassDeclaration decl) {
            super(decl);
        }

        @Override
        public EType getType() {
            return EType.eClass;
        }
    }

    public static class Method extends Symbol {

        public Method(final MethodDeclaration decl) {
            super(decl);
        }

        @Override
        public EType getType() {
            return EType.eMethod;
        }

    }

    public static class LocalVar extends Symbol {

        public LocalVar(final VarDeclStatement decl) {
            super(decl);
        }

        @Override
        public EType getType() {
            return EType.eLocalVar;
        }

    }
}
