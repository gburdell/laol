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

import static gblib.Util.getList;
import static gblib.Util.getUnModifiableList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class MethodParamDecl extends Item {

    public MethodParamDecl(final laol.parser.apfe.MethodParamDecl decl) {
        super(decl);
        m_decl = oneOrNone(1);
    }

    public List<MethodParamDeclEle> getDecl() {
        return getUnModifiableList(m_decl, f -> f.getParms());
    }

    public static List<MethodParamDeclEle> getParms(final MethodParamDecl decl) {
        return getList(decl, MethodParamDecl::getDecl);
    }

    private final MethodParamDeclList m_decl;

}
