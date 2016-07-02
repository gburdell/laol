
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ModuleDeclaration extends Acceptor {

    public ModuleDeclaration() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KMODULE(),
new IDENT(),
new LCURLY(),
new Repetition(new ModuleItem(), Repetition.ERepeat.eZeroOrMore),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ModuleDeclaration create() {
        return new ModuleDeclaration();
    }

 


}


