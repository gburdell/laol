
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MLCOMMENT extends Acceptor {

    public MLCOMMENT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("/*"),
new Repetition(new Sequence(new NotPredicate(new CharSeq("*/")),
new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
new CharSeq("*/")) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MLCOMMENT create() {
        return new MLCOMMENT();
    }

 


}


