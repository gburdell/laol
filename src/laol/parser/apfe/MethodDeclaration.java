
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodDeclaration extends Acceptor {

    public MethodDeclaration() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new AccessModifier(), Repetition.ERepeat.eOptional),
new Repetition(new KABSTRACT(), Repetition.ERepeat.eOptional),
new KDEF(),
new MethodName(),
new Repetition(new MethodParamDecl(), Repetition.ERepeat.eOptional),
new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new LCURLY(),
new MethodBody(),
new RCURLY()) ;
break;
case 1:
acc = new SEMI() ;
break;
}
return acc;
}
})) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodDeclaration create() {
        return new MethodDeclaration();
    }

 


}


