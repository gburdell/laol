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

import static gblib.Util.invariant;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author kpfalzer
 */
public interface ISymbol extends IName, IModifiers {

    //todo: use EnumSet for storage...
    public static enum EType {
        eClass, eInterface, 
        eMethod, //not associated with class/interface
        eVar,    //ditto...
        eMemberVar, 
        eAnonFunc, 
        eMemberMethod,
        eConstructor,
        eInheritedMethod,
        eInheritedVar,
        eInheritedConstructor
    }

    public EType getType();

    /**
     * Check if symbol is (at least) one of the specified types.
     * @param types types to check.
     * @return true if symbol at least one of these types.
     */
    public default boolean isA(EType... types) {
        for (EType type : types) {
            if (getType() == type) {
                return true;
            }
        }
        return false;
    }
    
    public default boolean hasScope() {
        return (EType.eVar != getType()) && (EType.eMemberVar != getType());
    }

    /**
     * Create SymbolTable within contents (i.e., for hasScope()==true types).
     *
     * @param parent
     */
    public default void createContentSymbols(SymbolTable parent) {
        if (hasScope()) {
            createContentSymbolsImpl(parent);
        }
    }

    /**
     * Build next level of hierarchical symbol table. Symbol elements with scope
     * will implement this method.
     *
     * @param parent parent SymbolTable.
     */
    public default void createContentSymbolsImpl(SymbolTable parent) {
        invariant(false);
    }

    public default boolean isImported() {
        return false;
    }

    /**
     * Get modifiers encoded as per java.lang.reflect.Modifier
     *
     * @return encoded modifiers.
     */
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
    
    public static final List<ISymbol> EMPTY_LIST = new LinkedList<>();
}
