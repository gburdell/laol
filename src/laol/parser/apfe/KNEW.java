
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KNEW extends Acceptor {

    public KNEW() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("new"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KNEW create() {
        return new KNEW();
    }

 


}


