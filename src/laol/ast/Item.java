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
package laol.ast;

import apfe.runtime.Acceptor;
import apfe.runtime.Marker;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Sequence;
import gblib.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import laol.parser.IDENT;

/**
 *
 * @author gburdell
 */
public abstract class Item {

    public Marker getLocation() {
        return null;
    }

    final protected Ident getIdent(final Acceptor seq, final int ix) {
        return new Ident(apfe.runtime.Util.<IDENT>extractEle(seq, 1));
    }

    public static Sequence asSequence(Acceptor acc) {
        return apfe.runtime.Util.asSequence(acc);
    }

    public static PrioritizedChoice asPrioritizedChoice(Acceptor acc) {
        return apfe.runtime.Util.asPrioritizedChoice(acc);
    }

    /**
     * Our AST classes are named same as the parser classes, so we can use
     * this consistency to automatically/dynamically call constructors to
     * create AST objects from parser objects.
     * 
     * @param <T> type of AST object
     * @param <A> type of parser object
     * @param acc parser object
     * @return AST object created from parser object.
     */
    final protected <T extends Item, A extends Acceptor> T createItem(final A acc) {
        T item = null;
        try {
            String accName = acc.getClass().getSimpleName();
            final String astClsName = getClass().getPackage().getName() + "." + accName;
            final Class astCls = Class.forName(astClsName);
            Constructor astCons = astCls.getConstructor(acc.getClass());
            item = (T) astCons.newInstance(acc);
            return item;
        } catch (ClassNotFoundException ex) {
            Util.abnormalExit(ex);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Util.abnormalExit(ex);
        }
        return item;
    }
}
