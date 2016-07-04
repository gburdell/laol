package laol.parser;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import laol.parser.apfe.Grammar;
import gblib.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class GrammarTest {

    public GrammarTest() {

    }

    static {
        //dont clutter message with these
        ParseError.setSkipErrorHints("' '", "'\\t'", "'//'", "'/*'");
    }

    /**
     * Test of accepti method, of class Grammar.
     */
    @Test
    public void testAccepti() {
        final String fns[] = {
            //"test/data/t2.txt",
            "test/data/t1.txt",
            "test/data/collections.laol"
        };
        try {
            for (String fn : fns) {
                InputStream fis = new InputStream(fn);
                System.out.println("Info: " + fn);
                CharBuffer cbuf = fis.newCharBuffer();
                CharBufState.create(cbuf, true);
                Grammar gram = new Grammar();
                Acceptor acc = gram.accept();
                if (null != acc) {
                    String ss = acc.toString();
                    System.out.println("returns:\n========\n" + ss);
                }
                boolean result = (null != acc) && CharBufState.getTheOne().isEOF();
                if (!result) {
                    ParseError.printTopMessage();
                }
                assertTrue(result);
            }
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
    }

}
