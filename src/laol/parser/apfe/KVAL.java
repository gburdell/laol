
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KVAL extends Acceptor {

    public KVAL() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("val"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KVAL create() {
        return new KVAL();
    }

 


}


