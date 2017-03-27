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
import laol.ast.etc.IName;
import laol.ast.etc.ISymbol;

/**
 *
 * @author gburdell
 */
public class AnonymousFunctionDecl extends Item implements IName, ISymbol {

    public AnonymousFunctionDecl(final laol.parser.apfe.AnonymousFunctionDecl decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_parmName = createItem(seq, 1);
        m_parms = oneOrNone(seq, 2);
        m_return = oneOrNone(seq, 3);
    }

    public MethodParamDecl getParms() {
        return m_parms;
    }

    public MethodReturnDecl getReturn() {
        return m_return;
    }

    public ParamName getParmName() {
        return m_parmName;
    }

    private final ParamName m_parmName;
    private final MethodParamDecl m_parms;
    private final MethodReturnDecl m_return;

    @Override
    public Ident getIdent() {
        return getParmName().getName();
    }

    @Override
    public EType getType() {
        return EType.eAnonFunc;
    }

    /**
     * We're almost a ISymbol --- but we need an actual implementation of
     * getModifiers.
     *
     * @return 0 (invalid).
     */
    @Override
    public int getModifiers() {
        assert (false);
        return 0;
    }

}
