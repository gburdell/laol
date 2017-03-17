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

import gblib.Util.Triplet;
import static gblib.Util.downCast;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.nonNull;
import laol.ast.AccessModifier;
import laol.ast.AssignStatement;
import laol.ast.ClassBody;
import laol.ast.Item;
import laol.ast.LhsRef;
import laol.ast.MethodDeclaration;
import laol.ast.MethodParamDecl;
import laol.ast.MethodParamDeclEle;
import laol.ast.MethodParamDeclList;
import laol.ast.MethodParamDeclModifier;
import laol.ast.MutTypeDecl;
import laol.ast.ParamName;
import laol.ast.ScopedName;
import laol.ast.Statement;
import laol.ast.TypeDecl;
import laol.ast.VarDeclStatement;

/**
 * Useful utilities for (target agnostic) manipulation of ast.ClassDeclaration.
 *
 * @author kpfalzer
 */
public class ClassDeclaration {

    public static class Member extends Triplet<String, AccessModifier.EType, Boolean> {

        private Member(String where, String name, AccessModifier.EType access, Boolean isMutable) {
            super(name, access, isMutable);
            m_where = where;
        }

        public String getName() {
            return super.v1;
        }

        public AccessModifier.EType getAccess() {
            return super.v2;
        }

        public boolean isMutable() {
            return super.e3;
        }

        public String getWhere() {
            return m_where;
        }

        private final String m_where;
    }

    /**
     * Walk class declaration and innards to determine members.
     *
     * @param cdecl class declaration.
     * @return list of class members.
     */
    public static Collection<Member> getMembers(final laol.ast.ClassDeclaration cdecl) {
        Map<String, Member> membersByName = new LinkedHashMap<>();
        {
            //parameters
            MethodParamDecl mparams = cdecl.getParms();
            if (nonNull(mparams)) {
                MethodParamDeclList decls = mparams.getDecl();
                if (nonNull(decls)) {
                    List<MethodParamDeclEle> params = decls.getParms();
                    for (MethodParamDeclEle ele : params) {
                        ParamName name = ele.getParamName();
                        assert (!name.getName().hasSuffix());
                        MethodParamDeclModifier modifier = ele.getModifier();
                        addMember(membersByName,
                                name.getName().getId(),
                                name.getFileLineCol(),
                                modifier.getAccess().getType(),
                                modifier.getMutability().isVar());
                    }
                }
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
                                    name.asSimpleName(),
                                    name.getFileLineCol(),
                                    tdecl.getAccess().getType(),
                                    tdecl.getMutability().isVar());
                        }
                    } else if (item instanceof VarDeclStatement) {
                        VarDeclStatement decl = downCast(item);
                        MutTypeDecl tdecl = decl.getType();
                        List<ScopedName> names = decl.getNames();
                        for (ScopedName name : names) {
                            addMember(membersByName, 
                                    name.asSimpleName(), 
                                    name.getFileLineCol(), 
                                    tdecl.getAccess().getType(), 
                                    tdecl.getMutability().isVar());
                        }
                    } else if (item instanceof MethodDeclaration) {
                        //todo
                        /**
                         * TODO: create/add interface ISimpleName to ScopedName
                         * and ParamName; and IAccessMutability to MethodParamDeclModifier
                         * and TypeDecl and MutTypeDecl.
                         * Then addMember(..., ISimpleName, IAccessMutability)
                         */
                    }
                }
            }
        }
        return membersByName.values();
    }

    private static void addMember(
            Map<String, Member> membersByName,
            String name, String loc, AccessModifier.EType type, boolean isMutable) {
        if (!membersByName.containsKey(name)) {
            membersByName.put(name, new Member(loc, name, type, isMutable));
        }
    }
}
