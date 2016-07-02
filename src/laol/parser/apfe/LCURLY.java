
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LCURLY extends Acceptor {

    public LCURLY() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('{'),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public LCURLY create() {
        return new LCURLY();
    }

 


}


