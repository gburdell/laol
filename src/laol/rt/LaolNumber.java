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
public interface LaolNumber extends Laol {

    /**
     * Conversion to LaolInteger.
     * @return conversion to 'int' as LaolInteger.
     */
    public Laol toInteger();

    /**
     * Conversion to LaolDouble.
     * @return conversion to 'double' as LaolDouble.
     */
    public Laol toDouble();

    public Laol addOp(Laol b);

    public Laol subOp(Laol b);

    public Laol multOp(Laol b);

    public Laol divOp(Laol b);

    static final Laol ONE = new LaolInteger(1);

    default public Laol preIncrOp() {
        set(addOp(ONE));
        return Laol.class.cast(this);
    }

    default public Laol preDecrOp() {
        set(subOp(ONE));
        return Laol.class.cast(this);
    }

    default public Laol postIncrOp() {
        Laol currVal = clone();
        set(addOp(ONE));
        return currVal;
    }

    default public Laol postDecrOp() {
        Laol currVal = clone();
        set(subOp(ONE));
        return currVal;
    }

    public void set(Laol val);

    public Laol clone();

    /**
     * Convert LaolNumber to LaolDouble. 
     * @param o Subclass of LaolNumber to convert.
     * @return conversion to LaolDouble.
     */
    public static LaolDouble toDouble(final Laol o) {
        return LaolDouble.class.cast(LaolNumber.class.cast(o).toDouble());
    }

    /**
     * Convert LaolNumber to LaolInteger. 
     * @param o Subclass of LaolNumber to convert.
     * @return conversion to LaolInteger.
     */
    public static LaolInteger toInteger(final Laol o) {
        return LaolInteger.class.cast(LaolNumber.class.cast(o).toInteger());
    }

    default public Laol binaryDblOp(Laol a, Laol b, DoubleBinaryOperator op) {
        final double r = op.applyAsDouble(toDouble(a).get(), toDouble(b).get());
        return new LaolDouble(r);
    }

    default public Laol binaryIntOp(Laol a, Laol b, IntBinaryOperator op) {
        final int r = op.applyAsInt(toInteger(a).get(), toInteger(b).get());
        return new LaolInteger(r);
    }
}
