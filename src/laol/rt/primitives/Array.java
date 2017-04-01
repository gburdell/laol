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
package laol.rt.primitives;

import static gblib.Util.invariant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import laol.rt.Laol;
import laol.rt.LaolBase;
import laol.rt.LaolException;

/**
 * Array/List implementation. Unlike Ruby array, Array only grows linearly
 in size: i.e., cannot: a = []; a << 4; a[34] = 7;
 * Array is also bounded: so index out of bounds throws exception.
 *
 * @author kpfalzer
 */
public class Array extends LaolBase {

    public Array() {
        m_eles = new ArrayList();
    }

    public Array(final Collection<? extends Laol> items) {
        m_eles = new ArrayList(items);
    }

    //operator <<
    public Laol add(final Laol val) {
        mutableCheck();
        m_eles.add(val);
        return this;
    }

    //operator []=
    public Laol set(final Laol ix, final Laol val) {
        return set(Number.toInteger(ix).get(), val);
    }

    //operator []=
    private Laol set(final int ix, final Laol val) {
        mutableCheck();
        final int i = realIndex(ix);
        if (isValidIndex(i)) {
            m_eles.set(i, val);
            return val;
        }
        throw new IndexException(ix);
    }

    //operator []
    public Laol get(final Laol ix) {
        return get(Number.toInteger(ix).get());
    }

    //operator []
    private Laol get(final int ix) {
        final int i = realIndex(ix);
        final Laol val = isValidIndex(i) ? m_eles.get(i) : null;
        return val;
    }

    //empty?
    public Laol isEmpty() {
        return new Boolean(m_eles.isEmpty());
    }

    public Laol size() {
        return new Integer(m_eles.size());
    }

    private int realIndex(final Laol ix) {
        return realIndex(realIndex(Number.toInteger(ix).get()));
    }

    private int realIndex(int i) {
        i = (0 > i) ? (m_eles.size() + i) : i;
        return i;
    }

    private boolean isValidIndex(int i) {
        return (0 <= i) && (m_eles.size() > i);
    }

    public static class IndexException extends LaolException {

        public IndexException(final Laol ix) {
            this((int)Number.toInteger(ix).get());
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
        final Array other = (Array) obj;
        return Objects.deepEquals(this.m_eles, other.m_eles);
    }

    public Slice slice(Laol startIx, Laol length) {
        return new Slice(startIx, length);
    }
    
    public class Slice implements laol.rt.Slice {

        public Slice(Laol startIx, Laol length) {
            this(Number.toInteger(startIx).get(), Number.toInteger(length).get());
        }

        public Slice(int startIx, int length) {
            m_startIx = startIx;
            m_length = length;
            invariant(0 <= m_length);
            m_lastIx = m_startIx + m_length - 1;
        }

        private final int m_startIx, m_lastIx, m_length;

        @Override
        public laol.rt.Slice assignImpl(laol.rt.Iterator items) {
            Laol newval;
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
        public String toS() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Integer size() {
            return new Integer(m_length);
        }

        public class Iterator implements laol.rt.Iterator {

            private Iterator() {
                m_currIx = m_startIx;
            }

            private int m_currIx;

            @Override
            public Laol next() {
                Laol rval = null;
                if (hasNext().get()) {
                    rval = get(m_currIx++);
                }
                return rval;
            }

            @Override
            public Boolean hasNext() {
                return new Boolean(m_currIx < m_lastIx);
            }

            @Override
            public String toS() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        }

    }

    private final ArrayList<Laol> m_eles;
}
