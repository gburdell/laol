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
import java.util.List;
import laol.ast.etc.IStatements;

/**
 *
 * @author gburdell
 */
public class AnonymousFunctionDefn extends Item implements IStatements {

    public AnonymousFunctionDefn(final laol.parser.apfe.AnonymousFunctionDefn decl) {
        super(decl);
        final Sequence seq = asSequence(asPrioritizedChoice().getAccepted());
        final boolean isAnon = (seq.itemAt(0).getClass() == laol.parser.apfe.ARROW.class);
        if (isAnon) {
            m_parms = gblib.Util.<MethodParamDecl>downCast(createItem(seq, 1)).getDecl();
        } else {
            m_parms = MethodParamDeclList.class.cast(createItem(seq, 2)).getParms();
        }
        m_return = oneOrNone(seq, isAnon ? 2 : 4);
        m_body = oneOrNone(seq, isAnon ? 4 : 5);
    }

    public MethodBody getBody() {
        return m_body;
    }

    public List<MethodParamDeclEle> getParms() {
        return m_parms;
    }

    public MethodReturnDecl getReturn() {
        return m_return;
    }

    @Override
    public List<Statement> getStatements() {
        return getBody().getStatements();
    }

    private final List<MethodParamDeclEle> m_parms;
    private final MethodReturnDecl m_return;
    private final MethodBody m_body;

}
