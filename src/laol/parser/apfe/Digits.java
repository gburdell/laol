
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Digits extends Acceptor {

    public Digits() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharClass(CharClass.matchRange('0','9')),
new Repetition(new CharClass(CharClass.matchRange('0','9'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eZeroOrMore),
new NotPredicate(new CharSeq('\''))) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Digits create() {
        return new Digits();
    }

 


}


