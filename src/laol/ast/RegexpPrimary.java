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

import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class RegexpPrimary extends Item {

    public RegexpPrimary(final laol.parser.apfe.RegexpPrimary decl) {
        super(decl);
        final PrioritizedChoice pc = asPrioritizedChoice();
        final Sequence acc = pc.getAccepted();
        if (0 == pc.whichAccepted()) {
            m_expr = zeroOrMore(acc, 2);
            m_sfx = acc.getText(4);
        } else {
            m_expr = zeroOrMore(acc, 1);
            m_sfx = acc.getText(3);
        }
    }

    public List<RegexpItem> getExpr() {
        return Collections.unmodifiableList(m_expr);
    }
    
    public boolean hasSuffix() {
        return !getSuffix().isEmpty();
    }
    
    public String getSuffix() {
        return m_sfx;
    }
    
    private final List<RegexpItem> m_expr;
    private final String m_sfx;
}
