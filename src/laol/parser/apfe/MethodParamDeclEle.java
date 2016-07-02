
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodParamDeclEle extends Acceptor {

    public MethodParamDeclEle() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new MethodParamDeclModifier(),
new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new AND() ;
break;
case 1:
acc = new STAR() ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOptional),
new IDENT(),
new Repetition(new MethodParamDeclDefault(), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodParamDeclEle create() {
        return new MethodParamDeclEle();
    }

 


}


