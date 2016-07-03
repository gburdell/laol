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

/**
 *
 * @author gburdell
 */
public interface ITokenCodes {
    public static final int STRING_LITERAL = 999;
    public static final int COMMENT = 998;
    public static final int EOL = 997;
    
    //{ insert 'codes'
	public static final int ABSTRACT_K = 1 ;
	public static final int ALIAS_K = 2 ;
	public static final int BREAK_K = 3 ;
	public static final int CASE_K = 4 ;
	public static final int CATCH_K = 5 ;
	public static final int CLASS_K = 6 ;
	public static final int DEF_K = 7 ;
	public static final int ELSE_K = 8 ;
	public static final int ELSIF_K = 9 ;
	public static final int EXTENDS_K = 10 ;
	public static final int FALSE_K = 11 ;
	public static final int FINALLY_K = 12 ;
	public static final int FOR_K = 13 ;
	public static final int IF_K = 14 ;
	public static final int IMPLEMENTS_K = 15 ;
	public static final int IN_K = 16 ;
	public static final int MIXIN_K = 17 ;
	public static final int MODULE_K = 18 ;
	public static final int NEXT_K = 19 ;
	public static final int NEW_K = 20 ;
	public static final int NIL_K = 21 ;
	public static final int PRIVATE_K = 22 ;
	public static final int PROTECTED_K = 23 ;
	public static final int PUBLIC_K = 24 ;
	public static final int REQUIRE_K = 25 ;
	public static final int RETURN_K = 26 ;
	public static final int STATIC_K = 27 ;
	public static final int SUPER_K = 28 ;
	public static final int THIS_K = 29 ;
	public static final int THROW_K = 30 ;
	public static final int TRUE_K = 31 ;
	public static final int TRY_K = 32 ;
	public static final int UNLESS_K = 33 ;
	public static final int UNTIL_K = 34 ;
	public static final int VAL_K = 35 ;
	public static final int VAR_K = 36 ;
	public static final int WHEN_K = 37 ;
	public static final int WHILE_K = 38 ;
	public static final int FILE_K = 39 ;
	public static final int TARGET_K = 40 ;
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
