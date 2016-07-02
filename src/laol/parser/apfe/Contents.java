
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Contents extends Acceptor {

    public Contents() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new RequireStatement(), Repetition.ERepeat.eZeroOrMore),
new Repetition(new FileItem(), Repetition.ERepeat.eOneOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Contents create() {
        return new Contents();
    }

 


}


