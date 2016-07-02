
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ParamExpressionList extends Acceptor {

    public ParamExpressionList() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new UnnamedParam(), Repetition.ERepeat.eOptional),
new Repetition(new NamedParam(), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ParamExpressionList create() {
        return new ParamExpressionList();
    }

 


}


