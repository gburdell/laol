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
import apfe.runtime.Acceptor;
import apfe.runtime.Sequence;
import gblib.Util;

/**
 *
 * @author gburdell
 */
public class MethodParamDeclEle extends Item {
    public MethodParamDeclEle(final laol.parser.apfe.MethodParamDeclEle decl) {
        super(decl);
        Sequence seq = asSequence();
        m_modifier = createItem(seq, 0);
        final Acceptor acc = asPrioritizedChoice(seq.itemAt(1)).getAccepted();
        if (acc instanceof laol.parser.apfe.AnonymousFunctionDecl) {
            m_ele = createItem(acc);
        } else {
            m_ele = new Named(acc);
        }
    }
    
    /**
     * A more complex element.
     */
    public static class Named extends Item {
        
        public Named(Acceptor parsed) {
            super(parsed);
            final Sequence seq = asSequence();
            int offset = 0;
            if (seq.itemAt(offset) instanceof laol.parser.apfe.TypeName) {
                m_type = createItem(seq, offset++);
            } else {
                m_type = null;
            }
            m_hasStar = 0 < asRepetition(seq, offset++).sizeofAccepted();
            m_paramName = createItem(seq, offset++);
            m_default = oneOrNone(seq, offset++);
        }

        public MethodParamDeclDefault getDefault() {
            return m_default;
        }

        public ParamName getParamName() {
            return m_paramName;
        }

        public TypeName getType() {
            return m_type;
        }

        public boolean hasStar() {
            return m_hasStar;
        }

        private final TypeName  m_type;
        private final boolean m_hasStar;
        private final ParamName m_paramName;
        private final MethodParamDeclDefault m_default;
    }

    public Item getEle() {
        return m_ele;
    }

    public MethodParamDeclModifier getModifier() {
        return m_modifier;
    }
   
    public boolean isNamed() {
        return getEle() instanceof Named;
    }
    
    public ParamName getParamName() {
        ParamName name;
        if (isNamed()) {
            final Named named = Util.downCast(getEle());
            name = named.getParamName();
        } else {
            final AnonymousFunctionDecl afunc = Util.downCast(getEle());
            name = afunc.getParmName();
        }
        return name;
    }
    
    private final MethodParamDeclModifier   m_modifier;
    private final Item m_ele;
}
