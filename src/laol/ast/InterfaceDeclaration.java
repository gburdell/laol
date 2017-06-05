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

import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import gblib.Util;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.etc.IModifiers;

/**
 *
 * @author gburdell
 */
public class InterfaceDeclaration extends Item implements IModifiers {

    public InterfaceDeclaration(final laol.parser.apfe.InterfaceDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        m_name = getIdent(seq, 2);
        {
            final Repetition rep = asRepetition(seq, 3);
            if (0 < rep.sizeofAccepted()) {
                m_implements = createItem(rep.getOnlyAccepted(), 1);
            } else {
                m_implements = null;
            }
        }
        m_body = createItem(seq, 5);
    }

    public AccessModifier getAccess() {
        return m_access;
    }

    public InterfaceBody getBody() {
        return m_body;
    }

    public List<String> getBaseNames() {
        return nonNull(getImplements())
                ? getImplements().getNames().stream()
                        .map(scoped -> scoped.toString())
                        .collect(Collectors.toList())
                : gblib.Util.emptyUnmodifiableList();
    }

    public ScopedNameList getImplements() {
        return m_implements;
    }

    public Ident getIdent() {
        return m_name;
    }

    /**
     * Get access privilege for InterfaceDeclaration. Default is private.
     *
     * @return modifiers encoded as per java.lang.reflect.Modifier.
     */
    @Override
    public int getModifiers() {
        return Util.getNonNullValue(
                getAccess(),
                e -> e.getType().getModifier(),
                () -> Modifier.PRIVATE);
    }

    private final AccessModifier m_access;
    private final Ident m_name;
    private final ScopedNameList m_implements;
    private final InterfaceBody m_body;

}
