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
import static gblib.Util.info;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import static java.util.Objects.isNull;
import laol.ast.Contents;
import laol.ast.Item;
import laol.generate.Util;
import static laol.generate.java.Util.getOutputDir;

/**
 * Organize sideband data during code generation.
 *
 * @author gburdell
 */
public class Context implements AutoCloseable {

    public Context(final Contents contents, final Config config) {
        m_contents = contents;
        m_config = config;
        reset();
    }

    public Config getConfig() {
        return m_config;
    }

    public Contents getContents() {
        return m_contents;
    }

    @Override
    public void close() throws Exception {
        reset();
    }

    public Context createOS(final String fname) throws FileNotFoundException, Util.EarlyTermination {
        assert (Objects.isNull(m_os));
        Path dir = getOutputDir(
                getConfig().getAsString("outputDir"),
                getPackageName());
        m_ofn = Paths.get(dir.toString(), fname);
        m_os = new PrintStream(m_ofn.toFile());
        info(2, "LG-FILE-3", m_ofn.toString());
        return this;
    }

    public Context header(final Item item) {
        LocalDateTime now = LocalDateTime.now();
        os().println("// Created: " + now);
        from(item);
        return this;
    }

    public Context from(final Item item) {
        os().println("// From " + item.getFileLineCol());
        return this;
    }

    public String getPackageName() {
        String pkg = getContents().getPackageName();
        if (isNull(pkg)) {
            pkg = getConfig().getAsString("packageName");
        }
        return pkg;
    }
    
    public Context packageAndImports() {
        os().println("package " + getPackageName() + ";");
        getContents().getRequires().forEach((istmt) -> {
            os().format("import %s%s ;\n", 
                    //todo? (istmt.isStatic()) ? "static " : "",
                    istmt.getImport().toString());
        });
        return this;
    }
    
    public PrintStream os() {
        return m_os;
    }

    private void reset() {
        if (Objects.nonNull(m_os)) {
            m_os.close();
        }
        m_ofn = null;
        m_os = null;
    }

    private Path m_ofn = null;
    private PrintStream m_os = null;
    private final Contents m_contents;
    private final Config m_config;

}
