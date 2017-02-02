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
import apfe.runtime.Util;
import laol.parser.IDENT;

/**
 *
 * @author gburdell
 */
public class Ident extends Item {

    public Ident(final AString.S name) {
        super(name.getParsed());
        m_id = name.toString();
        m_sfx = null;
    }
    
    public Ident(final Acceptor id) {
        this(Util.downcast(id));
    }
    
    public Ident(final IDENT id) {
        super(id);
        final String s = id.getIdent();
        if (s.endsWith("?")) {
            m_id = s.substring(0, s.length() - 1);
            m_sfx = '?';
        } else {
            m_id = s;
            m_sfx = null;
        }
    }

    public String getId() {
        return m_id;
    }
    
    public Character getSfx() {
        return m_sfx;
    }
    
    private final String m_id;
    private final Character m_sfx;

}
