package laol.parser;

import apfe.runtime.*;
import static gblib.Util.downCast;
import laol.parser.apfe.Comment;
import laol.parser.apfe.MLCOMMENT;
import laol.parser.apfe.SEMI;
import laol.parser.apfe.SLCOMMENT;

public class SpacingWithSemi extends Acceptor {

    private static boolean stLastIsSemiOrEOL;

    public SpacingWithSemi() {
        stLastIsSemiOrEOL = false;
    }

    public static boolean lastWasSemiOrEOL() {
        return stLastIsSemiOrEOL;
    }

    private static void XXsetLastSeen(final Repetition rep) {
        if (0 < rep.sizeofAccepted()) {
            boolean ok = false;
            PrioritizedChoice pc;
            for (Acceptor curr : rep.getAccepted()) {
                pc = downCast(curr);
                ok |= (pc.getAccepted() instanceof SEMI)
                        || (pc.getBaseAccepted() instanceof EOL);
                if (!ok && (pc.getAccepted() instanceof Comment)) {
                    pc = downCast(pc.getAccepted());
                    ok |= (pc.getAccepted() instanceof SLCOMMENT);
                    if (!ok) {
                        final MLCOMMENT mlc = downCast(pc.getAccepted());
                        ok |= (0 <= mlc.toString().indexOf("\n"));
                    }
                }
                if (ok) {
                    break;
                }
            }
            stLastIsSemiOrEOL = ok;
        }
    }

    private static void setLastSeen(final Acceptor acc) {
        final String s = acc.toString();
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if ((s.charAt(i) == ';') || s.charAt(i) == '\n') {
                    stLastIsSemiOrEOL = true;
                    break;
                }
            }
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
