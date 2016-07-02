
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class FileItem extends Acceptor {

    public FileItem() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new ModuleDeclaration() ;
break;
case 1:
acc = new Statement() ;
break;
}
return acc;
}
}) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public FileItem create() {
        return new FileItem();
    }

 


}


