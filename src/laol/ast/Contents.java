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

import apfe.runtime.Sequence;
import gblib.JarFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import laol.ast.etc.ISymbol;
import laol.ast.etc.ISymbolCreator;
import laol.ast.etc.ImportedClass;
import laol.ast.etc.ImportedField;
import laol.ast.etc.SymbolTable;
import static laol.generate.Util.resolve;

/**
 * Contents is the topmost compilation unit (a source file). It/Contents is
 * bound to a package.
 *
 * @author gburdell
 */
public class Contents extends Item {

    public Contents(final laol.parser.apfe.Contents decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_package = oneOrNone(seq, 0);
        m_requires = zeroOrMore(seq, 1);
        m_fileItems = zeroOrMore(seq, 2);
    }

    public List<FileItem> getFileItems() {
        return Collections.unmodifiableList(m_fileItems);
    }

    public List<ScopedName> getRequires() {
        List<ScopedName> stmts = new LinkedList<>();
        m_requires.forEach((ImportStatements imports)->{stmts.addAll(imports.getImports());});
        return Collections.unmodifiableList(stmts);
    }

    public PackageStatement getPackage() {
        return m_package;
    }

    public boolean hasPackage() {
        return isNonNull(m_package);
    }

    public boolean hasSamePackage(Contents cmp) {
        boolean isSame = (!hasPackage() && !cmp.hasPackage());
        isSame |= getPackageName().equals(cmp.getPackageName());
        return isSame;
    }

    public String getPackageName() {
        return hasPackage() ? getPackage().getPackageName().toString() : null;
    }

    /**
     * After AST is created, pull in any required packages, etc.
     *
     * @param jarFiles .jar file(s) to process to find requires/imports.
     * @param stabByPackage SymbolTable by package name.
     * @return true on success; false on error.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    /*
    public boolean processImports(
            Collection<String> jarFiles,
            Map<String, SymbolTable> stabByPackage
    ) throws IOException, ClassNotFoundException {
        boolean ok = true;
        for (ImportStatement imprt : getRequires()) {
            final String where = imprt.getImport().getFileLineCol();
            final String req = imprt.getImport().toString();
            SymbolTable fromPkg = resolve(stabByPackage, req);
            if (isNonNull(fromPkg)) {
                //insert from package iff. symbol does NOT exist.
                //thus: package defined symbols overshadow imports
                ok &= m_stab.insert(fromPkg, s -> !m_stab.containsKey(s.getName()));
            } else {
                Map<String, ISymbol> imported;
                if (imprt.isStatic()) {
                    imported = JarFile.getStaticNamesInPackage(req)
                            .entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    kv -> kv.getValue().getName(),
                                    kv -> new ImportedField(
                                            where,
                                            kv.getKey(),
                                            kv.getValue())));
                } else {
                    imported = JarFile.getImports(Arrays.asList(req), jarFiles)
                            .entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    kv -> kv.getValue().getSimpleName(),
                                    kv -> new ImportedClass(
                                            where,
                                            kv.getKey(),
                                            kv.getValue())));
                }
                ok &= m_stab.insert(imported);
            }
            if (!ok) {
                break;//for
            }
        }
        return ok;
    }
    */

    /**
     * Create SymbolTable for elements declared in Contents.
     *
     * @return true on success; false on error.
     */
    public boolean createSymbolTable() {
        //map to Statements which create symbols
        boolean ok = getFileItems()
                .stream()
                .map(fileItem -> fileItem.getStatement().getStmt())
                .filter(item -> item instanceof ISymbolCreator)
                .map(ISymbolCreator.class::cast)
                //insert into symbol table
                .map(symCreator -> {
                    return symCreator.insert(m_stab);
                })
                .allMatch(e -> e);
        return ok;
    }

    public SymbolTable getSymbolTable() {
        return m_stab;
    }

    private final PackageStatement m_package;
    private final List<ImportStatements> m_requires;
    private final List<FileItem> m_fileItems;

    /**
     * Symbol table for compilation unit (contents: aka. source file) level.
     * This will contain symbols for:
     * <ul>
     * <li>imported class/enums
     * <li>imported interface
     * <li>imported static fields: variable/constants and methods
     * <li>class/interface defined herein
     * <li>toplevel (not in class): methods and variables/constants
     * </ul>
     */
    private final SymbolTable m_stab = new SymbolTable();
}
