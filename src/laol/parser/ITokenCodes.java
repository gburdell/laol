/*
 * The MIT License
 *
 * Copyright 2016 gburdell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package laol.parser;
import apfe.runtime.Token;

/**
 *
 * @author gburdell
 */
public interface ITokenCodes {
    public static final int STRING_LITERAL = 999;
    public static final int COMMENT = 998;
    public static final int ARRAY_OF = 996;
    public static final int REGEXP = 995;
    public static final int PREFIX_OP = 994;
    public static final int EOF = Token.EOF;
    public static final int NL = 500;
    
    //{ insert 'codes'
	public static final int K_ABSTRACT = 1 ;
	public static final int K_ALIAS = 2 ;
	public static final int K_BREAK = 3 ;
	public static final int K_CASE = 4 ;
	public static final int K_CATCH = 5 ;
	public static final int K_CLASS = 6 ;
	public static final int K_DEF = 7 ;
	public static final int K_ELSE = 8 ;
	public static final int K_ELSIF = 9 ;
	public static final int K_EXTENDS = 10 ;
	public static final int K_FALSE = 11 ;
	public static final int K_FINALLY = 12 ;
	public static final int K_FOR = 13 ;
	public static final int K_IF = 14 ;
	public static final int K_IMPLEMENTS = 15 ;
	public static final int K_IN = 16 ;
	public static final int K_MIXIN = 17 ;
	public static final int K_MODULE = 18 ;
	public static final int K_NEXT = 19 ;
	public static final int K_NEW = 20 ;
	public static final int K_NIL = 21 ;
	public static final int K_PRIVATE = 22 ;
	public static final int K_PROTECTED = 23 ;
	public static final int K_PUBLIC = 24 ;
	public static final int K_REQUIRE = 25 ;
	public static final int K_RETURN = 26 ;
	public static final int K_STATIC = 27 ;
	public static final int K_SUPER = 28 ;
	public static final int K_THIS = 29 ;
	public static final int K_THROW = 30 ;
	public static final int K_TRUE = 31 ;
	public static final int K_TRY = 32 ;
	public static final int K_UNLESS = 33 ;
	public static final int K_UNTIL = 34 ;
	public static final int K_VAL = 35 ;
	public static final int K_VAR = 36 ;
	public static final int K_WHEN = 37 ;
	public static final int K_WHILE = 38 ;
	public static final int K_FILE = 39 ;
	public static final int K_TARGET = 40 ;
	public static final int COLON = 41 ;
	public static final int COLON2 = 42 ;
	public static final int SEMI = 43 ;
	public static final int DOT = 44 ;
	public static final int DOT2 = 45 ;
	public static final int COMMA = 46 ;
	public static final int LT = 47 ;
	public static final int LTEQ = 48 ;
	public static final int LT2 = 49 ;
	public static final int LT2_EQ = 50 ;
	public static final int GT = 51 ;
	public static final int GTEQ = 52 ;
	public static final int GT2 = 53 ;
	public static final int GT2_EQ = 54 ;
	public static final int EQ = 55 ;
	public static final int EQ2 = 56 ;
	public static final int NEQ = 57 ;
	public static final int EXCL = 58 ;
	public static final int TILDE = 59 ;
	public static final int CARET = 60 ;
	public static final int AND = 61 ;
	public static final int AND2 = 62 ;
	public static final int AND_EQ = 63 ;
	public static final int OR = 64 ;
	public static final int OR2 = 65 ;
	public static final int OR_EQ = 66 ;
	public static final int STAR = 67 ;
	public static final int STAR_EQ = 68 ;
	public static final int MINUS = 69 ;
	public static final int MINUS2 = 70 ;
	public static final int MINUS_EQ = 71 ;
	public static final int ARROW = 72 ;
	public static final int PLUS = 73 ;
	public static final int PLUS2 = 74 ;
	public static final int PLUS_EQ = 75 ;
	public static final int QMARK = 76 ;
	public static final int DIV = 77 ;
	public static final int DIV_EQ = 78 ;
	public static final int LCURLY = 79 ;
	public static final int RCURLY = 80 ;
	public static final int LPAREN = 81 ;
	public static final int RPAREN = 82 ;
	public static final int LBRACK = 83 ;
	public static final int RBRACK = 84 ;
	public static final int PCNT = 85 ;
	public static final int PCNT_EQ = 86 ;
	public static final int IDENT = 87 ;
	public static final int UNSIGNED_NUMBER = 88 ;
	public static final int REAL_NUMBER = 89 ;
	public static final int BASED_NUMBER = 90 ;
    //} insert 'codes'
}
