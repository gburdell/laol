
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodBody extends Acceptor {

    public MethodBody() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Repetition(new Statement(), Repetition.ERepeat.eZeroOrMore) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodBody create() {
        return new MethodBody();
    }

 


}


