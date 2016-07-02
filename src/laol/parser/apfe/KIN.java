
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KIN extends Acceptor {

    public KIN() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("in"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KIN create() {
        return new KIN();
    }

 


}


