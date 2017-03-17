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

import static gblib.Util.invariant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Array/List implementation. Unlike Ruby array, LaolArray only grows linearly
 * in size: i.e., cannot: a = []; a << 4; a[34] = 7;
 * Array is also bounded: so index out of bounds throws exception.
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
        return set(LaolNumber.toInteger(ix).get(), val);
    }

    //operator []=
    private ILaol set(final int ix, final ILaol val) {
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
        return get(LaolNumber.toInteger(ix).get());
    }

    //operator []
    private ILaol get(final int ix) {
        final int i = realIndex(ix);
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
            this((int)LaolNumber.toInteger(ix).get());
        }

        public IndexException(final int ix) {
            super("Invalid index: " + ix);
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

    public Slice slice(ILaol startIx, ILaol length) {
        return new Slice(startIx, length);
    }
    
    public class Slice implements ISlice {

        public Slice(ILaol startIx, ILaol length) {
            this(LaolNumber.toInteger(startIx).get(), LaolNumber.toInteger(length).get());
        }

        public Slice(int startIx, int length) {
            m_startIx = startIx;
            m_length = length;
            invariant(0 <= m_length);
            m_lastIx = m_startIx + m_length - 1;
        }

        private final int m_startIx, m_lastIx, m_length;

        @Override
        public ISlice assignImpl(laol.rt.Iterator items) {
            ILaol newval;
            for (int ix = m_startIx; ix < m_lastIx; ix++) {
                newval = items.next();  //we get null (ok!) if exhausted
                set(ix, newval);
            }
            return this;
        }

        @Override
        public Iterator iterator() {
            return new Iterator();
        }

        @Override
        public LaolString toS() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public LaolInteger size() {
            return new LaolInteger(m_length);
        }

        public class Iterator implements laol.rt.Iterator {

            private Iterator() {
                m_currIx = m_startIx;
            }

            private int m_currIx;

            @Override
            public ILaol next() {
                ILaol rval = null;
                if (hasNext().get()) {
                    rval = get(m_currIx++);
                }
                return rval;
            }

            @Override
            public LaolBoolean hasNext() {
                return new LaolBoolean(m_currIx < m_lastIx);
            }

            @Override
            public LaolString toS() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        }

    }

    private final ArrayList<ILaol> m_eles;
}
