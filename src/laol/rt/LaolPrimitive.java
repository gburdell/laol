package laol.rt;

/**
 *
 * @author kpfalzer
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
    
    private T m_val;
}
