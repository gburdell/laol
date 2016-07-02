
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class DIVEQ extends Acceptor {

    public DIVEQ() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("/="),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public DIVEQ create() {
        return new DIVEQ();
    }

 


}


