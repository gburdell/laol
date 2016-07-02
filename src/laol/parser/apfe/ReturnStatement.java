
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ReturnStatement extends Acceptor {

    public ReturnStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KRETURN(),
new Expression()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ReturnStatement create() {
        return new ReturnStatement();
    }

 


}


