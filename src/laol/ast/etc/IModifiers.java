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
import java.lang.reflect.Modifier;
import laol.ast.AccessModifier;
import laol.ast.Mutability;

/**
 *
 * @author kpfalzer
 */
public interface IModifiers {

    public int getModifiers();

    /**
     * Process (flat/linear) pair of IModifiers,int... where int value is
     * default if IModifers is null.
     *
     * @param modifiers pairs of IModifiers,int.
     * @return or-d value of modifiers.
     */
    public default int getModifiers(Object... modifiers) {
        invariant(0 == (modifiers.length % 2)); //even
        int allMods = 0;
        for (int i = 0; i < modifiers.length; i += 2) {
            if (null != modifiers[i]) {
                allMods |= IModifiers.class.cast(modifiers[i]).getModifiers();
            } else {
                allMods |= Integer.class.cast(modifiers[i + 1]);
            }
        }
        return allMods;
    }

    public default int getModifers(AccessModifier access, Mutability mutability, boolean isStatic) {
        return getModifiers(
                access, Modifier.PRIVATE,
                mutability, Modifier.FINAL
        )
                | (isStatic ? Modifier.STATIC : 0);

    }

    public default boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    public default boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    public default boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    public default boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public default boolean isConst() {
        return Modifier.isFinal(getModifiers());
    }
}
