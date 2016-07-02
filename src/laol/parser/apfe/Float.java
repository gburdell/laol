
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Float extends Acceptor {

    public Float() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Digits(),
new Repetition(new Sequence(new CharSeq('.'),
new Digits()), Repetition.ERepeat.eOptional),
new Repetition(new Sequence(new CharClass(CharClass.matchOneOf('e'),
CharClass.matchOneOf('E')),
new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq('-') ;
break;
case 1:
acc = new CharSeq('+') ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOptional),
new Digits()), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Float create() {
        return new Float();
    }

 


}


