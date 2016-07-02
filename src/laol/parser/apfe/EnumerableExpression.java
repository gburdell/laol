
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class EnumerableExpression extends Acceptor {

    public EnumerableExpression() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Expression() ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public EnumerableExpression create() {
        return new EnumerableExpression();
    }

 


}


