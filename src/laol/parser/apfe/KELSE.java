
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KELSE extends Acceptor {

    public KELSE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("else"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KELSE create() {
        return new KELSE();
    }

 


}


