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

import gblib.MessageMgr;
import static gblib.Util.invariant;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 *
 * @author kpfalzer
 */
public class Scope {

    public static enum EType {
        eBlock, eModule, eClass, eMethod
    }

    public Symbol getSymbol(String name) {
        Symbol val = null;
        if (nonNull(m_stab)) {
            val = m_stab.get(name);
        }
        return val;
    }

    public Scope add(Symbol sym) {
        if (isNull(m_stab)) {
            m_stab = new SymbolTable();
        }
        final String name = sym.getIName().getName().asSimpleName();
        invariant(m_stab.insert(name, sym)); //TODO: LG-SYM error
        return this;
    }

    private SymbolTable m_stab;

    static {
        MessageMgr.addMessage(
                "LG-SYM",
                "%s:%d: symbol '%s' already defined at %s:%d");
    }
}
