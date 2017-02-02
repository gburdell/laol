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
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class VarDeclStatement extends Item implements IName {
    public VarDeclStatement(final laol.parser.apfe.VarDeclStatement decl) {
        super(decl);
        final Sequence seq = asSequence();
        final PrioritizedChoice pc = asPrioritizedChoice(seq.itemAt(0));
        Acceptor acc = pc.getAccepted();
        if (acc instanceof Sequence) {
            final Sequence seq2 = asSequence(acc);
            m_type = createItem(seq2, 0);
            m_names.add(createItem(seq2, 1));
        } else {
            m_type = null;
            m_names.add(createItem(acc));
        }
        m_names.addAll(zeroOrMore(asRepetition(seq, 1), 1));
        final Repetition rep = asRepetition(seq, 2);
        if (0 < rep.sizeofAccepted()) {
            final Sequence seq3 = rep.getOnlyAccepted();
            m_op = createItem(seq3, 0);
            m_rhs = createItem(seq3, 1);
        } else {
            m_op = null;
            m_rhs = null;
        }
        m_stmtModifier = getStatementModifier(seq, 3);
    }

    @Override
    public List<ScopedName> getNames() {
        return Collections.unmodifiableList(m_names);
    }

    public AssignmentOp getOp() {
        return m_op;
    }

    public AssignmentRhs getRhs() {
        return m_rhs;
    }

    public StatementModifier getStmtModifier() {
        return m_stmtModifier;
    }

    public TypeDecl getType() {
        return m_type;
    }
 
    private final TypeDecl  m_type;
    private final List<ScopedName>    m_names = new LinkedList<>();
    private final AssignmentOp  m_op;
    private final AssignmentRhs m_rhs;
    private final StatementModifier m_stmtModifier;
}
