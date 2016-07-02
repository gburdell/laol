
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class RequireStatement extends Acceptor {

    public RequireStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KREQUIRE(),
new STRING(),
new STail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public RequireStatement create() {
        return new RequireStatement();
    }

 


}


