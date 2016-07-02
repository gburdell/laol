
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KEXTENDS extends Acceptor {

    public KEXTENDS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("extends"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KEXTENDS create() {
        return new KEXTENDS();
    }

 


}


