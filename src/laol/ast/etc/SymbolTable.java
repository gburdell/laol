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
package laol.ast.etc;

import gblib.MessageMgr;
import gblib.Pair;
import static gblib.Util.error;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author kpfalzer
 */
public class SymbolTable extends HashMap<String, List<ISymbol>> {
    
    /**
     * Insert a map of element by name into symbol table.
     *
     * @param <T> type which we apply createSymbol to create ISymbol.
     * @param symbols map of elements by name.
     * @param onDup optional receiver to handle duplicates. If invoked, pass
     * Pair of current and new ISymbol with same name/key. If onDup is null,
     * then no receiver called on duplicate.
     * @return true if no duplicates detected during insert; else false.
     */
    public <T> boolean insert(
            Map<String, ISymbol> symbols,
            Consumer<Pair<ISymbol, ISymbol>> onDup) {
        AtomicBoolean ok = new AtomicBoolean(true);
        symbols.forEach((k, v) -> {
            if (!insert(k, v)) {
                ok.set(false);
                if (Objects.nonNull(onDup)) {
                    onDup.accept(new Pair<>(get(k).get(0), v));
                }
            }
        });
        return ok.get();
    }

    public <T> boolean insert(Map<String, ISymbol> symbols) {
        return insert(symbols, DUP_MSG);
    }

    /**
     * Insert symbol.
     *
     * @param key symbol key.
     * @param value symbol value.
     * @return true if insertion done. Duplicate symbols are allowed for class
     * member methods and constructors.
     */
    public boolean insert(String key, ISymbol value) {
        boolean ok = false;
        if (super.containsKey(key)) {
            if (allowedDup(value)) {
                List<ISymbol> existing = super.get(key);
                ok = existing.stream().allMatch(val -> allowedDup(val));
                if (ok) {
                    existing.add(value);
                }
            }
        } else {
            ok = (null == super.put(key, new LinkedList<>(Arrays.asList(value))));
        }
        return ok;
    }

    private static boolean allowedDup(ISymbol value) {
        return value.getType().equals(ISymbol.CONSTRUCTOR_TYPE)
                || value.getType().equals(ISymbol.MEMBER_METHOD_TYPE);
    }

    public boolean insert(ISymbol sym) {
        return insert(sym.getName(), sym);
    }

    /**
     * Insert another SymbolTable into this one.
     *
     * @param stab SymbolTable to add.
     * @param ifTrue insert symbol ifTrue(sym[i].value)
     * @param onDup optional receiver to handle duplicates. If invoked, pass
     * Pair of current and new ISymbol with same name/key. If onDup is null,
     * then no receiver called on duplicate.
     * @return true if no duplicates detected during insert; else false.
     */
    public boolean insert(SymbolTable stab, Predicate<ISymbol> ifTrue, Consumer<Pair<ISymbol, ISymbol>> onDup) {
        // We use Atomic to be able to update w/in lambda.
        AtomicBoolean ok = new AtomicBoolean(true);
        stab.forEach((k, vals) -> {
            vals.forEach((ISymbol v) -> {
                if (ok.get() && ifTrue.test(v) && !insert(k, v)) {
                    ok.set(false);
                    if (Objects.nonNull(onDup)) {
                        onDup.accept(new Pair<>(get(k).get(0), v));
                    }
                }
            });
        });
        return ok.get();
    }

    public boolean insert(SymbolTable stab, Predicate<ISymbol> ifTrue) {
        return insert(stab, ifTrue, DUP_MSG);
    }

    private static final Consumer DUP_MSG = new Consumer<Pair<ISymbol, ISymbol>>() {

        @Override
        public void accept(Pair<ISymbol, ISymbol> dup) {
            error("LG-SYM", dup.v2.getFileLineCol(), dup.v1.getName(), dup.v1.getFileLineCol());
        }

    };

    static {
        MessageMgr.addMessage(
                "LG-SYM",
                "%s: symbol '%s' already defined at %s");
    }
}
