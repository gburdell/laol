
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KRETURN extends Acceptor {

    public KRETURN() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("return"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KRETURN create() {
        return new KRETURN();
    }

 


}


