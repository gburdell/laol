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

import static gblib.Util.emptyUnmodifiableList;
import static gblib.Util.invariant;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author kpfalzer
 */
public interface ISymbol extends IName, IModifiers {

    public static enum EType {
        //primitive types
        eClass,
        eInterface,
        eMethod,
        eVar,
        eConstructor,
        eAnonFunc,
        //modifiers
        eIsMember,
        eIsInherited
    }

    /**
     * Return enumerated set of one type.
     *
     * @param t0 one type.
     * @return enumerated set.
     */
    public static EnumSet<EType> createType(EType t0) {
        return EnumSet.of(t0);
    }

    /**
     * Return enumerated set of one or more type.
     *
     * @param t0 at least one type.
     * @param types more types.
     * @return enumerated set.
     */
    public static EnumSet<EType> createType(EType t0, EType... types) {
        return EnumSet.of(t0, types);
    }

    public static final EnumSet<EType> CLASS_TYPE = ISymbol.createType(EType.eClass);
    public static final EnumSet<EType> INTERFACE_TYPE = ISymbol.createType(EType.eInterface);
    public static final EnumSet<EType> CONSTRUCTOR_TYPE = ISymbol.createType(EType.eConstructor);
    public static final EnumSet<EType> MEMBER_METHOD_TYPE = ISymbol.createType(EType.eIsMember, EType.eMethod);
    public static final EnumSet<EType> MEMBER_VAR_TYPE = ISymbol.createType(EType.eIsMember, EType.eVar);
    public static final EnumSet<EType> VAR_TYPE = ISymbol.createType(EType.eVar);
    public static final EnumSet<EType> ANONFUNC_TYPE = ISymbol.createType(EType.eAnonFunc);

    public EnumSet<EType> getType();

    public default boolean isConstructor() {
        return getType().equals(CONSTRUCTOR_TYPE);
    }

    public default boolean isClass() {
        return getType().equals(CLASS_TYPE);
    }

    public default boolean isInterface() {
        return getType().equals(INTERFACE_TYPE);
    }

    public default boolean isMemberMethod() {
        return getType().equals(MEMBER_METHOD_TYPE);
    }

    public default boolean isMemberVar() {
        return getType().equals(MEMBER_VAR_TYPE);
    }

    public default boolean isInherited() {
        return isAnyOf(EType.eIsInherited);
    }

    /**
     * Check if symbol is (at least) one of the specified types.
     *
     * @param types types to check.
     * @return true if symbol at least one of these types.
     */
    public default boolean isAnyOf(EType... types) {
        for (EType type : types) {
            if (getType().contains(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if symbol is (at least) one of the specified types.
     *
     * @param types types to check.
     * @return true if symbol at least one of these types.
     */
    public default boolean isAnyOf(EnumSet<EType>... types) {
        for (EnumSet<EType> type : types) {
            if (getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public default boolean hasScope() {
        return !isAnyOf(EType.eVar) && !isImported();
    }

    /**
     * Create SymbolTable within contents (i.e., for hasScope()==true types).
     *
     * @param parent
     * @return true on success (or if no scope); false on symbol error.
     */
    public default boolean createContentSymbols(SymbolTable parent) {
        boolean ok = true;
        if (hasScope()) {
            ok = createContentSymbolsImpl(parent);
        }
        return ok;
    }

    /**
     * Build next level of hierarchical symbol table. Symbol elements with scope
     * will implement this method.
     *
     * @param parent parent SymbolTable.
     * @return true on success; else false on symbol error.
     */
    public default boolean createContentSymbolsImpl(SymbolTable parent) {
        return true;
    }

    public default boolean isImported() {
        return false;
    }

    /**
     * Get modifiers encoded as per java.lang.reflect.Modifier
     *
     * @return encoded modifiers.
     */
    @Override
    public default int getModifiers() {
        return 0;
    }

    /**
     * Get SymbolTable for this scope.
     *
     * @return SymbolTable or null.
     */
    public default SymbolTable getSymbolTable() {
        return null;
    }

    public static final List<ISymbol> EMPTY_LIST = emptyUnmodifiableList();
}
