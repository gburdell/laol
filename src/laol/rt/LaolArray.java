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
package laol.rt;

import gblib.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Array/List implementation.
 *
 * @author kpfalzer
 */
public final class LaolArray extends LaolObject {

    public LaolArray() {
        m_eles = new ArrayList();
    }

    public LaolArray(final Collection<? extends LaolObject> items) {
        m_eles = new ArrayList(items);
    }

    //operator <<
    public <T extends LaolObject> LaolArray add(final T val) {
        mutableCheck();
        m_eles.add(val);
        return this;
    }

    //operator []=
    public <T extends LaolObject> LaolObject set(final LaolInteger ix, final T val) {
        mutableCheck();
        final int i = realIndex(ix);
        if (isValidIndex(i)) {
            m_eles.set(i, val);
            return val;
        }
        throw new IndexException(ix);
    }

    public LaolObject get(final LaolInteger ix) {
        return get(ix.get());
    }

    //operator []
    public LaolObject get(final int ix) {
        final int i = realIndex(ix);
        final LaolObject val = isValidIndex(i) ? m_eles.get(i) : null;
        return Util.downCast(val);
    }

    //empty?
    public LaolBoolean isEmpty() {
        return new LaolBoolean(m_eles.isEmpty());
    }

    public LaolInteger size() {
        return new LaolInteger(m_eles.size());
    }

    private int realIndex(int i) {
        i = (0 > i) ? (m_eles.size() + i) : i;
        return i;
    }

    private int realIndex(final LaolInteger ix) {
        return realIndex(ix.get());
    }

    private boolean isValidIndex(int i) {
        return (0 <= i) && (m_eles.size() > i);
    }

    public static class IndexException extends LaolException {

        public IndexException(final LaolInteger ix) {
            super("Invalid index: " + ix.get());
        }

    }

    @Override
    public int hashCode() {
        return m_eles.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LaolArray other = (LaolArray) obj;
        return Objects.equals(this.m_eles, other.m_eles);
    }

    private final ArrayList<LaolObject> m_eles;
}
