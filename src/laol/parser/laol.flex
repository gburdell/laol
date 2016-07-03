package laol.parser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import apfe.runtimev2.Token;
import apfe.runtimev2.Scanner;

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

  public LaolScanner(String fn) throws FileNotFoundException {
    this(new FileReader(fn));
    setFileName(fn);
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
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

Space = [ \t\f]

/* comments */
Comment = ("/*" ~"*/") | {EndOfLineComment}
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

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
    {LineTerminator}   	{return create(EOL);}
    {Comment}     		{return create(COMMENT);}

    //{insert 'create'
	"abstract" {return create(ABSTRACT_K);}
	"alias" {return create(ALIAS_K);}
	"break" {return create(BREAK_K);}
	"case" {return create(CASE_K);}
	"catch" {return create(CATCH_K);}
	"class" {return create(CLASS_K);}
	"def" {return create(DEF_K);}
	"else" {return create(ELSE_K);}
	"elsif" {return create(ELSIF_K);}
	"extends" {return create(EXTENDS_K);}
	"false" {return create(FALSE_K);}
	"finally" {return create(FINALLY_K);}
	"for" {return create(FOR_K);}
	"if" {return create(IF_K);}
	"implements" {return create(IMPLEMENTS_K);}
	"in" {return create(IN_K);}
	"mixin" {return create(MIXIN_K);}
	"module" {return create(MODULE_K);}
	"next" {return create(NEXT_K);}
	"new" {return create(NEW_K);}
	"nil" {return create(NIL_K);}
	"private" {return create(PRIVATE_K);}
	"protected" {return create(PROTECTED_K);}
	"public" {return create(PUBLIC_K);}
	"require" {return create(REQUIRE_K);}
	"return" {return create(RETURN_K);}
	"static" {return create(STATIC_K);}
	"super" {return create(SUPER_K);}
	"this" {return create(THIS_K);}
	"throw" {return create(THROW_K);}
	"true" {return create(TRUE_K);}
	"try" {return create(TRY_K);}
	"unless" {return create(UNLESS_K);}
	"until" {return create(UNTIL_K);}
	"val" {return create(VAL_K);}
	"var" {return create(VAR_K);}
	"when" {return create(WHEN_K);}
	"while" {return create(WHILE_K);}
	"__FILE__" {return create(FILE_K);}
	"__TARGET__" {return create(TARGET_K);}
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
  \\h{HexDigit}?{HexDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),16); string.append( val ); }
  
  /* error cases */
  \\.                            { error("Illegal escape sequence"); }
  {LineTerminator}               { error("Unterminated string at end of line"); }
}

/* error fallback */
[^]                              { error("Illegal character"); }
<<EOF>>                          { return create(Token.EOF);  }
