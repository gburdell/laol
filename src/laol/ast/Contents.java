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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        m_imports = zeroOrMore(seq, 1);
        m_fileItems = zeroOrMore(seq, 2);
    }

    public List<FileItem> getFileItems() {
        return Collections.unmodifiableList(m_fileItems);
    }

    public List<ImportName> getImports() {
        List<ImportName> stmts = new LinkedList<>();
        m_imports.forEach((ImportStatements imports)->{stmts.addAll(imports.getImports());});
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

    private final PackageStatement m_package;
    private final List<ImportStatements> m_imports;
    private final List<FileItem> m_fileItems;

}
