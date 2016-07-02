
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KALIAS extends Acceptor {

    public KALIAS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("alias"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KALIAS create() {
        return new KALIAS();
    }

 


}


