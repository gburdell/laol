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

import laol.parser.apfe.KFALSE;
import laol.parser.apfe.KFILE;
import laol.parser.apfe.KNEW;
import apfe.runtime.Acceptor;
import laol.parser.apfe.KNIL;
import laol.parser.apfe.KSUPER;
import laol.parser.apfe.KTARGET;
import laol.parser.apfe.KTHIS;
import laol.parser.apfe.KTRUE;
import laol.parser.apfe.STRING;
import laol.parser.apfe.SYMBOL;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import gblib.Util;
import static apfe.runtime.Util.extractEle;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gburdell
 */
public class PrimaryExpression extends Node {

    @Override
    public ENode getType() {
        return ENode.ePrimaryExpression;
    }

    /*
        K_NIL QMARK?
/   K_NEW
/   K_FILE / K_TARGET
/   K_TRUE / K_FALSE
/   K_THIS / K_SUPER
/   here_doc
/   variable_name (QMARK / EXCL)?
/   STRING
/   SYMBOL
/   Number

     */
    public static enum EType {
        eNil, eNew, eFile, eTarget, eTrue, eFalse,
        eThis, eSuper, eVarName, eString, eSymbol,
        eNumber
    }

    public PrimaryExpression(final laol.parser.apfe.PrimaryExpression acc) {
        final PrioritizedChoice pc = Util.downCast(acc.getBaseAccepted());
        // take it, else 1st of sequence
        Acceptor alt = pc.getAccepted();
        if (alt instanceof Sequence) {
            alt = extractEle(alt, 0);
        }
        m_type = TYPE_BY_CLS.get(alt.getClass());
        switch (m_type) {
            //TODO
            //set qmark and excl
        }
    }

    private boolean m_qmark = false, m_excl = false;
    private final EType m_type;

    private static final Map<Class, EType> TYPE_BY_CLS = new HashMap<>();

    static {
        TYPE_BY_CLS.put(KNIL.class, PrimaryExpression.EType.eNil);
        TYPE_BY_CLS.put(KNEW.class, PrimaryExpression.EType.eNew);
        TYPE_BY_CLS.put(KFILE.class, PrimaryExpression.EType.eFile);
        TYPE_BY_CLS.put(KTARGET.class, PrimaryExpression.EType.eTarget);
        TYPE_BY_CLS.put(KFALSE.class, PrimaryExpression.EType.eFalse);
        TYPE_BY_CLS.put(KTRUE.class, PrimaryExpression.EType.eTrue);
        TYPE_BY_CLS.put(KTHIS.class, PrimaryExpression.EType.eThis);
        TYPE_BY_CLS.put(KSUPER.class, PrimaryExpression.EType.eSuper);
        TYPE_BY_CLS.put(STRING.class, PrimaryExpression.EType.eString);
        TYPE_BY_CLS.put(SYMBOL.class, PrimaryExpression.EType.eSymbol);
        TYPE_BY_CLS.put(laol.parser.apfe.Number.class, PrimaryExpression.EType.eNumber);
        TYPE_BY_CLS.put(laol.parser.apfe.VariableName.class, PrimaryExpression.EType.eVarName);
    }

}
