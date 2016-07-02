
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Statement extends Acceptor {

    public Statement() {
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
acc = new Sequence(new Expression(),
new STail()) ;
break;
case 3:
acc = new Sequence(new CaseStatement(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 4:
acc = new IfStatement() ;
break;
case 5:
acc = new WhileStatement() ;
break;
case 6:
acc = new UntilStatement() ;
break;
case 7:
acc = new ForStatement() ;
break;
case 8:
acc = new Sequence(new LCURLY(),
new Repetition(new Statement(), Repetition.ERepeat.eZeroOrMore),
new RCURLY(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 9:
acc = new Sequence(new KBREAK(),
new STail()) ;
break;
case 10:
acc = new Sequence(new KNEXT(),
new STail()) ;
break;
case 11:
acc = new Sequence(new KALIAS(),
new MethodName(),
new MethodName(),
new STail()) ;
break;
case 12:
acc = new Sequence(new ReturnStatement(),
new STail()) ;
break;
case 13:
acc = new TryStatement() ;
break;
case 14:
acc = new Sequence(new ThrowStatement(),
new STail()) ;
break;
case 15:
acc = new Sequence(new ClassDeclaration(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 16:
acc = new Sequence(new MethodDeclaration(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
break;
case 17:
acc = new Sequence(new MixinStatement(),
new STail()) ;
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
    public Statement create() {
        return new Statement();
    }

 


}


