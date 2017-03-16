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
 * Unlike Ruby array, LaolArray only grows linearly in size:
 * i.e., cannot: a = []; a << 4; a[34] = 7;
 *
 * @author kpfalzer
 */
public class LaolArray extends LaolObject {

    public LaolArray() {
        m_eles = new ArrayList();
    }

    public LaolArray(final Collection<? extends ILaol> items) {
        m_eles = new ArrayList(items);
    }

    //operator <<
    public ILaol add(final ILaol val) {
        mutableCheck();
        m_eles.add(val);
        return this;
    }

    //operator []=
    public ILaol set(final ILaol ix, final ILaol val) {
        mutableCheck();
        final int i = realIndex(ix);
        if (isValidIndex(i)) {
            m_eles.set(i, val);
            return val;
        }
        throw new IndexException(ix);
    }

    //operator []
    public ILaol get(final ILaol ix) {
        final int i = realIndex(LaolNumber.toInteger(ix).get());
        final ILaol val = isValidIndex(i) ? m_eles.get(i) : null;
        return val;
    }

    //empty?
    public ILaol isEmpty() {
        return new LaolBoolean(m_eles.isEmpty());
    }

    public ILaol size() {
        return new LaolInteger(m_eles.size());
    }

    private int realIndex(final ILaol ix) {
        return realIndex(realIndex(LaolNumber.toInteger(ix).get()));
    }
    
    private int realIndex(int i) {
        i = (0 > i) ? (m_eles.size() + i) : i;
        return i;
    }

    private boolean isValidIndex(int i) {
        return (0 <= i) && (m_eles.size() > i);
    }

    public static class IndexException extends LaolException {

        public IndexException(final ILaol ix) {
            super("Invalid index: " + LaolNumber.toInteger(ix));
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
        return Objects.deepEquals(this.m_eles, other.m_eles);
    }

    private final ArrayList<ILaol> m_eles;
}
