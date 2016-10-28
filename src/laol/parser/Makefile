ANTLR := antlr4
ANTLR_OPTS := -no-listener -visitor

.PHONY: goal clean

goal: LaolParser.java ;

LaolParser.java: Laol.g4
	${ANTLR} ${ANTLR_OPTS} $<

clean:
	rm -f Laol{*Listener,*Visitor,Lexer,Parser,}.{java,tokens}
