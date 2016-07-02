
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ForStatement extends Acceptor {

    public ForStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KFOR(),
new ForVariableName(),
new KIN(),
new EnumerableExpression(),
new Statement()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ForStatement create() {
        return new ForStatement();
    }

 


}


