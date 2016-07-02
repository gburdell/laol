
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class OREQ extends Acceptor {

    public OREQ() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("|="),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public OREQ create() {
        return new OREQ();
    }

 


}


