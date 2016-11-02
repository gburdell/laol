package laol.rt;

/**
 * Base class of all objects.
 *
 * @author kpfalzer
 */
public abstract class LaolObject {

    protected LaolObject() {
    }

    public final void setMutable() {
        m_mutable = true;
    }

    public final boolean isMutable() {
        return m_mutable;
    }

    protected void mutableCheck(final boolean enable) {
        if (enable && !m_mutable) {
            throw new LaolException.Immutable();
        }
    }

    protected void mutableCheck() {
        mutableCheck(true);
    }

    public boolean isNull() {
        return false;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
    
    private boolean m_mutable = false;
}
