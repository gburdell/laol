
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class TILDE extends Acceptor {

    public TILDE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('~'),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public TILDE create() {
        return new TILDE();
    }

 


}


