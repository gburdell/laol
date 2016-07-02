
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KCASE extends Acceptor {

    public KCASE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("case"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KCASE create() {
        return new KCASE();
    }

 


}


