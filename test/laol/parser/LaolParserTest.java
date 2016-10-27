package laol.parser;

import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class LaolParserTest {

    private static final String PATH = "/home/gburdell/projects/laol/test/data/";
    
    private static final String FILES[] = {
        PATH + "t1.txt",
        PATH + "Map.laol"
        //PATH + "collections.laol"
    };
    
    @Test
    public void testFile() throws IOException {
        for (String fn : FILES) {
            System.out.println("===============================\n" + fn);
            ANTLRFileStream inf = new ANTLRFileStream(fn);
            LaolLexer lexer = new LaolLexer(inf);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LaolParser parser = new LaolParser(tokens);
            ParseTree tree = parser.file();
            System.out.println(tree.toStringTree(parser));
        }
    } 

}
