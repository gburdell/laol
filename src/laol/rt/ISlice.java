package laol.rt;

/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
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

/**
 * Methods to manipulate selected objects of a collection.
 * 
 * @author kpfalzer
 */
public interface ISlice extends Iterable {
    /**
     * Assign (1:1) to slice elements.
     * If there are fewer number of items, then null values are assigned
     * to remaining elements in slice.
     * If there are more number of items, then the extra assign items are unused.
     * @param items (Iterator) new values to assign to each element of slice.
     * @return this slice.
     */
    public default ISlice assign(ILaol items) {
        Iterator iter = downCast(items);
        return assignImpl(iter);
    }

    public ISlice assignImpl(Iterator items);
    
    /**
     * Get number of objects in this slice.
     * @return number of objects in this slice.
     */
    public LaolInteger size();
    
    public default LaolBoolean isEmpty() {
        return new LaolBoolean(1 > size().get());
    }
}
