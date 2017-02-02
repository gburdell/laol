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

import apfe.runtime.Sequence;

/**
 *
 * @author gburdell
 */
public class ClassDeclaration extends Item implements IName {

    public ClassDeclaration(final laol.parser.apfe.ClassDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_isExtern = 0 < asRepetition(seq, 0).sizeofAccepted();
        m_access = oneOrNone(seq, 1);
        m_name = createItem(seq, 3);
        m_parms = oneOrNone(seq, 4);
        m_extends = oneOrNone(seq, 5);
        m_body = createItem(seq, 7);
        m_stmtModifier = getStatementModifier(seq, 9);
    }

    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }

    public AccessModifier getAccess() {
        return m_access;
    }

    public ClassBody getBody() {
        return m_body;
    }

    public ClassExtends getExtends() {
        return m_extends;
    }

    @Override
    public ScopedName getName() {
        return m_name;
    }

    public MethodParamDecl getParms() {
        return m_parms;
    }

    public boolean isExtern() {
        return m_isExtern;
    }

    private final boolean m_isExtern;
    private final AccessModifier  m_access;
    //TODO private final boolean m_isAbstract;
    private final ClassName m_name;
    private final MethodParamDecl   m_parms;
    private final ClassExtends  m_extends;
    private final ClassBody m_body;
    private final StatementModifier m_stmtModifier;
}
