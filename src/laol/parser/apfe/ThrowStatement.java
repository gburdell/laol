
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ThrowStatement extends Acceptor {

    public ThrowStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KTHROW(),
new Expression()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ThrowStatement create() {
        return new ThrowStatement();
    }

 


}


