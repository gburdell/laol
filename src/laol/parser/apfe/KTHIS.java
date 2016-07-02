
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTHIS extends Acceptor {

    public KTHIS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("this"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTHIS create() {
        return new KTHIS();
    }

 


}


