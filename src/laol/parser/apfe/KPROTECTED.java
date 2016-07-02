
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KPROTECTED extends Acceptor {

    public KPROTECTED() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("protected"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KPROTECTED create() {
        return new KPROTECTED();
    }

 


}


