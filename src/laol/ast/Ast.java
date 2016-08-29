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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import apfe.runtime.Util;
import java.util.LinkedList;
import java.util.List;
import laol.parser.apfe.Contents;

/**
 *
 * @author gburdell
 */
public class Ast extends Item {
    public Ast(final Acceptor grammar) {
        super(grammar);
        Contents contents = Util.getOnlyElement(Util.<Repetition>extractEle(asSequence(), 1));
        Sequence seq = asSequence(contents);
        for (Acceptor stmt : asRepetition(seq, 0).getAccepted()) {
            m_requireStmts.add(new RequireStatement(stmt));
        }
        for (Acceptor fileItem : asRepetition(seq, 1).getAccepted()) {
            add(asPrioritizedChoice(fileItem).getAccepted());
        }
    }
    
    private void add(final Acceptor item) {
        m_items.add(createItem(item));
    }
    
    private final List<RequireStatement>    m_requireStmts = new LinkedList<>();
    /**
     * Collection of ModuleDeclaration and Statement.
     */
    private final List<Item>   m_items = new LinkedList<>();
}
