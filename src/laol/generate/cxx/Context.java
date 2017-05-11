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
package laol.generate.cxx;

import gblib.Config;
import static gblib.Util.info;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;
import laol.ast.Contents;
import laol.ast.Ident;
import laol.ast.Item;
import laol.ast.ScopedName;
import laol.generate.Util;
import static laol.generate.cxx.Util.getOutputDir;
import static laol.generate.cxx.Util.toPath;

/**
 * Organize sideband data during code generation.
 *
 * @author gburdell
 */
public class Context implements AutoCloseable {

    public Context(final String srcFname, final Contents contents, final Config config) throws FileNotFoundException, Util.EarlyTermination {
        m_srcFname = srcFname;
        m_contents = contents;
        m_config = config;
        String baseName = (new File(m_srcFname)).getName();
        m_baseName = baseName.substring(0, baseName.indexOf(".")).toLowerCase();
        createOS().initHxx().initCxx();
    }

    public Config getConfig() {
        return m_config;
    }

    public Contents getContents() {
        return m_contents;
    }

    @Override
    public void close() throws Exception {
        Arrays.asList(m_cxx, m_hxx).forEach((os) -> {
            nameSpace(os, false);
        });
        m_hxx.format("#endif //%s\n", getHxxDefine());
        Arrays.asList(m_cxx, m_hxx).forEach((os) -> {
            os.close();
        });
    }

    /**
     * Initialize header/hxx and source/cxx file(s).
     *
     * @return this Context.
     * @throws FileNotFoundException
     * @throws laol.generate.Util.EarlyTermination
     */
    private Context createOS() throws FileNotFoundException, Util.EarlyTermination {
        assert (Objects.isNull(m_hxx) && Objects.isNull(m_cxx));
        Path dir = getOutputDir(
                getConfig().getAsString("outputDir"),
                getPackageName());
        m_hxx = new NamedPrintStream(dir, m_baseName + ".hxx");
        m_cxx = new NamedPrintStream(dir, m_baseName + ".cxx");
        info(2, "LG-FILE-3", m_hxx.getPathName());
        info(2, "LG-FILE-3", m_cxx.getPathName());
        return this;
    }

    private Context initHxx() {
        {
            final String s = getHxxDefine();
            m_hxx.format("#ifndef %s\n#define %s\n", s, s);
        }
        getContents().getImports().forEach((ScopedName istmt) -> {
            m_hxx.format("#include \"%s.hxx\"\n", toPath(istmt));
        });
        //convert import to namespace by dropping last
        getContents().getImports().forEach((ScopedName istmt) -> {
            final int n = istmt.getNames().size();
            String nsu = String.join("/", 
                    istmt
                    .getNames()
                    .subList(0, n - 1)
                    .stream()
                    .map(e->{return e.toString();})
                    .collect(Collectors.toList()));
            if (!nsu.isEmpty()) {
                m_hxx.format("namespace using %s ;\n", nsu);
            }
        });
        nameSpace(m_hxx, true);
        return this;
    }

    private String packageNameRepl(final String repl) {
        return getPackageName().replaceAll("::", repl);
    }
    
    private Context initCxx() {
        String ifn = packageNameRepl("/");
        ifn += "/" + m_baseName + ".hxx";
        m_cxx.format("#include \"%s\"\n", ifn);
        nameSpace(m_cxx, true);
        return this;
    }
    
    private Context nameSpace(final PrintStream os, final boolean isOpen) {
        for (String ns : getPackageName().split("::")) {
            os.format(isOpen ? "namespace %s {\n" : "} //%s\n", ns);
        }
        return this;
    }

    private String getHxxDefine() {
        String dfn = "__" + packageNameRepl("_");
        dfn += "_" + m_baseName + "__";
        return dfn;
    }

    private static LocalDateTime now() {
        return LocalDateTime.now();
    }

    private Context header(final Item item, final Collection<PrintStream> oss) {
        oss.forEach((os) -> {
            os.println("// Created: " + now());
            from(os, item);
        });
        return this;
    }

    public Context from(final PrintStream os, final Item item) {
        os.println("// From " + item.getFileLineCol());
        return this;
    }

    public String getPackageName() {
        String pkg = getContents().getPackageName();
        if (isNull(pkg)) {
            pkg = getConfig().getAsString("packageName");
        }
        return pkg;
    }

    public PrintStream hxx() {
        return m_hxx;
    }

    public PrintStream cxx() {
        return m_cxx;
    }

    private static class NamedPrintStream extends PrintStream {

        public NamedPrintStream(final Path dir, final String fname) throws FileNotFoundException {
            super(new File(dir.toFile(), fname));
            m_dir = dir.toFile();
            m_fname = fname;
            super.format("// Created %s\n\n", now().toString());
        }

        public String getPathName() {
            return (new File(m_dir, m_fname)).getPath();
        }

        private final File m_dir;
        private final String m_fname;
    }

    private NamedPrintStream m_hxx, m_cxx;
    private final Contents m_contents;
    private final Config m_config;
    private final String m_srcFname, m_baseName;
}
