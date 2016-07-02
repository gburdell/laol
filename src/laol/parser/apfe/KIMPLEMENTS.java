
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KIMPLEMENTS extends Acceptor {

    public KIMPLEMENTS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("implements"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KIMPLEMENTS create() {
        return new KIMPLEMENTS();
    }

 


}


