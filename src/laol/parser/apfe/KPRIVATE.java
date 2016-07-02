
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KPRIVATE extends Acceptor {

    public KPRIVATE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("private"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KPRIVATE create() {
        return new KPRIVATE();
    }

 


}


