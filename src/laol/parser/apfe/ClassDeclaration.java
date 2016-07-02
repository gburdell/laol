
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ClassDeclaration extends Acceptor {

    public ClassDeclaration() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new AccessModifier(), Repetition.ERepeat.eOptional),
new Repetition(new KABSTRACT(), Repetition.ERepeat.eOptional),
new KCLASS(),
new IDENT(),
new Repetition(new MethodParamDecl(), Repetition.ERepeat.eOptional),
new Repetition(new ClassExtends(), Repetition.ERepeat.eOptional),
new LCURLY(),
new ClassBody(),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ClassDeclaration create() {
        return new ClassDeclaration();
    }

 


}


