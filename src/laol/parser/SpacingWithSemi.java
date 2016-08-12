package laol.parser;

import apfe.runtime.*;
import static gblib.Util.downCast;
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
        if (0 < rep.sizeofAccepted()) {
            boolean ok = false;
            PrioritizedChoice pc;
            for (Acceptor curr : rep.getAccepted()) {
                pc = downCast(curr);
                ok |= (pc.getAccepted() instanceof SEMI) 
                        || (pc.getBaseAccepted() instanceof EOL);
            }
            stLastIsSemiOrEOL = ok;
        }
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
        if (match) {
            setLastSeen(downCast(matcher));
        }
        return match;
    }

    @Override
    public SpacingWithSemi create() {
        return new SpacingWithSemi();
    }

}
