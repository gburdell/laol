
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KMODULE extends Acceptor {

    public KMODULE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("module"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KMODULE create() {
        return new KMODULE();
    }

 


}


