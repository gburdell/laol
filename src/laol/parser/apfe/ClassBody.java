
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ClassBody extends Acceptor {

    public ClassBody() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new BaseClassInitializer(), Repetition.ERepeat.eOptional),
new Repetition(new Statement(), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ClassBody create() {
        return new ClassBody();
    }

 


}


