
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ModuleItem extends Acceptor {

    public ModuleItem() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new SEMI() ;
break;
case 1:
acc = new Sequence(new AssignmentLhs(),
new AssignmentOp(),
new AssignmentRhs()) ;
break;
case 2:
acc = new Sequence(new ClassDeclaration(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 3:
acc = new Sequence(new MethodDeclaration(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 4:
acc = new Sequence(new ModuleDeclaration(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
}
return acc;
}
}) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ModuleItem create() {
        return new ModuleItem();
    }

 


}


