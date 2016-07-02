
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class UntilStatement extends Acceptor {

    public UntilStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KUNTIL(),
new Expression(),
new Statement()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public UntilStatement create() {
        return new UntilStatement();
    }

 


}


