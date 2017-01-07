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

import apfe.runtime.Sequence;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class ModuleDeclaration extends Item {

    public ModuleDeclaration(final laol.parser.apfe.ModuleDeclaration mdecl) {
        super(mdecl);
        final Sequence seq = asSequence();
        m_name = createItem(seq, 1);
        m_items = zeroOrMore(seq, 3);
    }

    public List<Item> getM_items() {
        return Collections.unmodifiableList(m_items);
    }

    public ModuleName getM_name() {
        return m_name;
    }
    
    private final ModuleName m_name;
    private final List<Item>    m_items;
}
