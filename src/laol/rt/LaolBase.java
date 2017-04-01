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

import gblib.Util;

/**
 * Base class of all objects.
 * Basically an Object witht mutability attribute.
 *
 * @author kpfalzer
 */
public abstract class LaolBase implements Laol {

    /**
     * Set immutable object.
     * @param <T> subclass of LaolBase.
     * @param val object to make immutable.
     * @return immutable object.
     */
    public static <T extends LaolBase> T asConst(final T val) {
        return val.setMutable(false);
    }
    
    /**
     * Set mutable object.
     * @param <T> subclass of LaolBase.
     * @param val object to make mutable.
     * @return mutable object.
     */
    public static <T extends LaolBase> T asVar(final T val) {
        return val.setMutable(true);
    }
    
    public final <T extends LaolBase> T setMutable(boolean val) {
        m_mutable = val;
        return Util.downCast(this);
    }

    public final <T extends LaolBase> T setMutable() {
        return setMutable(true);
    }

    public final boolean isMutable() {
        return m_mutable;
    }

    protected void mutableCheck(final boolean enable) {
        if (enable && !m_mutable) {
            throw new LaolException.ImmutableException();
        }
    }

    protected void mutableCheck() {
        mutableCheck(true);
    }

    @Override
    public LaolString toS() {
        return new LaolString(super.toString());
    }

    private boolean m_mutable = false;
}
