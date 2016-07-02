
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Grammar extends Acceptor {

    public Grammar() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Spacing(),
new Repetition(new Contents(), Repetition.ERepeat.eOptional),
new EOF()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Grammar create() {
        return new Grammar();
    }

 


}


