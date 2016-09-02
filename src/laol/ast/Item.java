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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import gblib.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import laol.parser.IDENT;

/**
 *
 * @author gburdell
 */
public abstract class Item {
    protected Item(final Marker loc) {
        this(loc, null);
    }
    
    protected Item(final Acceptor parsed) {
        this(parsed.getStartMark(), parsed);
    }
    
    private Item(final Marker loc, final Acceptor parsed) {
        m_loc = loc;
        m_parsed = parsed;
    }
    
    public Marker getLocation() {
        return m_loc;
    }

    private final Marker   m_loc;
    protected final Acceptor m_parsed;
    
    final protected Ident getIdent(final int ix) {
        return new Ident(apfe.runtime.Util.<IDENT>extractEle(asSequence(), ix));
    }

    final protected Ident getIdent(final Acceptor seq, final int ix) {
        return new Ident(apfe.runtime.Util.<IDENT>extractEle(seq, ix));
    }

    final protected Sequence asSequence() {
        return asSequence(m_parsed);
    }
    
    public static Sequence asSequence(final Acceptor acc) {
        return apfe.runtime.Util.asSequence(acc);
    }

    final protected Repetition asRepetition(final int pos) {
        return asRepetition(asSequence(), pos);
    }
    
    public static Repetition asRepetition(final Sequence seq, final int pos) {
        return apfe.runtime.Util.extractEle(seq, pos);
    }

    final protected <T extends Item> T oneOrNone(final int pos) {
        return oneOrNone(asSequence(), pos);
    }
    
    final protected <T extends Item> T oneOrNone(final Sequence seq, final int pos) {
        final Repetition rep = asRepetition(seq, pos);
        T rval = null;
        if (0 < rep.sizeofAccepted()) {
            rval = createItem(rep.getOnlyAccepted());
        }
        return rval;
    }

    final protected <T extends Item> List<T> zeroOrMore(final int pos) {
        return zeroOrMore(asSequence(), pos);
    }
    
    final protected <T extends Item> List<T> zeroOrMore(final Sequence seq, final int pos) {
        final Repetition rep = asRepetition(seq, pos);
        List<T> rval;
        if (0 < rep.sizeofAccepted()) {
            rval = new LinkedList<>();
            for (Acceptor item : rep.getAccepted()) {
                rval.add(createItem(item));
            }
        } else {
            rval = Collections.EMPTY_LIST;
        }
        return rval;
    }
    
    final protected PrioritizedChoice asPrioritizedChoice() {
        return asPrioritizedChoice(m_parsed);
    }

    final protected PrioritizedChoice asPrioritizedChoice(final int pos) {
        return asPrioritizedChoice(apfe.runtime.Util.extractEle(asSequence(), pos));
    }
    
    public static PrioritizedChoice asPrioritizedChoice(final Acceptor acc) {
        return apfe.runtime.Util.asPrioritizedChoice(acc);
    }

    /**
     * Our AST classes are named same as the parser classes, so we can use this
     * consistency to automatically/dynamically call constructors to create AST
     * objects from parser objects.
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

    final protected <T extends Item> T createItem(final Sequence seq, final int pos) {
        return createItem(apfe.runtime.Util.extractEle(seq, pos));
    }
    
    final protected <T extends Item> T createItem(final Acceptor seq, final int pos) {
        return createItem(apfe.runtime.Util.downcast(seq), pos);
    }

    final protected <T extends Item> T createItem(final int pos) {
        return createItem(asSequence(), pos);
    }

}
