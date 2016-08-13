package laol.parser;

import laol.parser.apfe.Spacing;
import apfe.runtime.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IDENT extends Acceptor {

    public IDENT() {
    }

    @Override
    // [a-zA-Z_] [a-zA-Z0-9_]* Spacing
    protected boolean accepti() {
        Sequence matcher = new Sequence(
                new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('A', 'Z'),
                        CharClass.matchOneOf('_')),
                new Repetition(new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('A', 'Z'),
                        CharClass.matchOneOf('_'),
                        CharClass.matchRange('0', '9')), Repetition.ERepeat.eZeroOrMore),
                new Spacing());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            m_ident = matcher.getTexts(0, 1);
            match &= ALLOW_KEYWORDS || !KEYWORD_MAP.contains(m_ident);
        }
        return match;
    }

    private static final boolean ALLOW_KEYWORDS = false;
    
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
                    "abstract", "alias",
                    "break",
                    "case", "catch", "class",
                    "def",
                    "else", "elsif",
                    "extends",
                    "false", "finally", "for",
                    "if", "implements", "in",
                    "mixin", "module", "next", "new", "nil",
                    "private", "protected", "public",
                    "require", "return",
                    "static", "super",
                    "this", "throw", "true", "try",
                    "unless", "until",
                    "val", "var",
                    "when", "while"
            ));
    
}
