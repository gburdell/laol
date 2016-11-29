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
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import laol.parser.apfe.KEXTENDS;

/**
 *
 * @author gburdell
 */
public class ClassExtends extends Item {
    public ClassExtends(final laol.parser.apfe.ClassExtends decl) {
        super(decl);
        Sequence cls = asPrioritizedChoice().getAccepted();
        Class clz = cls.getAccepted()[0].getClass();
        if (clz == KEXTENDS.class) {
            m_extends = createItem(cls, 1);
            Repetition imps = asRepetition(cls, 2);
            cls = (0 < imps.sizeofAccepted()) ? imps.getOnlyAccepted() : null;
        } else {
            m_extends = null;
        }
        m_implements = (null != cls) ? createItem(cls, 1) : null;
    }
    
    private final ClassName m_extends;
    private final ClassNameList   m_implements;
}