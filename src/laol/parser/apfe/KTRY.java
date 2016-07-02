
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTRY extends Acceptor {

    public KTRY() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("try"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTRY create() {
        return new KTRY();
    }

 


}


