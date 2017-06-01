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

/**
 *
 * @author gburdell
 */
public class ImportName extends Item {
    public ImportName(final laol.parser.apfe.ImportName decl) {
        super(decl);
        m_isSystem = (0 == asPrioritizedChoice().whichAccepted());
        if (m_isSystem) {
            m_name = createItem(asSequence(asPrioritizedChoice().getAccepted()), 1);
        } else {
            m_name = createItem(asPrioritizedChoice().getAccepted());
        }
    }
    
    public ScopedName getScopedName() {
        return m_name;
    }
    
    /**
     * Return true if name was specified as '<name>' (in brackets).
     * @return true if system name.
     */
    public boolean isSystemName() {
        return m_isSystem;
    }
    
    private final ScopedName    m_name;
    private final boolean       m_isSystem;
}
