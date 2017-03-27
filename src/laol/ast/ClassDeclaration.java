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
import static gblib.Util.invariant;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import laol.ast.etc.HierSymbolTable;
import laol.ast.etc.IModifiers;
import laol.ast.etc.ISymbol;
import laol.ast.etc.ISymbolCreator;
import laol.ast.etc.SymbolTable;

/**
 *
 * @author gburdell
 */
public class ClassDeclaration extends Item implements IModifiers, ISymbol, ISymbolCreator {

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

    @Override
    public Ident getIdent() {
        return m_name;
    }

    public MethodParamDecl getParms() {
        return m_parms;
    }

    public boolean hasParams() {
        return isNonNull(getParms());
    }

    private final AccessModifier m_access;
    private final Ident m_name;
    private final MethodParamDecl m_parms;
    private final ClassExtends m_extends;
    private final ClassBody m_body;

    @Override
    public EType getType() {
        return EType.eClass;
    }

    /**
     * Get access privilege for ClassDeclaration. Default is private.
     *
     * @return modifiers encoded as per java.lang.reflect.Modifier.
     */
    @Override
    public int getModifiers() {
        return getModifiers(m_access, Modifier.PRIVATE);
    }

    @Override
    public void createContentSymbolsImpl(SymbolTable parent) {
        m_stab = new HierSymbolTable(parent);
        // Add constructor params as members
        Util.getNonNullValue(
                getParms(),
                mpd -> mpd.getDecl(),
                mpdl -> mpdl.getParms(),
                () -> MethodParamDeclEle.EMPTY_LIST
        )
                .stream()
                //take named and anonFuncDecl; so NO filter: .filter(MethodParamDeclEle::isNamed)
                .forEach((MethodParamDeclEle ele) -> {
                    ele.insert(m_stab);
                });
        //inherited class and its members
        if (isNonNull(m_extends)) {
            if (m_extends.hasExtends()) {
                final String superClsName = m_extends.getExtends().getId();
                List<ISymbol> superClsDecls = Util.getNonNullValue(
                        m_stab.getParent().get(superClsName),
                        ISymbol.EMPTY_LIST
                )
                        .stream()
                        .filter(sym -> sym.getType() == EType.eClass)
                        .collect(Collectors.toList());
                invariant(1 == superClsDecls.size());
                //we dont add the inherited/super cls decl: but we'll
                //add its public/protected constructor/methods/vars (and label inheritedXXX)
                //and public inheritedMethods/vars
                SymbolTable superClsSymbols = superClsDecls.get(0).getSymbolTable();
                superClsSymbols.values()
                        .stream()
                        .flatMap(symList -> {
                            return symList
                                    .stream()
                                    .filter(sym->canInherit(sym));
                        })
                        .forEach((ISymbol inherited) -> {
                            
                        });
            }
        }
        //todo: var decls...
        //todo: method defs...
        //todo: inner class decl
    }

    private static boolean canInherit(ISymbol sym) {
        switch (sym.getType()) {
            case eConstructor: case eMemberMethod: case eMemberVar:
                break;
            default:
                return false;
        }
        return sym.isPublic() || sym.isProtected();
    }
    
    @Override
    public SymbolTable getSymbolTable() {
        return m_stab;
    }

    private HierSymbolTable m_stab;
}
