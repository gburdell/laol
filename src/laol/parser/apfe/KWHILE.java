
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KWHILE extends Acceptor {

    public KWHILE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("while"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KWHILE create() {
        return new KWHILE();
    }

 


}


