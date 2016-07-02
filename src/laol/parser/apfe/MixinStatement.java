
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MixinStatement extends Acceptor {

    public MixinStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KMIXIN(),
new MixinName(),
new Repetition(new Sequence(new COMMA(),
new MixinName()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MixinStatement create() {
        return new MixinStatement();
    }

 


}


