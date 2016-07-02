
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KFOR extends Acceptor {

    public KFOR() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("for"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KFOR create() {
        return new KFOR();
    }

 


}


