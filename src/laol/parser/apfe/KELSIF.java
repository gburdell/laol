
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KELSIF extends Acceptor {

    public KELSIF() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("elsif"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KELSIF create() {
        return new KELSIF();
    }

 


}


