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

import gblib.Config;
import gblib.MessageMgr;
import gblib.Options;
import static gblib.Util.error;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import laol.ast.Contents;
import laol.ast.etc.ISymbol;
import laol.ast.etc.SymbolTable;

/**
 *
 * @author kpfalzer
 */
public class Main {

    private Main(String argv[]) {
        m_argv = argv;
    }

    private final String m_argv[];
    private Parse m_parsed;
    private Map<String, SymbolTable> m_stabByPackage = new HashMap<>();

    public static void main(final String argv[], boolean exitOn0) {
        try {
            Main me = new Main(argv);
            final int status = me.process();
            if ((0 != status) || exitOn0) {
                System.exit(status);
            }
        } catch (IOException | ClassNotFoundException ex) {
            gblib.Util.abnormalExit(ex);
        }
    }

    public static void main(final String argv[]) {
        main(argv, false);
    }

    private int process() throws IOException, ClassNotFoundException {
        int status = 1;
        if (1 > m_argv.length) {
            usage();
        } else {
            Collection<String> srcFiles = CMD_OPTIONS.process(m_argv);
            MessageMgr.setMessageLevel(CONFIG.getAsInteger("verbosity"));
            if (srcFiles.isEmpty()) {
                error("LG-NOSRC");
            } else if (checkOptions()) {
                m_parsed = new Parse(srcFiles);
                if (m_parsed.hasErrors()) {
                    error("LG-EXIT", m_parsed.getErrorCnt());
                    status = 2;
                } else {
                    status = setupStabs(CONFIG.getAsString("jars"));
                    if (0 == status) {
                        status = laol.generate.java.Generate
                                .create(m_parsed.getAsts(), CONFIG)
                                .generate();

                    }
                }
            }
        }
        return status;
    }

    /**
     * Setup SymbolTables:
     * <ol>
     * <li>foreach ast/compilation-unit (aka. CU[i]): import into CU[i])
     * <li>foreach CU[i]: add toplevel symbols to CU[i]
     * <li>foreach CU[i]: populate stabByPackage.
     * </ol>
     *
     * @param asts CUs to process.
     * @param jars colon-separated .jar file(s)
     * @return 0 on success; !=0 if error(s).
     */
    private int setupStabs(String jars) {
        Collection<Parse.Ast> asts = m_parsed.getAsts();
        Collection<String> jarFiles = Arrays.asList(jars.split(":"));
        //get contents to process
        List<Contents> allCUs = asts
                .stream()
                .map((Parse.Ast to) -> {
                    return to.getGrammar().getContents();
                })
                .collect(Collectors.toList());
        //add toplevel symbols to CU[i] 
        boolean ok = allCUs
                .stream()
                .map(contents -> {
                    return contents.createSymbolTable();
                })
                .allMatch(e -> e);
        //update SymbolTables by package.
        ok &= allCUs
                .stream()
                .map((Contents cu) -> {
                    SymbolTable pkgStab = m_stabByPackage.computeIfAbsent(cu.getPackageName(), x -> new SymbolTable());
                    return pkgStab.insert(cu.getSymbolTable(), (ISymbol sym) -> !sym.isImported());
                }).allMatch(e -> e);
        /*
         *  At this point, we have processed all CU.  
         *  Each has a symbol table.
         *  We also have a map of SymbolTable by package.
         *  Next, pull imports into each CU.
         */
        ok &= allCUs
                .stream()
                .map((Contents contents) -> {
                    try {
                        return contents.processImports(jarFiles, m_stabByPackage);
                    } catch (IOException | ClassNotFoundException ex) {
                        Util.handleException(ex);
                    }
                    return false;
                })
                .allMatch(e -> e);
        return ok ? 0 : 1;
    }

    /**
     * Check command line options.
     *
     * @return true if ok, else false on error(s).
     */
    private static boolean checkOptions() {
        try {
            Util.createDirectory(CONFIG.getAsString("outputDir"));
            return true;
        } catch (Util.EarlyTermination ex) {
            return false;
        }
    }

    private static void usage() {
        System.err.printf("Usage: %s option* file.laol+\n%s",
                PROGNM, CMD_OPTIONS.getUsage());
    }

    public static final String PROGNM = "laol2j";

    private static final Config CONFIG = Config.create()
            .add(new String[]{
        "packageName laol.java.user",
        "outputDir " + PROGNM + "/generate/src",
        "verbosity *I1",
        "class Main",
        "jars missing.jar"
    });

    private static final Options CMD_OPTIONS = Options.create();

    static {
        {
            final String keyName = "packageName";
            final String dflt = CONFIG.get(keyName).toString();
            CMD_OPTIONS
                    .add(
                            "-p|--package name",
                            "Default Java package name " + defaultOpt(dflt),
                            (opt) -> {
                                CONFIG.put(keyName, opt);
                            }
                    );
        }
        {
            final String keyName = "outputDir";
            final String dflt = CONFIG.get(keyName).toString();
            CMD_OPTIONS
                    .add(
                            "-o|--outputDir dirName",
                            "Output generated files into dirName " + defaultOpt(dflt),
                            (opt) -> {
                                CONFIG.put(keyName, opt);
                            }
                    );
        }
        {
            final String keyName = "verbosity";
            final String dflt = CONFIG.get(keyName).toString();
            CMD_OPTIONS
                    .add(
                            "-v|--verbosity level",
                            "Message verbosity level " + defaultOpt(dflt),
                            (opt) -> {
                                CONFIG.put(keyName, Integer.parseInt((String) opt));
                            }
                    );
        }
        {
            final String keyName = "class";
            final String dflt = CONFIG.get(keyName).toString();
            CMD_OPTIONS
                    .add(
                            "-c|--class class",
                            "Default class " + defaultOpt(dflt),
                            (opt) -> {
                                CONFIG.put(keyName, opt);
                            }
                    );
        }
        {
            final String keyName = "jars";
            final String dflt = CONFIG.get(keyName).toString();
            CMD_OPTIONS
                    .add(
                            "-j|--jars jar1:...",
                            "Colon (:) separated .jar file(s) " + defaultOpt(dflt),
                            (opt) -> {
                                CONFIG.put(keyName, opt);
                            }
                    );
        }
    }

    private static String defaultOpt(final String dflt) {
        return "(default: " + dflt + ")";
    }

    /**
     * These messages are added to those in apfe/messages.txt.
     */
    private static final String MESSAGES[] = new String[]{
        "LG-DIR | %s: could not create directory specified by --outputDir.",
        "LG-EXIT | Cannot continue due to %d error(s).",
        "LG-FILE-1 | %s: processing ...",
        "LG-FILE-2 | %s: could not read file (%s).",
        "LG-FILE-3 | %s: creating ...",
        "LG-NOSRC | Missing source file(s).",
        "LG-EXCPT | Unhandled exception"
    };

    static {
        MessageMgr.addMessages(MESSAGES);
    }
}
