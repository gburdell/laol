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
import apfe.runtime.Marker;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class InterfaceDeclaration extends Item {
    public InterfaceDeclaration(final laol.parser.apfe.InterfaceDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_isExtern = 0 < asRepetition(seq, 0).sizeofAccepted();
        m_access = oneOrNone(seq, 1);
        m_name = createItem(seq, 3);
        {
            final Repetition rep = asRepetition(seq, 4);
            if (0 < rep.sizeofAccepted()) {
                m_implements = createItem(rep.getOnlyAccepted(), 1);
            } else {
                m_implements = null;
            }
        }
        m_body = createItem(seq, 6);
        m_stmtModifier = getStatementModifier(seq, 8);
    }

    public AccessModifier getAccess() {
        return m_access;
    }

    public ClassBody getBody() {
        return m_body;
    }

    public ClassNameList getImplements() {
        return m_implements;
    }

    public ClassName getName() {
        return m_name;
    }

    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }
    
    private final boolean           m_isExtern;
    private final AccessModifier    m_access;
    private final ClassName         m_name;
    private final ClassNameList     m_implements;
    private final ClassBody         m_body;
    private final StatementModifier m_stmtModifier;
    
}
