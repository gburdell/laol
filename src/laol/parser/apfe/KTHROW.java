
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTHROW extends Acceptor {

    public KTHROW() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("throw"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTHROW create() {
        return new KTHROW();
    }

 


}


