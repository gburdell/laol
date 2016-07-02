
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class WhileStatement extends Acceptor {

    public WhileStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KWHILE(),
new Expression(),
new Statement()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public WhileStatement create() {
        return new WhileStatement();
    }

 


}


