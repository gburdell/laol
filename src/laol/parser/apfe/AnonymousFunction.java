
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AnonymousFunction extends Acceptor {

    public AnonymousFunction() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new ARROW(),
new MethodParamDecl(),
new LCURLY(),
new MethodBody(),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public AnonymousFunction create() {
        return new AnonymousFunction();
    }

 


}


