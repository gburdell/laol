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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import laol.ast.AnonymousFunctionDefn;
import laol.ast.Item;
import laol.ast.Statement;

/**
 *
 * @author kpfalzer
 */
public interface IStatements {

    public List<Statement> getStatements();

    public default List<Item> getStatementsAsItems() {
        return getStatements().stream()
                .map(stmt -> stmt.getStmt())
                .collect(Collectors.toList());
    }

    /**
     * Get all statements, depth first.
     *
     * @param notInfoAnonFuncDefn do not collect statements in anonymous
     * function defn.  (Usu. set true if collecting statements looking for return statement).
     * @return collection of all statements: not necessarily in useful order.
     */
    public default List<Item> getAllStatements(final boolean notInfoAnonFuncDefn) {
        List<Item> allStmts = new LinkedList<>();
        getStatements().stream()
                .map((stmt) -> stmt.getStmt())
                .map((item) -> {
                    allStmts.add(item);
                    return item;
                })
                .filter((item) -> ((item instanceof IStatements)
                && (!notInfoAnonFuncDefn || !(item instanceof AnonymousFunctionDefn))))
                .map((item) -> gblib.Util.<IStatements>downCast(item))
                .forEachOrdered((IStatements stmts) -> {
                    allStmts.addAll(stmts.getAllStatements(notInfoAnonFuncDefn));
                });
        return Collections.unmodifiableList(allStmts);
    }

    public default List<Item> getAllStatements() {
        return getAllStatements(true);
    }
}
