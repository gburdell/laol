
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ClassExtends extends Acceptor {

    public ClassExtends() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new Sequence(new KEXTENDS(),
new IDENT()), Repetition.ERepeat.eOptional),
new Repetition(new Sequence(new KIMPLEMENTS(),
new IDENT(),
new Sequence(new COMMA(),
new IDENT())), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ClassExtends create() {
        return new ClassExtends();
    }

 


}


