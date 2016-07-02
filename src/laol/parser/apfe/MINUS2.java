
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MINUS2 extends Acceptor {

    public MINUS2() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("--"),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MINUS2 create() {
        return new MINUS2();
    }

 


}


