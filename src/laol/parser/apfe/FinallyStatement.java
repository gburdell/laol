
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class FinallyStatement extends Acceptor {

    public FinallyStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KFINALLY(),
new Statement()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public FinallyStatement create() {
        return new FinallyStatement();
    }

 


}


