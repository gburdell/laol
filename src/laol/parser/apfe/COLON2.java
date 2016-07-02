
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class COLON2 extends Acceptor {

    public COLON2() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new CharSeq("::") ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public COLON2 create() {
        return new COLON2();
    }

 


}


