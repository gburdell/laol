
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KUNLESS extends Acceptor {

    public KUNLESS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("unless"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KUNLESS create() {
        return new KUNLESS();
    }

 


}


