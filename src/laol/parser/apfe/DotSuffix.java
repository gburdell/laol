
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class DotSuffix extends Acceptor {

    public DotSuffix() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new DOT(),
new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new KNIL(),
new Repetition(new QMARK(), Repetition.ERepeat.eOptional)) ;
break;
case 1:
acc = new KNEW() ;
break;
case 2:
acc = new Sequence(new MethodName(),
new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new EXCL() ;
break;
case 1:
acc = new QMARK() ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOptional)) ;
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
    public DotSuffix create() {
        return new DotSuffix();
    }

 


}


