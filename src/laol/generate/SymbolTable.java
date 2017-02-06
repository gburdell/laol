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
package laol.generate;

import static gblib.Util.assertNever;
import java.util.HashMap;

/**
 *
 * @author kpfalzer
 */
public class SymbolTable extends HashMap<String, Symbol> {
    /**
     * Insert symbol.
     * @param key symbol key.
     * @param value symbol value.
     * @return true if key does not exist, else false (and no insert done).
     */
    public boolean insert(String key, Symbol value) {
        boolean ok = false;
        if (! super.containsKey(key)) {
            ok = (null == super.put(key, value));
        }
        return ok;
    }

    @Override
    public Symbol put(String key, Symbol value) {
        assertNever("use insert() method instead");
        return null;
    }
}
