/*
 * The MIT License
 *
 * Copyright 2017 gburdell.
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
package laol.generate.java;

import gblib.Config;
import gblib.Tree;
import laol.ast.Contents;
import laol.generate.Scope;

/**
 * Organize sideband data during code generation.
 *
 * @author gburdell
 */
public class Context {

    public Context(final Contents contents, final Config config) {
        m_contents = contents;
        m_config = config;
    }
    
    public Config getConfig() {
        return m_config;
    }
    
    public Tree<Scope> getScopes() {
        return m_scopes;
    }
    
    private final Contents m_contents;
    private final Config m_config;
    /**
     * Tree of Scope initialized with global scope.
     */
    private final Tree<Scope> m_scopes = new Tree<>(new Scope());
}
