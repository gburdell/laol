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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gburdell
 */
public class PrimaryExpression extends Item {

    public PrimaryExpression(final laol.parser.apfe.PrimaryExpression decl) {
        super(decl);
        final Acceptor acc = asPrioritizedChoice().getAccepted();
        final Class cls = acc.getClass();
        if (KWRDS.contains(cls)) {
            m_expr = new Keyword(acc);
        } else if (cls == laol.parser.apfe.STRING.class) {
            m_expr = new AString(apfe.runtime.Util.downcast(acc));
        } else if (cls == laol.parser.apfe.SYMBOL.class) {
            m_expr = new Symbol(apfe.runtime.Util.downcast(acc));
        } else if (cls == Sequence.class) {
            //parenthesized expression
            m_expr = createItem(acc, 1);
        } else {
            m_expr = createItem(acc);
        }
    }

    public boolean isKeyword() {
        return (getExpr() instanceof Keyword);
    }

    public boolean isNull() {
        return isKeyword() && (gblib.Util.<Keyword>downCast(getExpr()).toString().equals("nil"));
    }

    public boolean isString() {
        return (getExpr() instanceof AString);
    }

    public boolean isSymbol() {
        return (getExpr() instanceof Symbol);
    }

    public boolean isNumber() {
        return (getExpr() instanceof Number);
    }
    
    public boolean isScopedName() {
        return (getExpr() instanceof ScopedName);
    }
    
    public boolean isSimpleName() {
        return isScopedName() && gblib.Util.<ScopedName>downCast(getExpr()).isSimpleName();
    }
    
    public String toScopedName() {
        assert(isScopedName());
        return gblib.Util.<ScopedName>downCast(getExpr()).toString();
    }
    
    public String toSimpleName() {
        assert(isSimpleName());
        return gblib.Util.<ScopedName>downCast(getExpr()).toString();
    }
    
    public boolean isParenthesizedExpr() {
        return (getExpr() instanceof Expression);
    }
    
    public Item getExpr() {
        return m_expr;
    }

    private final Item m_expr;
    private static final Set<Class> KWRDS = new HashSet<>();

    static {
        final Class classes[] = {
            laol.parser.apfe.KNIL.class,
            laol.parser.apfe.KTRUE.class,
            laol.parser.apfe.KFALSE.class,
            laol.parser.apfe.KSELF.class,};
        KWRDS.addAll(Arrays.asList(classes));
    }
}
