
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodName extends Acceptor {

    public MethodName() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new Repetition(new METHODNAMEIDENT(), Repetition.ERepeat.eOptional),
new MethodNameOp()) ;
break;
case 1:
acc = new METHODNAMEIDENT() ;
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
    public MethodName create() {
        return new MethodName();
    }

 


}


