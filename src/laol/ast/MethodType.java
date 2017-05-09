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
import apfe.runtime.Sequence;

/**
 *
 * @author kpfalzer
 */
public class MethodType extends Item {

    public static enum EType {
        eDef, eDefault, eOverride, eStatic
    }

    public MethodType(final laol.parser.apfe.MethodType decl) {
        super(decl);
        final Acceptor acc = asPrioritizedChoice().getAccepted();
        if (acc instanceof laol.parser.apfe.KDEF) {
            m_type = EType.eDef;
        } else {
            final Acceptor first = asSequence(acc).itemAt(0);
            if (first instanceof laol.parser.apfe.KDEFAULT) {
                m_type = EType.eDefault;
            } else if (first instanceof laol.parser.apfe.KOVERRIDE) {
                m_type = EType.eOverride;
            } else {
                m_type = EType.eStatic;
            }
        }
    }

    public EType getType() {
        return m_type;
    }
    
    public boolean isOverrideDef() {
        return (EType.eOverride == getType());
    }

    public boolean isStaticDef() {
        return (EType.eStatic == getType());
    }

    public boolean isDef() {
        return (EType.eDef == getType());
    }
    
    public boolean isDefaultDef() {
        return (EType.eDefault == getType());
    }

    private final EType m_type;
}
