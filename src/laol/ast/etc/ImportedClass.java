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

import laol.ast.Ident;
import laol.ast.Item;

/**
 * Class from an import/require statement
 *
 * @author kpfalzer
 */
public class ImportedClass implements ISymbol, ISymbolCreator {

    public ImportedClass(String loc, String fqn, Class cls) {
        m_fullyQualifiedName = fqn;
        m_class = cls;
        m_location = loc;
    }

    private final String m_fullyQualifiedName;
    private final Class m_class;
    private final String m_location;

    @Override
    public EType getType() {
        return EType.eClass;
    }

    @Override
    public boolean isImported() {
        return true;
    }

    @Override
    public int getModifiers() {
        return m_class.getModifiers();
    }

    @Override
    public String getName() {
        return m_class.getSimpleName();
    }

    @Override
    public String getFileLineCol() {
        return m_location;
    }

    @Override
    public Ident getIdent() {
        return null;
    }

}
