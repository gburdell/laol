
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KFALSE extends Acceptor {

    public KFALSE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("false"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KFALSE create() {
        return new KFALSE();
    }

 


}


