package laol.rt;

/**
 * Base class of all objects.
 *
 * @author kpfalzer
 */
public abstract class LaolObject {

    protected LaolObject() {
    }

    protected final void setMutability() {
        m_mutable = true;
    }

    protected final boolean isMutable() {
        return m_mutable;
    }

    protected void mutableCheck(final boolean enable) {
        if (enable && !m_mutable) {
            throw new RuntimeException("cannot change immutable object");
        }
    }

    protected void mutableCheck() {
        mutableCheck(true);
    }

    private boolean m_mutable = false;
}
