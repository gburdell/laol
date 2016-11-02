package laol.rt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Map implementation which uses LinkedHashMap to maintain (insertion) key order.
 * 
 * @author kpfalzer
 */
public final class LaolMap extends LaolObject {

    public LaolMap() {
    }
    
    //operator []=
    public LaolObject set(final LaolObject key, final LaolObject val) {
        mutableCheck();
        m_map.put(key, val);
        return val;
    }
    
    //operator []
    public LaolObject get(final LaolObject key) {
        LaolObject val = m_map.get(key);
        if (null == val) {
            val = Null.getNull();
        }
        return val;
    }
    
    //empty?
    public LaolBoolean isEmpty() {
        return new LaolBoolean(m_map.isEmpty());
    }
    
    public LaolInteger size() {
        return new LaolInteger(m_map.size());
    }

    @Override
    public int hashCode() {
        return m_map.hashCode();
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
        final LaolMap other = (LaolMap) obj;
        return Objects.equals(this.m_map, other.m_map);
    }
    
    private final Map<LaolObject, LaolObject> m_map = new LinkedHashMap<>();
}
