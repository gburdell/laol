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
import apfe.runtime.LeftRecursiveAcceptor;
import apfe.runtime.Sequence;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class BinaryOp extends Item {

    public BinaryOp(final laol.parser.apfe.BinaryOp decl) {
        super(decl);
        Acceptor acc = asPrioritizedChoice().getAccepted();
        Class clz = acc.getClass();
        if (!gblib.Util.isUpperCase(clz.getSimpleName())) {
            //assume leaf op based on all-caps naming convention
            acc = asPrioritizedChoice(acc).getAccepted();
            clz = acc.getClass();
            assert(gblib.Util.isUpperCase(clz.getSimpleName()));
        }
        m_op = clz;
    }

    public Class getOp() {
        return m_op;
    }
    
    private final Class m_op;
    
    /**
     * Helper class.
     * @param <T> a left recursive nonterminal.
     */
    public static class LRExpr<T extends LeftRecursiveAcceptor> extends Item {

        protected LRExpr(final T decl) {
            super(decl);
            for (Acceptor ele : decl.getItems()) {
                if (ele instanceof Sequence) {
                    m_items.add(createItem(ele, 0));
                    m_items.add(createItem(ele, 1));
                } else {
                    m_items.add(createItem(ele));
                }
            }

        }

        public List<Item> getItems() {
            return Collections.unmodifiableList(m_items);
        }

        private final List<Item> m_items = new LinkedList<>();
    }
}
