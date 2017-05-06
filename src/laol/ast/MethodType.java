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
package laol.ast;

import apfe.runtime.Acceptor;

/**
 *
 * @author kpfalzer
 */
public class MethodType extends Item {

    public MethodType(final laol.parser.apfe.MethodType decl) {
        super(decl);
        final Acceptor acc = asPrioritizedChoice().getAccepted();
        if (acc instanceof laol.parser.apfe.KINTERFACE) {
            m_isInterface = true;
        } else if (acc instanceof laol.parser.apfe.KIMPLEMENTS) {
            m_isImplements = true;
        } else if (acc instanceof laol.parser.apfe.KDEF) {
            m_isDef = true;
        } else {
            m_isStatic = m_isDef = true;
        }
    }

    public boolean isInterface() {
        return m_isInterface;
    }

    public boolean isImplements() {
        return m_isImplements;
    }

    public boolean isStaticDef() {
        return m_isStatic && m_isDef;
    }

    public boolean isDef() {
        return m_isDef && !isStaticDef();
    }

    private boolean m_isStatic = false, m_isDef = false, m_isImplements = false, m_isInterface = false;
}
