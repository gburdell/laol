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
package laol.rt;

import laol.rt.primitives.String;
import gblib.Util;
import java.lang.invoke.MethodHandle;
import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Tagging interface base for all Laol objects (and interfaces).
 *
 * @author kpfalzer
 */
public interface Laol {

    public String toS();

    /**
     * Alias for callPublic method.
     *
     * @param method public method to call.
     * @param argv argument(s).
     * @return result of method or Void.
     */
    public default Laol cm(final java.lang.String method, Laol... argv) {
        return callPublic(method, argv);
    }

    public default Laol callPublic(final java.lang.String method, Laol... argv) {
        Laol rval = null;
        try {
            rval = (Laol) getHandle(this.getClass(), method, argv.length)
                    .invoke(this, argv);
        } catch (Exception ex) {
            throw new LaolException(ex);
        } catch (Throwable ex) {
            throw new LaolException(ex);
        }
        return rval;
    }

    /*private*/ static final Map<java.lang.String, MethodHandle> HANDLE_BY_NAME = new HashMap<>();

    /*private*/ default MethodHandle getHandle(Class clz, java.lang.String name, int arity) throws NoSuchMethodException, IllegalAccessException {
        final java.lang.String key = clz.getName() + "/" + name + "#" + arity;
        MethodHandle handle = HANDLE_BY_NAME.get(key);
        if (null == handle) {
            final Class<Laol> rtypes[] = new Class[arity];
            Arrays.fill(rtypes, Laol.class);
            NoSuchMethodException lastExc = null;
            for (Class rtnClz : new Class[]{Laol.class, Void.class}) {
                try {
                    handle = publicLookup()
                            .findVirtual(
                                    clz,
                                    name,
                                    methodType(
                                            rtnClz, //return types
                                            rtypes //arg types
                                    ));
                    lastExc = null;
                } catch (NoSuchMethodException ex) {
                    lastExc = ex;
                }
                if (null != handle) {
                    break;
                }
            }
            if (null != lastExc) {
                throw lastExc;
            }
            //arguments to method are p1,...,p2 (spread: not array)
            handle = handle.asSpreader(Laol[].class, arity);
            HANDLE_BY_NAME.put(key, handle);
        }
        return handle;
    }

    // Convenient routines
    public default <T extends Laol> T downCast(Laol ele) {
        return Util.downCast(ele);
    }

    public default boolean isNull(Object ele) {
        return Objects.isNull(ele);
    }

    public default boolean isNonNull(Object ele) {
        return !isNull(ele);
    }
}
