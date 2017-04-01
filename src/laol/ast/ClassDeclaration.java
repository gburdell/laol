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
import java.util.EnumSet;
import java.util.List;
import gblib.AtomicBoolean;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import laol.ast.etc.HierSymbolTable;
import laol.ast.etc.IModifiers;
import laol.ast.etc.ISymbol;
import laol.ast.etc.ISymbolCreator;
import laol.ast.etc.Inherited;
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
    public EnumSet<EType> getType() {
        return ISymbol.CLASS_TYPE;
    }

    /**
     * Get access privilege for ClassDeclaration. Default is public.
     *
     * @return modifiers encoded as per java.lang.reflect.Modifier.
     */
    @Override
    public int getModifiers() {
        return getModifiers(m_access, Modifier.PUBLIC);
    }

    @Override
    public boolean createContentSymbolsImpl(SymbolTable parent) {
        AtomicBoolean ok = new AtomicBoolean(true);
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
                    ok.and(ele.insert(m_stab));
                });
        //inherited class and its members
        if (isNonNull(m_extends)) {
            List<ISymbol> superDecls = new LinkedList<>();
            //only has implements/interfaces
            m_extends
                    .getImplements()
                    .getNames()
                    .stream()
                    .map(
                            (intrfcName) -> m_stab
                                    .getParent()
                                    .computeIfAbsent(intrfcName.getId(), x->ISymbol.EMPTY_LIST)
                                    .stream()
                                    .filter(sym -> sym.isInterface())
                                    .collect(Collectors.toList())
                    )
                    .forEachOrdered((intrfcDecls) -> {
                        superDecls.addAll(intrfcDecls);
                    });
            ok.and(pushDownInherited(superDecls));
        }
        //todo: var decls...
        //todo: method defs...
        //todo: inner class decl
        return ok.get();
    }

    private boolean pushDownInherited(Collection<ISymbol> inherited) {
        return pushDownInherited(inherited, m_stab);
    }

    /**
     * Push down inherited Interface members into HierSymbolTable.
     * TODO: this is not a recursive operation: we only do one level here!
     *
     * @param inherited inherited Interface.
     * @param stab hierarchical symbol table.
     * @return true on success; else false if issue(s).
     */
    /*package: share with InterfaceDecl*/
    static boolean pushDownInherited(Collection<ISymbol> inherited, HierSymbolTable stab) {
        AtomicBoolean ok = new AtomicBoolean(true);
        for (ISymbol superCls : inherited) {
            SymbolTable superClsSymbols = superCls.getSymbolTable();
            if (Objects.nonNull(superClsSymbols)) {
                //add its public/protected constructor/methods/vars (and label inheritedXXX)
                superClsSymbols.values()
                        .stream()
                        .flatMap(symList -> {
                            return symList
                                    .stream()
                                    .filter(sym -> canInherit(sym, superCls.isInterface()));
                        })
                        .forEach((ISymbol inheritedSym) -> {
                            ok.and(stab.insert(new Inherited(inheritedSym)));
                        });
                //and add public inheritedMethods/vars
                superClsSymbols.values()
                        .stream()
                        .flatMap(symList -> {
                            return symList
                                    .stream()
                                    .filter(sym -> sym.isPublic() && sym.isInherited());
                        })
                        .forEach((ISymbol publicInherited) -> {
                            ok.and(stab.insert(publicInherited));
                        });

            }
            if (!ok.get()) {
                break; //for: if we have any issues
            }
        }
        return ok.get();
    }

    /**
     * Check if can inherit symbol.
     *
     * @param sym symbol to check for inherit-ability.
     * @return true if we can inherit symbol.
     */
    private static boolean canInherit(ISymbol sym, boolean isInterface) {
        invariant(!isInterface || !sym.isConstructor());  //interface does not have constructor
        if (!sym.isAnyOf(CONSTRUCTOR_TYPE, MEMBER_METHOD_TYPE, MEMBER_VAR_TYPE)) {
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
