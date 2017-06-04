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
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import laol.ast.etc.IModifiers;

/**
 *
 * @author gburdell
 */
public class ClassDeclaration extends Item implements IModifiers {

    public ClassDeclaration(final laol.parser.apfe.ClassDeclaration decl) {
        super(decl);
        final Sequence seq = asSequence();
        m_access = oneOrNone(seq, 0);
        m_name = getIdent(seq, 2);
        m_parms = oneOrNone(seq, 3);
        m_extends = oneOrNone(seq, 4);
        m_body = createItem(seq, 6);
    }

    public ClassBody getBody() {
        return m_body;
    }

    public ClassExtends getExtends() {
        return m_extends;
    }

    public Ident getIdent() {
        return m_name;
    }

    public List<MethodParamDeclEle> getParms() {
        return isNonNull(m_parms) ? m_parms.getDecl() : MethodParamDecl.EMPTY_LIST;
    }

    /**
     * Return all constructor declarations.
     * We guarantee to return at least one element: the default declared with this class.
     * @return constructor declarations.
     */
    public List<List<MethodParamDeclEle>> getConstructorParmDecls() {
        final List<List<MethodParamDeclEle>> decls = new LinkedList<>();
        decls.add(getParms());
        getBody().getStatements().forEach((Statement stmt) -> {
            boolean isDecl = stmt.getStmt() instanceof MethodDeclaration;
            if (isDecl) {
                final MethodDeclaration mdecl = MethodDeclaration.class.cast(stmt.getStmt());
                isDecl = mdecl.getName().toString().equals(getIdent().toString());
                if (isDecl) {
                    decls.add(MethodParamDecl.getParms(mdecl.getParmDecl()));
                }
            }
        });
        return decls;
    }
    
    private final AccessModifier m_access;
    private final Ident m_name;
    private final MethodParamDecl m_parms;
    private final ClassExtends m_extends;
    private final ClassBody m_body;

    /**
     * Get access privilege for ClassDeclaration. Default is public.
     *
     * @return modifiers encoded as per java.lang.reflect.Modifier.
     */
    @Override
    public int getModifiers() {
        return getModifiers(m_access, Modifier.PUBLIC);
    }

}
