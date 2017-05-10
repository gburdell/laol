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
import java.util.Collection;

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

    public static void main(final String argv[], boolean exitOn0) {
        try {
            Main me = new Main(argv);
            final int status = me.process();
            if ((0 != status) || exitOn0) {
                System.exit(status);
            }
        } catch (IOException ex) {
            gblib.Util.abnormalExit(ex);
        }
    }

    public static void main(final String argv[]) {
        main(argv, false);
    }

    private int process() throws IOException {
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
                    status = laol.generate.cxx.Generate
                            .create(m_parsed.getAsts(), CONFIG)
                            .generate();

                }
            }
        }
        return status;
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
        "outputDir " + PROGNM + "/generate/src",
        "verbosity *I1"
    });

    private static final Options CMD_OPTIONS = Options.create();

    static {
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
