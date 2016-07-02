
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KFINALLY extends Acceptor {

    public KFINALLY() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("finally"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KFINALLY create() {
        return new KFINALLY();
    }

 


}


