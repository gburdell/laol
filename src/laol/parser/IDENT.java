package laol.parser;

import laol.parser.apfe.Spacing;
import apfe.runtime.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IDENT extends Acceptor {

    protected IDENT(boolean allowKwrd) {
        ALLOW_KEYWORDS = allowKwrd;
    }

    public IDENT() {
        this(true);
    }

    @Override
    // [a-zA-Z_] [a-zA-Z0-9_]* ('?'|'!')? Spacing
    protected boolean accepti() {
        Sequence matcher = new Sequence(
                new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('A', 'Z'),
                        CharClass.matchOneOf('_')),
                new Repetition(new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('A', 'Z'),
                        CharClass.matchOneOf('_'),
                        CharClass.matchRange('0', '9')), Repetition.ERepeat.eZeroOrMore),
                new Repetition(new CharClass(CharClass.matchOneOf("?!")), Repetition.ERepeat.eOptional),
                new Spacing());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            m_ident = matcher.getTexts(0, 1);
            match &= ALLOW_KEYWORDS || !KEYWORD_MAP.contains(m_ident);
        }
        return match;
    }

    private final boolean ALLOW_KEYWORDS;

    private String m_ident;

    public String getIdent() {
        return m_ident;
    }

    @Override
    public IDENT create() {
        return new IDENT();
    }

    private static final Set<String> KEYWORD_MAP = new HashSet<>(
            Arrays.asList(
                    "alias",
                    "bool", "break",
                    "case", "catch", "class", "const",
                    "def", "default", "double",
                    "else", "elsif",
                    "extends",
                    "false", "finally", "for",
                    "if", "implements", "import", "in", "int", "interface",
                    "next", "new", "nil",
                    "package", "private", "protected", "public",
                    "return",
                    "static", "string", "super",
                    "this", "throw", "true", "try",
                    "unless", "until",
                    "var",
                    "when", "while"
            ));

    //NOTE: cannot memoize!
    /**
     * Begin memoize
     *
     * @Override protected void memoize(Marker mark, Marker endMark) {
     * stMemo.add(mark, this, endMark); }
     *
     * @Override protected Memoize.Data hasMemoized(Marker mark) { return
     * stMemo.memoized(mark); } /// // Memoize for all instances of IDENT. //
     * private static final Memoize stMemo = new Memoize(); //End memoize
    *
     */
}
