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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class ClassBody extends Item implements IStatements {

    public ClassBody(final laol.parser.apfe.ClassBody decl) {
        super(decl);
        m_base = zeroOrMore(0);
        m_stmts = zeroOrMore(1);
    }

    /**
     * Get base class initializer which match actual base names.
     * @param baseNames list of base class names.
     * @return base class initializers.
     * The initializers are removed from here upon return.
     */
    public List<BaseClassInitializer> getBaseInitializers(List<String> baseNames) {
        List<BaseClassInitializer> inits = new LinkedList<>();
        getBase().stream()
                .filter(init -> baseNames.contains(init.getBaseName()))
                .forEach(init -> inits.add(init));
        inits.forEach(init -> remove(init));
        return inits;
    }
    
    /**
     * Get list of possible base class initializers.
     * If the leadining IDENT does not match a base class, then 
     * actually a statement.
     * 
     * @return possible base class initializers.
     */
    private List<BaseClassInitializer> getBase() {
        return Collections.unmodifiableList(isNonNull(m_base) ? m_base : Collections.emptyList());
    }

    /**
     * Remove initializer after processed.
     * We do so in order to keep any statements which actually matched
     * as base initializer.
     * @param init initializer to remove.
     */
    private void remove(BaseClassInitializer init) {
        final boolean ok = m_base.remove(init);
        assert(ok);
    }
    
    /**
     * Get statements of class body.
     * If there are any remaining BaseClassInitializer remaining; then
     * those should be processed first as statement-like.
     * @return class body statements.
     */
    @Override
    public List<Statement> getStatements() {
        assert(getBase().isEmpty());    //see above
        return Collections.unmodifiableList(isNonNull(m_stmts) ? m_stmts : Collections.emptyList());
    }

    private final List<BaseClassInitializer> m_base;
    private final List<Statement> m_stmts;
}
