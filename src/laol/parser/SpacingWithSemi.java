package laol.parser;

import apfe.runtime.*;
import laol.parser.apfe.Comment;
import laol.parser.apfe.SEMI;

public class SpacingWithSemi extends Acceptor {

    private static boolean stLastIsSemiOrEOL;
    
    public SpacingWithSemi() {
        stLastIsSemiOrEOL = false;
    }
    
    public static boolean lastWasSemiOrEOL() {
        return stLastIsSemiOrEOL;
    }
    
    private static void setLastSeen(final Repetition rep) {
        
    }

    @Override
    protected boolean accepti() {
        Acceptor matcher = new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor acc = null;
                switch (ix) {
                    case 0:
                        acc = new SEMI();
                        break;
                    case 1:
                        acc = new WS();
                        break;
                    case 2:
                        acc = new Comment();
                        break;
                    case 3:
                        acc = new EOL();
                        break;
                }
                return acc;
            }
        }), Repetition.ERepeat.eZeroOrMore);
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public SpacingWithSemi create() {
        return new SpacingWithSemi();
    }

}
