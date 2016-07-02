
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class IdentCont extends Acceptor {

    public IdentCont() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new CharClass(CharClass.matchRange('a','z'),
CharClass.matchRange('A','Z'),
CharClass.matchOneOf('_'),
CharClass.matchRange('0','9')) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public IdentCont create() {
        return new IdentCont();
    }

 


}


