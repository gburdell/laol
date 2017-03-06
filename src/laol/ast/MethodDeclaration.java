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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import gblib.Util;
import laol.parser.apfe.KDEFAULT;
import laol.parser.apfe.KSTATIC;

/**
 *
 * @author gburdell
 */
public class MethodDeclaration extends Item implements IName {
    public MethodDeclaration(final laol.parser.apfe.MethodDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        {
            final Repetition rep = seq.itemAt(1).getBaseAccepted();
            if (null == rep) {
                m_isDefault = false;
                m_isStatic = false;
            } else if (rep.getOnlyAccepted() instanceof KSTATIC) {
                m_isStatic = true;
                m_isDefault = false;
            } else {
                assert(rep.getOnlyAccepted() instanceof KDEFAULT);
                m_isDefault = true;
                m_isStatic = false;
            }
        }
        m_name = createItem(seq, 3);
        m_parmDecl = oneOrNone(seq, 4);
        m_retnDecl = oneOrNone(seq, 5);
        {
            final Repetition rep = asRepetition(seq, 6);
            if (0 < rep.sizeofAccepted()) {
                m_body = createItem(asSequence(rep.getOnlyAccepted()), 1);
            } else {
                m_body = null;
            }
        }
        m_stmtModifier = getStatementModifier(seq, 7);
    }

    public AccessModifier getAccess() {
        return m_access;
    }

    public MethodBody getBody() {
        return m_body;
    }

    @Override
    public ScopedName getName() {
        final Item name = m_name.getName();
        ScopedName scopedName;
        if (name instanceof ScopedName) {
            scopedName = Util.downCast(name);
        } else {
            MethodNameOp op = Util.downCast(name);
            scopedName = new ScopedName(op.getOpName());
        }
        return scopedName;
    }

    public MethodParamDecl getParmDecl() {
        return m_parmDecl;
    }

    public MethodReturnDecl getRetnDecl() {
        return m_retnDecl;
    }

    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }

    public boolean isDefault() {
        return m_isDefault;
    }

    public boolean isStatic() {
        return m_isStatic;
    }
    
    private final AccessModifier    m_access;
    private final boolean   m_isStatic, m_isDefault;
    private final MethodName    m_name;
    private final MethodParamDecl   m_parmDecl;
    private final MethodReturnDecl  m_retnDecl;
    private final MethodBody    m_body;
    private final StatementModifier m_stmtModifier;
    
    
}
