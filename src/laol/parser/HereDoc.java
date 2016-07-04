package laol.parser;

import apfe.runtime.*;

public class HereDoc extends Acceptor {

    public HereDoc() {
    }

    private StringBuilder m_doc;
    private String m_ident;

    @Override
    protected boolean accepti() {
        Sequence matcher = new Sequence(new CharSeq("<<"), new IDENT());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            m_ident = Util.getIdent(matcher, 1);
            m_doc = new StringBuilder();
            String line;
            do {
                matcher = new Sequence(
                        new Repetition(new WS(), Repetition.ERepeat.eZeroOrMore),
                        new CharSeq(m_ident),
                        new Repetition(new WS(), Repetition.ERepeat.eZeroOrMore),
                        new AndPredicate(new Char(';')));
                match = (null != match(matcher));
                if (match) {
                    break; //while
                }
                matcher = new Sequence(new Repetition(new Sequence(new NotPredicate(new EOL()),
                        new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
                        new EOL());
                match = (null != match(matcher));
                if (!match) {
                    m_baseAccepted = null;
                    break;  //while
                }
                line = matcher.toString();
                m_doc.append(line);
            } while (match);
        }

        return match;
    }

    @Override
    public HereDoc create() {
        return new HereDoc();
    }

}
