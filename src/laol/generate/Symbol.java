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
import laol.ast.IName;
import laol.ast.Item;

/**
 *
 * @author kpfalzer
 */
public abstract class Symbol {

    public static enum EType {
        eModule, eClass, eMethod, eLocalVar
    }

    protected Symbol(final Item namedItem, final EType type) {
        m_item = namedItem;
        assert (m_item instanceof IName);
        m_type = type;
    }

    public EType getType() {
        return m_type;
    }

    private IName getIName() {
        return Util.downCast(m_item);
    }

    private final EType m_type;
    private final Item m_item;

    public static class Module extends Symbol {

        public Module(final String name) {
            super(name, EType.eModule);
        }
    }

    public static class Class extends Symbol {

        public Class(final String name) {
            super(name, EType.eClass);
        }
    }

    public static class Method extends Symbol {

        public Method(final String name) {
            super(name, EType.eMethod);
        }
    }

    public static class LocalVar extends Symbol {

        public LocalVar(final String name) {
            super(name, EType.eLocalVar);
        }
    }
}
