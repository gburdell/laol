
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class METHODNAMEIDENT extends Acceptor {

    public METHODNAMEIDENT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharClass(CharClass.matchRange('a','z'),
CharClass.matchRange('A','Z')),
new Repetition(new IdentCont(), Repetition.ERepeat.eZeroOrMore),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public METHODNAMEIDENT create() {
        return new METHODNAMEIDENT();
    }

 


}


