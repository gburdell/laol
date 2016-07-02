
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KUNTIL extends Acceptor {

    public KUNTIL() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("until"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KUNTIL create() {
        return new KUNTIL();
    }

 


}


