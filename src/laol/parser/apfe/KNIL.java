
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KNIL extends Acceptor {

    public KNIL() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("nil"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KNIL create() {
        return new KNIL();
    }

 


}


