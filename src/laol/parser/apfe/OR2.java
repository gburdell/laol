
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class OR2 extends Acceptor {

    public OR2() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("||"),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public OR2 create() {
        return new OR2();
    }

 


}


