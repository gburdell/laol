/*
 * The MIT License
 *
 * Copyright 2016 kpfalzer.
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

import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;

/**
 * We use exact methods here to catch over/underflow. TODO: If we catch these,
 * use BigInteger...
 *
 * @author kpfalzer
 */
public interface LaolNumber {

    /**
     * Conversion to LaolInteger.
     * @return conversion to 'int' as LaolInteger.
     */
    public LaolObject toInteger();

    /**
     * Conversion to LaolDouble.
     * @return conversion to 'double' as LaolDouble.
     */
    public LaolObject toDouble();

    public LaolObject addOp(LaolObject b);

    public LaolObject subOp(LaolObject b);

    public LaolObject multOp(LaolObject b);

    public LaolObject divOp(LaolObject b);

    static final LaolObject ONE = new LaolInteger(1);

    default public LaolObject preIncrOp() {
        set(addOp(ONE));
        return LaolObject.class.cast(this);
    }

    default public LaolObject preDecrOp() {
        set(subOp(ONE));
        return LaolObject.class.cast(this);
    }

    default public LaolObject postIncrOp() {
        LaolObject currVal = clone();
        set(addOp(ONE));
        return currVal;
    }

    default public LaolObject postDecrOp() {
        LaolObject currVal = clone();
        set(subOp(ONE));
        return currVal;
    }

    public void set(LaolObject val);

    public LaolObject clone();

    /**
     * Convert LaolNumber to LaolDouble. 
     * @param o Subclass of LaolNumber to convert.
     * @return conversion to LaolDouble.
     */
    public static LaolDouble toDouble(final LaolObject o) {
        return LaolDouble.class.cast(LaolNumber.class.cast(o).toDouble());
    }

    /**
     * Convert LaolNumber to LaolInteger. 
     * @param o Subclass of LaolNumber to convert.
     * @return conversion to LaolInteger.
     */
    public static LaolInteger toInteger(final LaolObject o) {
        return LaolInteger.class.cast(LaolNumber.class.cast(o).toInteger());
    }

    default public LaolObject binaryDblOp(LaolObject a, LaolObject b, DoubleBinaryOperator op) {
        final double r = op.applyAsDouble(toDouble(a).get(), toDouble(b).get());
        return new LaolDouble(r);
    }

    default public LaolObject binaryIntOp(LaolObject a, LaolObject b, IntBinaryOperator op) {
        final int r = op.applyAsInt(toInteger(a).get(), toInteger(b).get());
        return new LaolInteger(r);
    }
}
