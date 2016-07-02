
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class HashPrimary extends Acceptor {

    public HashPrimary() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new LCURLY(),
new HashKeyValue(),
new Repetition(new Sequence(new COMMA(),
new HashKeyValue()), Repetition.ERepeat.eZeroOrMore),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public HashPrimary create() {
        return new HashPrimary();
    }

 


}


