
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class CatchStatement extends Acceptor {

    public CatchStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KCATCH(),
new Expression(),
new Statement()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public CatchStatement create() {
        return new CatchStatement();
    }

 


}


