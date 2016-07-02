
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AND2 extends Acceptor {

    public AND2() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("&&"),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public AND2 create() {
        return new AND2();
    }

 


}


