package laol.rt;

import java.util.Objects;

/**
 * Box primitive types.
 * 
 * @author kpfalzer
 * @param <T> type to box.
 */
public class LaolPrimitive<T> extends LaolObject {
    public LaolPrimitive(final T val) {
        m_val = val;
    }
    
    public final LaolPrimitive set(final T val) {
        mutableCheck();
        m_val = val;
        return this;
    }
    
    public final T get() {
        return m_val;
    }

    @Override
    public int hashCode() {
        return m_val.hashCode();
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
        final LaolPrimitive<?> other = (LaolPrimitive<?>) obj;
        return Objects.equals(this.m_val, other.m_val);
    }
    
    private T m_val;
}
