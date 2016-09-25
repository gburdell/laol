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
        final Acceptor acc = asPrioritizedChoice().getBaseAccepted();
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
    
    private final Item  m_expr;
    private static final Set<Class> KWRDS = new HashSet<>();
    
    static {
        final Class classes[] = {
            laol.parser.apfe.KNEW.class,
            laol.parser.apfe.KNIL.class,
            laol.parser.apfe.KFILE.class,
            laol.parser.apfe.KTARGET.class,
            laol.parser.apfe.KTRUE.class,
            laol.parser.apfe.KFALSE.class,
            laol.parser.apfe.KSUPER.class,
            laol.parser.apfe.KTHIS.class
        };
        KWRDS.addAll(Arrays.asList(classes));
    }
}
