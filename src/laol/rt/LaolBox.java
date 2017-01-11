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

import java.util.Objects;

/**
 * Box types.
 *
 * @author kpfalzer
 * @param <T> type to box.
 */
public abstract class LaolBox<T> extends LaolObject implements Cloneable {

    public LaolBox(final T val) {
        m_val = val;
    }

    public final LaolBox<T> set(final T val) {
        mutableCheck();
        m_val = val;
        return this;
    }

    public final T get() {
        return m_val;  //could be null
    }

    @Override
    public int hashCode() {
        return m_val.hashCode();
    }

    @Override
    public LaolString toS() {
        return new LaolString(m_val.toString());
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
        final LaolBox<?> other = (LaolBox<?>) obj;
        return Objects.equals(this.m_val, other.m_val);
    }

    private T m_val;

}
