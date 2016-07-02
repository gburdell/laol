
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class BasedNumber extends Acceptor {

    public BasedNumber() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new Sequence(new CharClass(CharClass.matchRange('0','9')),
new Repetition(new CharClass(CharClass.matchRange('0','9'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eZeroOrMore)), Repetition.ERepeat.eOptional),
new NotPredicate(new STRING()),
new CharSeq('\''),
new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new CharClass(CharClass.matchOneOf('b'),
CharClass.matchOneOf('B')),
new Repetition(new CharClass(CharClass.matchOneOf('0'),
CharClass.matchOneOf('1'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eOneOrMore)) ;
break;
case 1:
acc = new Sequence(new CharClass(CharClass.matchOneOf('d'),
CharClass.matchOneOf('D')),
new Repetition(new CharClass(CharClass.matchRange('0','9'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eOneOrMore)) ;
break;
case 2:
acc = new Sequence(new CharClass(CharClass.matchOneOf('h'),
CharClass.matchOneOf('H')),
new Repetition(new CharClass(CharClass.matchRange('a','f'),
CharClass.matchRange('A','F'),
CharClass.matchRange('0','9'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eOneOrMore)) ;
break;
case 3:
acc = new Sequence(new CharClass(CharClass.matchOneOf('o'),
CharClass.matchOneOf('O')),
new Repetition(new CharClass(CharClass.matchRange('0','7'),
CharClass.matchOneOf('_')), Repetition.ERepeat.eOneOrMore)) ;
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
    public BasedNumber create() {
        return new BasedNumber();
    }

 


}


