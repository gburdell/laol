
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KDEF extends Acceptor {

    public KDEF() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("def"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KDEF create() {
        return new KDEF();
    }

 


}


