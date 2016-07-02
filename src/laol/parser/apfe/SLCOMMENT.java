
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class SLCOMMENT extends Acceptor {

    public SLCOMMENT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("//"),
new Repetition(new Sequence(new NotPredicate(new EOL()),
new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
new EOL()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public SLCOMMENT create() {
        return new SLCOMMENT();
    }

 


}


