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
import gblib.Util;
import static gblib.Util.downCast;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.unmodifiableList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
        if (Objects.nonNull(getExtends())) {
            m_baseNames = new LinkedList<>();
            getExtends().getAll().forEach(name -> m_baseNames.add(name.toString()));
            m_baseInits = getBody().getBaseInitializers(m_baseNames);
        } else {
            m_baseInits = null;
            m_baseNames = null;
        }
    }

    public final ClassBody getBody() {
        return m_body;
    }

    public List<String> getBaseNames() {
        return Util.getUnModifiableList(m_baseNames, self -> self);
    }

    public List<BaseClassInitializer> getBaseInitializers() {
        return Util.getUnModifiableList(m_baseInits, self -> self);
    }

    private ClassExtends getExtends() {
        return m_extends;
    }

    public Ident getIdent() {
        return m_name;
    }

    /**
     * Get names declared as members in body of default constructor.
     * Any variable declared in bofy of default constructor is a member (see notes.txt).
     * @return declared member names.
     */
    public List<String> getDeclaredMemberNames() {
        Set<String> members = new HashSet<>();
        getBody().getStatements().stream()
                .map(stmt -> stmt.getStmt())
                .filter(item -> item instanceof VarDeclStatement)
                .map(item -> gblib.Util.<VarDeclStatement>downCast(item))
                .forEach(decl -> members.addAll(decl.getLhsNames()));
        return members.stream().collect(Collectors.toList());
    }
    
    /**
     * Get parameters of primary constructor.
     * @return primary constructor parameters.
     */
    public List<MethodParamDeclEle> getParms() {
        return Util.getUnModifiableList(m_parms, p -> p.getDecl());
    }

    public class Constructor {

        public Constructor(ClassDeclaration decl) {
            m_parms = decl.getParms();
            m_baseInits = decl.getBaseInitializers();
            m_stmts = decl.getBody();
        }

        public Constructor(MethodDeclaration decl) {
            m_parms = MethodParamDecl.getParms(decl.getParmDecl());
            m_baseInits = decl.getBody().getBaseInitializers(getBaseNames());
            m_stmts = decl.getBody();
        }

        public List<MethodParamDeclEle> getParms() {
            return unmodifiableList(m_parms);
        }

        public List<BaseClassInitializer> getBaseInits() {
            return unmodifiableList(m_baseInits);
        }

        public IStatements getStatements() {
            return m_stmts;
        }
        
        private final List<MethodParamDeclEle> m_parms;
        private final List<BaseClassInitializer> m_baseInits;
        private final IStatements m_stmts;
    }

    public List<Constructor> getConstructors() {
        List<Constructor> cons = new LinkedList<>();
        cons.add(new Constructor(this));
        getBody().getStatements().forEach((Statement stmt) -> {
            boolean isDecl = stmt.getStmt() instanceof MethodDeclaration;
            if (isDecl) {
                final MethodDeclaration mdecl = MethodDeclaration.class.cast(stmt.getStmt());
                isDecl = mdecl.getName().toString().equals(getIdent().toString());
                if (isDecl) {
                    cons.add(new Constructor(mdecl));
                }
            }
        });
        return unmodifiableList(cons);
    }

    private final AccessModifier m_access;
    private final Ident m_name;
    private final MethodParamDecl m_parms;
    private final ClassExtends m_extends;
    private final ClassBody m_body;
    private final List<String> m_baseNames;
    private final List<BaseClassInitializer> m_baseInits;

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
