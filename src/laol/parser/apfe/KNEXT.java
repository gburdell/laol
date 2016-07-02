
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KNEXT extends Acceptor {

    public KNEXT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("next"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KNEXT create() {
        return new KNEXT();
    }

 


}


