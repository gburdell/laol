
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Number extends Acceptor {

    public Number() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new BasedNumber() ;
break;
case 1:
acc = new Integer() ;
break;
case 2:
acc = new Float() ;
break;
}
return acc;
}
}),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Number create() {
        return new Number();
    }

 


}


