package laol.parser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import apfe.runtime.Token;
import apfe.runtime.Scanner;

%%

%public
%class LaolScanner
%extends    Scanner
%implements ITokenCodes
%unicode
%line
%column
%function xnextToken
%type Token

%{
  private final StringBuilder string = new StringBuilder();

  private static String stFileName = null;

  public static void setFileName(String fn) {
    stFileName = fn;
  }

  public static String getFileName() {
    return stFileName;
  }

  private Token create(int id, String text) {
  	return new Token(stFileName, yyline+1, yycolumn+1, text, id);
  }

  private Token create(int id) {
  	return create(id, yytext());
  }

    public static LaolScanner create(String fn) throws IOException {
        try (FileReader rdr = new FileReader(fn)) {
            LaolScanner scanner = new LaolScanner(rdr);
            setFileName(fn);
            scanner.slurp();
            return scanner;
        }
    }

  //TODO: derive from RuntimeException to pass back to parser
  private void error(String msg) {
      StringBuilder sb = new StringBuilder("Error: ");
      sb.append(getFileName()).append(':').append(yyline+1).append(':')
              .append(yycolumn+1).append(": ").append(msg)
              .append(": ").append(yytext());
      throw new RuntimeException(sb.toString());
  }

    @Override
    public Token nextToken() {
        try {
            return xnextToken();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

  @Override  
  public boolean isEOF() {
    return zzAtEOF;
    }

	@Override
	public String getAsString(int tokCode) {
		return stMap.get(tokCode);
	}

	private static final HashMap<Integer,String> stMap = new HashMap<>();
	static {
		stMap.put(COMMENT,"<COMMENT>");
		stMap.put(STRING_LITERAL,"<STRING>");
		stMap.put(ARRAY_OF,"%[wi]{...}");
		stMap.put(REGEXP,"/.../|%r{...}");
		stMap.put(PREFIX_OP,"(--|++)_");
		stMap.put(NL,"\\n");
		//{contents of map
		stMap.put(K_ABSTRACT,"abstract");
		stMap.put(K_ALIAS,"alias");
		stMap.put(K_BREAK,"break");
		stMap.put(K_CASE,"case");
		stMap.put(K_CATCH,"catch");
		stMap.put(K_CLASS,"class");
		stMap.put(K_DEF,"def");
		stMap.put(K_ELSE,"else");
		stMap.put(K_ELSIF,"elsif");
		stMap.put(K_EXTENDS,"extends");
		stMap.put(K_FALSE,"false");
		stMap.put(K_FINALLY,"finally");
		stMap.put(K_FOR,"for");
		stMap.put(K_IF,"if");
		stMap.put(K_IMPLEMENTS,"implements");
		stMap.put(K_IN,"in");
		stMap.put(K_MIXIN,"mixin");
		stMap.put(K_MODULE,"module");
		stMap.put(K_NEXT,"next");
		stMap.put(K_NEW,"new");
		stMap.put(K_NIL,"nil");
		stMap.put(K_PRIVATE,"private");
		stMap.put(K_PROTECTED,"protected");
		stMap.put(K_PUBLIC,"public");
		stMap.put(K_REQUIRE,"require");
		stMap.put(K_RETURN,"return");
		stMap.put(K_STATIC,"static");
		stMap.put(K_SUPER,"super");
		stMap.put(K_THIS,"this");
		stMap.put(K_THROW,"throw");
		stMap.put(K_TRUE,"true");
		stMap.put(K_TRY,"try");
		stMap.put(K_UNLESS,"unless");
		stMap.put(K_UNTIL,"until");
		stMap.put(K_VAL,"val");
		stMap.put(K_VAR,"var");
		stMap.put(K_WHEN,"when");
		stMap.put(K_WHILE,"while");
		stMap.put(K_FILE,"__FILE__");
		stMap.put(K_TARGET,"__TARGET__");
		stMap.put(COLON,":");
		stMap.put(COLON2,"::");
		stMap.put(SEMI,";");
		stMap.put(DOT,".");
		stMap.put(DOT2,"..");
		stMap.put(COMMA,",");
		stMap.put(LT,"<");
		stMap.put(LTEQ,"<=");
		stMap.put(LT2,"<<");
		stMap.put(LT2_EQ,"<<=");
		stMap.put(GT,">");
		stMap.put(GTEQ,">=");
		stMap.put(GT2,">>");
		stMap.put(GT2_EQ,">>=");
		stMap.put(EQ,"=");
		stMap.put(EQ2,"==");
		stMap.put(NEQ,"!=");
		stMap.put(EXCL,"!");
		stMap.put(TILDE,"~");
		stMap.put(CARET,"^");
		stMap.put(AND,"&");
		stMap.put(AND2,"&&");
		stMap.put(AND_EQ,"&=");
		stMap.put(OR,"|");
		stMap.put(OR2,"||");
		stMap.put(OR_EQ,"|=");
		stMap.put(STAR,"*");
		stMap.put(STAR_EQ,"*=");
		stMap.put(MINUS,"-");
		stMap.put(MINUS2,"--");
		stMap.put(MINUS_EQ,"-=");
		stMap.put(ARROW,"->");
		stMap.put(PLUS,"+");
		stMap.put(PLUS2,"++");
		stMap.put(PLUS_EQ,"+=");
		stMap.put(QMARK,"?");
		stMap.put(DIV,"/");
		stMap.put(DIV_EQ,"/=");
		stMap.put(LCURLY,"{");
		stMap.put(RCURLY,"}");
		stMap.put(LPAREN,"(");
		stMap.put(RPAREN,")");
		stMap.put(LBRACK,"[");
		stMap.put(RBRACK,"]");
		stMap.put(PCNT,"%");
		stMap.put(PCNT_EQ,"%=");
		stMap.put(IDENT,"<IDENT>");
		stMap.put(UNSIGNED_NUMBER,"<UNSIGNED_NUMBER>");
		stMap.put(REAL_NUMBER,"<REAL_NUMBER>");
		stMap.put(BASED_NUMBER,"<BASED_NUMBER>");
		//}map
	}
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

Space = [ \t\f]

/* comments */
Comment = ("/*" ~"*/") | {EndOfLineComment}
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

Regexp  = (\/[^\s/]+\/) | (%r\{[^\}]*\})
ArrayOf = %[wi]\{[^\}]*\}

/* identifiers */
IDENT = [a-zA-Z_][a-zA-Z_0-9]*

// Verilog style numbers (for good or bad... :) )
OctDigit = [0-7]
HexDigit = [0-9a-fA-F]
NonZeroUnsignedNumber = [1-9][_0-9]*
UnsignedNumber        = [0-9][_0-9]*
RealNumber = {UnsignedNumber} '.' {UnsignedNumber} ([eE] [+-]? {UnsignedNumber})?
BinaryValue = [01?][01_]* 
OctalValue = [0-7?][0-7_]*
HexValue = [0-9a-fA-F][0-9a-fA-F_]*
DecimalBase = \' [sS]? [dD] {Space}*
BinaryBase = \' [sS]? [bB] {Space}*
OctalBase = \' [sS]? [oO] {Space}*
HexBase = \' [sS]? [hH] {Space}*
Size = {NonZeroUnsignedNumber} {Space}*

//A.8.7 Numbers
UNSIGNED_NUMBER = {UnsignedNumber}
REAL_NUMBER = {RealNumber}
BASED_NUMBER = {IntegralNumber}
IntegralNumber = {Size}? ({OctalNumber} | {BinaryNumber} | {HexNumber} | {DecimalNumber})
DecimalNumber = {DecimalBase} {UnsignedNumber}
BinaryNumber = {BinaryBase} {BinaryValue}
OctalNumber = {OctalBase} {OctalValue}
HexNumber = {HexBase} {HexValue}

/* string and character literals */
StringCharacter = [^\r\n\"\\]

%state STRING

%%

<YYINITIAL> {
    {Space}+      		{ /* ignore */ }
    {LineTerminator}   	{return create(NL);}
    {Comment}     		{return create(COMMENT);}
	{Regexp}			{return create(REGEXP);}
	{ArrayOf} 	        {return create(ARRAY_OF);}

	"--_" | "++_"		{return create(PREFIX_OP);}

    //{insert 'create'
	"abstract" {return create(K_ABSTRACT);}
	"alias" {return create(K_ALIAS);}
	"break" {return create(K_BREAK);}
	"case" {return create(K_CASE);}
	"catch" {return create(K_CATCH);}
	"class" {return create(K_CLASS);}
	"def" {return create(K_DEF);}
	"else" {return create(K_ELSE);}
	"elsif" {return create(K_ELSIF);}
	"extends" {return create(K_EXTENDS);}
	"false" {return create(K_FALSE);}
	"finally" {return create(K_FINALLY);}
	"for" {return create(K_FOR);}
	"if" {return create(K_IF);}
	"implements" {return create(K_IMPLEMENTS);}
	"in" {return create(K_IN);}
	"mixin" {return create(K_MIXIN);}
	"module" {return create(K_MODULE);}
	"next" {return create(K_NEXT);}
	"new" {return create(K_NEW);}
	"nil" {return create(K_NIL);}
	"private" {return create(K_PRIVATE);}
	"protected" {return create(K_PROTECTED);}
	"public" {return create(K_PUBLIC);}
	"require" {return create(K_REQUIRE);}
	"return" {return create(K_RETURN);}
	"static" {return create(K_STATIC);}
	"super" {return create(K_SUPER);}
	"this" {return create(K_THIS);}
	"throw" {return create(K_THROW);}
	"true" {return create(K_TRUE);}
	"try" {return create(K_TRY);}
	"unless" {return create(K_UNLESS);}
	"until" {return create(K_UNTIL);}
	"val" {return create(K_VAL);}
	"var" {return create(K_VAR);}
	"when" {return create(K_WHEN);}
	"while" {return create(K_WHILE);}
	"__FILE__" {return create(K_FILE);}
	"__TARGET__" {return create(K_TARGET);}
	":" {return create(COLON);}
	"::" {return create(COLON2);}
	";" {return create(SEMI);}
	"." {return create(DOT);}
	".." {return create(DOT2);}
	"," {return create(COMMA);}
	"<" {return create(LT);}
	"<=" {return create(LTEQ);}
	"<<" {return create(LT2);}
	"<<=" {return create(LT2_EQ);}
	">" {return create(GT);}
	">=" {return create(GTEQ);}
	">>" {return create(GT2);}
	">>=" {return create(GT2_EQ);}
	"=" {return create(EQ);}
	"==" {return create(EQ2);}
	"!=" {return create(NEQ);}
	"!" {return create(EXCL);}
	"~" {return create(TILDE);}
	"^" {return create(CARET);}
	"&" {return create(AND);}
	"&&" {return create(AND2);}
	"&=" {return create(AND_EQ);}
	"|" {return create(OR);}
	"||" {return create(OR2);}
	"|=" {return create(OR_EQ);}
	"*" {return create(STAR);}
	"*=" {return create(STAR_EQ);}
	"-" {return create(MINUS);}
	"--" {return create(MINUS2);}
	"-=" {return create(MINUS_EQ);}
	"->" {return create(ARROW);}
	"+" {return create(PLUS);}
	"++" {return create(PLUS2);}
	"+=" {return create(PLUS_EQ);}
	"?" {return create(QMARK);}
	"/" {return create(DIV);}
	"/=" {return create(DIV_EQ);}
	"{" {return create(LCURLY);}
	"}" {return create(RCURLY);}
	"(" {return create(LPAREN);}
	")" {return create(RPAREN);}
	"[" {return create(LBRACK);}
	"]" {return create(RBRACK);}
	"%" {return create(PCNT);}
	"%=" {return create(PCNT_EQ);}
	{IDENT} {return create(IDENT);}
	{UNSIGNED_NUMBER} {return create(UNSIGNED_NUMBER);}
	{REAL_NUMBER} {return create(REAL_NUMBER);}
	{BASED_NUMBER} {return create(BASED_NUMBER);}
    //create}

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return create(STRING_LITERAL, string.toString()); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8); string.append( val ); }
  \\h{HexDigit}?{HexDigit}       { char val = (char) Integer.parseInt(yytext().substring(1),16); string.append( val ); }
  
  /* error cases */
  \\.                            { error("Illegal escape sequence"); }
  {LineTerminator}               { error("Unterminated string at end of line"); }
}

/* error fallback */
[^]                              { error("Illegal character"); }
<<EOF>>                          { return create(Token.EOF);  }
