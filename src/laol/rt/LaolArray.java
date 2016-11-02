package laol.rt;

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
    public LaolArray add(final LaolObject val) {
        mutableCheck();
        m_eles.add(val);
        return this;
    }

    //operator []=
    public LaolObject set(final LaolInteger ix, final LaolObject val) {
        mutableCheck();
        final int i = realIndex(ix);
        if (isValidIndex(i)) {
            m_eles.set(i, val);
            return val;
        }
        throw new IndexException(ix);
    }

    //operator []
    public LaolObject get(final LaolInteger ix) {
        final int i = realIndex(ix);
        final LaolObject val = isValidIndex(i) ? m_eles.get(i) : Null.getNull();
        return val;
    }

    //empty?
    public LaolBoolean isEmpty() {
        return new LaolBoolean(m_eles.isEmpty());
    }

    public LaolInteger size() {
        return new LaolInteger(m_eles.size());
    }

    private int realIndex(final LaolInteger ix) {
        int i = ix.get();
        i = (0 > i) ? (m_eles.size() + i) : i;
        return i;
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
