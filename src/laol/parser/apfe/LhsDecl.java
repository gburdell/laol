
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LhsDecl extends Acceptor {

    public LhsDecl() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new AccessModifier(), Repetition.ERepeat.eOptional),
new Repetition(new KSTATIC(), Repetition.ERepeat.eOptional),
new Repetition(new Mutability(), Repetition.ERepeat.eOptional),
new LhsRef()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public LhsDecl create() {
        return new LhsDecl();
    }

 


}


