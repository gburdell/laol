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
import apfe.runtime.Marker;
import apfe.runtime.Repetition;
import apfe.runtime.Util;
import static apfe.runtime.Util.downcast;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class ModuleDeclaration extends Item {

    public ModuleDeclaration(final laol.parser.apfe.ModuleDeclaration mdecl) {
        m_loc = mdecl.getStartMark();
        m_name = createItem(Util.extractEle(asSequence(mdecl), 1));
        final Repetition items = Util.extractEle(asSequence(mdecl), 3);
        for (Acceptor item : items.getAccepted()) {
            m_items.add(createItem(asPrioritizedChoice(item).getAccepted()));
        }
    }

    @Override
    public Marker getLocation() {
        return m_loc;
    }

    private final ModuleName m_name;
    private final Marker m_loc;
    private final List<Item>    m_items = new LinkedList<>();
}
