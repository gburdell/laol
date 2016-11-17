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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Map implementation which uses LinkedHashMap to maintain (insertion) key
 * order.
 *
 * @author kpfalzer
 */
public class LaolMap extends LaolObject {

    public LaolMap() {
    }

    //operator []=
    public <K extends LaolObject, V extends LaolObject>
            V set(final K key, final V val) {
        mutableCheck();
        m_map.put(key, val);
        return val;
    }

    //operator []
    public <K extends LaolObject, V extends LaolObject>
            V get(final Class<V> valCls, final K key) {
        return valOrNull(valCls, m_map.get(key));
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

    @Override
    public LaolMap.Null getNull() {
        return new LaolMap.Null();
    }

    public static class Null extends LaolMap {

        @Override
        public boolean isNull() {
            return false;
        }
    }

    private final Map<LaolObject, LaolObject> m_map = new LinkedHashMap<>();
}
