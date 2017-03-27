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
package laol.generate;

import gblib.JarFile;
import static gblib.Util.downCast;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.AccessModifier;
import laol.ast.AssignStatement;
import laol.ast.ClassBody;
import laol.ast.Item;
import laol.ast.LhsRef;
import laol.ast.MethodDeclaration;
import laol.ast.MethodParamDeclEle;
import laol.ast.MethodParamDeclList;
import laol.ast.MethodParamDeclModifier;
import laol.ast.MutTypeDecl;
import laol.ast.Mutability;
import laol.ast.ParamName;
import laol.ast.ScopedName;
import laol.ast.Statement;
import laol.ast.TypeDecl;
import laol.ast.VarDeclStatement;
import laol.ast.etc.IModifiers;

/**
 * Useful utilities for (target agnostic) manipulation of ast.ClassDeclaration.
 *
 * @author kpfalzer
 */
public class ClassDeclaration {

    public static class Member {

        private Member(String where, String name, AccessModifier.EType access, boolean isMutable) {
            m_where = where;
            m_name = name;
            m_access = access;
            m_isMutable = isMutable;
        }

        public String getName() {
            return m_name;
        }

        public AccessModifier.EType getAccess() {
            return m_access;
        }

        public boolean isMutable() {
            return m_isMutable;
        }

        public String getWhere() {
            return m_where;
        }

        private final String m_where, m_name;
        private final AccessModifier.EType m_access;
        private final boolean m_isMutable;
    }

    /**
     * Get Field for static names found in package. Static elements (of class)
     * are:
     * <ul>
     * <li>method</li>
     * <li>member</li>
     * </ul>
     *
     * <B>Note:</B> we do not get enum values here since they have no equivalent
     * in laol.
     *
     * @param hierName name (syntax) as would appear in "import static ..."
     * statement.
     * @return map of Field by fully qualified name.
     * @throws ClassNotFoundException
     */
    protected static Map<String, Field> getStaticNamesInPackage(String hierName) throws ClassNotFoundException {
        return JarFile.getStaticNamesInPackage(hierName)
                .entrySet()
                .stream()
                .filter(map -> !map.getValue().isEnumConstant())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    /**
     * Walk class declaration and innards to determine members.
     *
     * @param cdecl class declaration.
     * @return list of class members.
     */
    protected static Collection<Member> getMembers(final laol.ast.ClassDeclaration cdecl) {
        Map<String, Member> membersByName = new LinkedHashMap<>();
        {
            //parameters
            if (cdecl.hasParams()) {
                addMembers(membersByName, cdecl.getParms().getDecl());
            }
        }
        {
            //class body
            ClassBody body = cdecl.getBody();
            if (nonNull(body)) {
                for (Statement stmt : body.getStatements()) {
                    Item item = stmt.getStmt();
                    if (item instanceof AssignStatement) {
                        AssignStatement astmt = downCast(item);
                        TypeDecl tdecl = astmt.getLhs().getDecl();
                        List<LhsRef> refs = astmt.getLhs().getRefs();
                        for (LhsRef lhs : refs) {
                            ScopedName name = downCast(lhs.getItems().get(0));
                            addMember(membersByName,
                                    name,
                                    tdecl);
                        }
                    } else if (item instanceof VarDeclStatement) {
                        VarDeclStatement decl = downCast(item);
                        MutTypeDecl tdecl = decl.getType();
                        List<ScopedName> names = decl.getNames();
                        for (ScopedName name : names) {
                            addMember(membersByName,
                                    name,
                                    tdecl);
                        }
                    } else if (item instanceof MethodDeclaration) {
                        MethodDeclaration decl = downCast(item);
                        if (decl.hasParmDecl()) {
                            addMembers(membersByName, decl.getParmDecl().getDecl(), true);
                        }
                    }
                }
            }
        }
        return membersByName.values();
    }

    protected static void addMembers(Map<String, Member> membersByName, MethodParamDeclList decls) {
        addMembers(membersByName, decls, false);
    }

    protected static void addMembers(Map<String, Member> membersByName, MethodParamDeclList decls,
            boolean iffMember) {
        if (nonNull(decls)) {
            List<MethodParamDeclEle> params = decls.getParms();
            for (MethodParamDeclEle ele : params) {
                ParamName name = ele.getParamName();
                if (!iffMember || name.isMember()) {
                    assert (!name.getName().hasSuffix());
                    MethodParamDeclModifier modifier = ele.getModifier();
                    addMember(membersByName,
                            name,
                            modifier);
                }
            }
        }
    }

    private static void addMember(
            Map<String, Member> membersByName,
            ISimpleName name, IModifiers accMut) {
        final String sname = name.asSimpleName();
        if (!membersByName.containsKey(sname)) {
            membersByName.put(
                    sname,
                    new Member(
                            name.getFileLineCol(),
                            sname,
                            getNonNullValue(
                                    accMut.getAccess(),
                                    AccessModifier::getType,
                                    () -> AccessModifier.EType.ePublic),
                            getNonNullValue(
                                    accMut.getMutability(),
                                    Mutability::isVar,
                                    () -> false)
                    )
            );
        }
    }
}
