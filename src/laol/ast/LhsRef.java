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
import apfe.runtime.Util;
import static apfe.runtime.Util.downcast;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class LhsRef extends Item {

    public LhsRef(final laol.parser.apfe.LhsRef decl) {
        super(decl);
        for (Acceptor acc : decl.getItems()) {
            if (acc instanceof laol.parser.apfe.ScopedName) {
                m_items.add(createItem(acc));
            } else {
                final Sequence seq = asSequence(acc);
                final Acceptor accs[] = seq.getAccepted();
                final Class cls = accs[0].getClass();
                if (cls == laol.parser.apfe.LBRACK.class) {
                    m_items.add(new Index(accs[1]));
                } else if (cls == laol.parser.apfe.LPAREN.class) {
                    m_items.add(new Params(seq));
                } else if (cls == apfe.runtime.PrioritizedChoice.class) {
                    m_items.add(new PostOp(asPrioritizedChoice(accs[0]).getAccepted()));
                } else {
                    m_items.add(new AccessOpIdent(seq));
                }
            }
        }
    }

    public static class Params extends Item {

        public ParamExpressionList getParms() {
            return m_parms;
        }

        private Params(final Acceptor acc) {
            super(acc);
            m_parms = oneOrNone(2);
        }
        private final ParamExpressionList m_parms;
    }

    public static class Index extends SelectExpression {

        private Index(final Acceptor expr) {
            super((laol.parser.apfe.SelectExpression) expr);
        }
    }

    public static class AccessOpIdent extends Item {

        private AccessOpIdent(final Sequence opIdent) {
            super(opIdent);
            m_op = createItem(opIdent, 0);
            m_ident = new Ident(opIdent.itemAt(1));
        }

        public Ident getIdent() {
            return m_ident;
        }

        public AccessOp getOp() {
            return m_op;
        }
        
        private final AccessOp m_op;
        private final Ident m_ident;
    }

    public static class PostOp extends Item {

        public EType getM_type() {
            return m_type;
        }

        public static enum EType {
            eIncr, eDecr
        };
        private final EType m_type;

        private PostOp(final Acceptor op) {
            super(op);
            m_type = (op.getClass() == laol.parser.apfe.PLUS2.class) ? EType.eIncr : EType.eDecr;
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(m_items);
    }

    private final List<Item> m_items = new LinkedList<>();
}
