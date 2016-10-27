grammar Laol;

/**
 *	NOTES:
 *	1) parameter lists 'm(a,b,...)' are specified when arguments are required.
 *	   If no arguments required, then 'm' is allowed, not 'm()'.
 */

@header {
	package laol.parser;
}

file: NL* contents NL* EOF ;

contents: require_statement* NL* file_item+ ;

require_statement: 'require' STRING eos ;

file_item
:   module_declaration
|   statement
;

module_item
:   var_decl_statement
|   assign_statement
|   class_declaration 
|   method_declaration
|   module_declaration
;

module_name: IDENT ;

module_declaration:
    'module' module_name '{' NL?
        module_item*
    '}' eos
;

class_name: IDENT ;

class_declaration:
    // Scala style
    access_modifier?
    'abstract'?
    'class' class_name
        method_param_decl?
        class_extends?
        '{' NL+
            class_body
        '}' eos
;

//1st statement is initializer
class_body: base_class_initializer? statement* ;

base_class_initializer: 'super' method_param_decl ; 

method_param_decl: '(' method_param_decl_list ')' ;

access_modifier: 'private' | 'protected' | 'public' ;

mutability: 'const' | 'var' ; 

method_param_decl_modifier: access_modifier? mutability? ;

method_param_decl_ele:
    //NOTE: only last in list can have STAR (marks as varargs)
    // AND (->) marks as function parameter
    method_param_decl_modifier
	(	'->' IDENT method_param_decl?
	|	'*'? IDENT method_param_decl_default?
	)
;

method_param_decl_default: '=' expression ;

method_param_decl_list: method_param_decl_ele (',' method_param_decl_ele)* ; 

class_extends
:   'extends' class_name ('implements' class_name_list)?
|	'implements' class_name_list
;

class_name_list: class_name (',' class_name)* ;

anonymous_function: '->' method_param_decl '{' method_body? '}' ;

method_declaration:
    access_modifier?
    ('abstract' | 'static')?
    'def' method_name (method_param_decl)?
        ('{' method_body '}')?
        //NOTE: abstract declaration if empty
		eos
;
    
method_name
:   method_name_op
|   identq
;

method_name_op
:   '[]='
|   '[]'
|   '()'
|   unary_op
|   binary_op
|   assignment_op
|   '++' | '--'     //postfix operator
|   '++()' | '--()' //prefix operator
;

method_body: statement ;

statement 
:   case_statement
|   if_statement
|   while_statement
|   until_statement
|   for_statement
|   block_statement
|   break_statement
|   next_statement
|   alias_statement
|   return_statement 
|   try_statement 
|   throw_statement
|   class_declaration
|   method_declaration 
|   mixin_statement
|   assign_statement
|   var_decl_statement
|   expression_statement
;

mixin_name: IDENT ('::' IDENT)* ;

mixin_statement: 'mixin' module_name (',' module_name)* eos ;

case_statement:
    'case' expression '{' NL?
        ('when' expression_list ':' NL? statement)*
        ('else' NL? statement)?
    '}' eos
;

if_statement:
    ('if' / 'unless') expression NL? statement
        ('elsif' expression NL? statement)*
        ('else' NL? statement)?
;

while_statement: 'while' expression NL? statement eos ;
until_statement: 'until' expression NL? statement eos ;
for_statement: 'for' IDENT 'in' enumerable_expression NL? statement eos ;
block_statement: '{' NL* statement* NL* '}' ;
break_statement: 'break' eos ;
next_statement:  'next' eos ;
alias_statement: 'alias' method_name method_name eos ;
return_statement: 'return' expression? eos ;
throw_statement: 'throw' expression eos ;
try_statement: 'try' NL? statement (NL? catch_statement)* (NL? finally_statement)? eos ;
catch_statement: 'catch' expression NL? statement eos ;
finally_statement: 'finally' NL? statement eos ;

assign_statement: assignment_lhs assignment_op assignment_rhs eos ;
var_decl_statement: var_decl eos ;
expression_statement: expression eos ;

primary_expression
:   'nil'
|   'new'
|   'file' | 'target'
|   'true' | 'false'
|   'this' | 'super'
|   STRING
|   SYMBOL
|	'(' expression ')'
|   here_doc
|   vm_name	//variable or method name
|   number
|   hash_primary
|   array_primary
|   regexp_primary
|	html_primary
|	sass_primary
;

postfix_expression
:   postfix_expression '[' array_select_expression ']' block?
|   postfix_expression '(' param_expression_list ')' block?
|   postfix_expression '.' postfix_expression
|   postfix_expression ('++' | '--') 
|   primary_expression block?
;

unary_expression 
:   unary_op unary_expression
|   postfix_expression
;

// STAR/STAR2 are splat operators (as in ruby)
unary_op: '~' | '!' | '+' | '-' | '*' | '**' | '++' | '--' | '&' ;

mult_expression
:   mult_expression mult_op unary_expression
|   unary_expression
;
	
mult_op : '*' | '|' | '%' ;

add_expression 
:   add_expression add_op mult_expression
|	mult_expression
;

add_op : '+' | '-' ;

shift_expression 
:   shift_expression shift_op add_expression
|   add_expression
;

shift_op : '<<' | '>>' ;

rel_expression
:	rel_expression rel_op shift_expression
|	shift_expression
;

rel_op : '<' | '<=' | '>' | '>=' ;

cmp_expression 
:   cmp_expression cmp_op rel_expression
|	rel_expression
;

cmp_op : '!=' | '==' | '=~' | '!~' ;

and_expression 
:   and_expression '&' cmp_expression
|	cmp_expression
;

xor_expression 
:   xor_expression '^' and_expression
|	and_expression
;

or_expression 
:   or_expression '|' xor_expression
|	xor_expression
;

land_expression 
:   land_expression '&&' or_expression
|	or_expression
;

lor_expression 
:   lor_expression '||' land_expression
|	land_expression
;

conditional_expression 
:   lor_expression '?' expression ':' expression
|	lor_expression
;

expression : conditional_expression ;

binary_op :
	mult_op
|	add_op
|	shift_op
|	rel_op
|	cmp_op
|	'&' | '|' | '^' | '&&' | '||'
;

assignment_op
:   '='
|   '<<='
|   '>>='
|   '&='
|   '|='
|   '^='
|   '*='
|   '-='
|   '+='
|   '/='
|   '%='
;

block: anonymous_function ;

expression_list: expression (',' expression)* ;

unnamed_param: expression_list ;

named_param_ele: IDENT ':' expression ;

// last: can be vararg
named_param: named_param_ele (',' named_param_ele)* (',' expression)? ;

// Any named params are at the end
param_expression_list
:   unnamed_param ',' named_param
|	named_param
|	unnamed_param
;

array_select_expression
:   expression '..' expression
|   expression_list
;

enumerable_expression: expression ;

var_decl: lhs_decl IDENT (',' IDENT)* eos ;

assignment_lhs: lhs_decl lhs_ref (',' lhs_ref)* ;

// NOTE: while ruby allows most statements as rhs; e.g.: "foo = if xxx; 12", 
// we will not.  Use method to do so.
assignment_rhs
:   expression_list
|   anonymous_function
;

lhs_decl: access_modifier? 'static'? mutability? ;

lhs_ref
:   lhs_ref '[' array_select_expression ']'
|   lhs_ref '(' param_expression_list ')'
|   lhs_ref '.' lhs_ref
|   lhs_ref ('++' | '--') 
|   vm_name
;

vm_name
:	(IDENT '::')* identq
|	'::' identq
;

identq: IDENT | IDENTQ ;

array_primary
:   '[' (expression_list)? ']'
    //array of (w) words or (s) symbols
|   ('%w{'|'%s{') IDENT* '}'
;

hash_primary: '{' (hash_key_value (',' hash_key_value)* )? '}' ;

hash_key: STRING | IDENT ;

hash_key_value: hash_key ':' expression ;

// optional modifier: a-z (overkill...)
regexp_primary: REGEXP ;

here_doc: '%h{}' ;  //TODO

html_primary: '%html{' NL* (html_code* | html_tag*) '}' ;

html_code:
	IDENT ('(' param_expression_list ')')? '{' NL*
		html_code_content*
	'}'
;

html_code_content
:	inline_eval
|	html_code
|	~'}'
;

html_tag:
	'<' IDENT html_attribute* '>'
		html_tag_content*
	('</' IDENT '>')?
;

html_tag_content
:	inline_eval
|	html_tag
|	~'}'
;

html_attribute: IDENT '=' STRING ;

sass_primary:
	'%sass{' NL*
		sass_content*
	'}'
;

sass_content
:	'{' (~'}' sass_content)* '}'
|	inline_eval
|	STRING
|	~'}'
;

inline_eval: '#{' NL* expression '}' ;

number
:	INTEGER
|	FLOAT
;

eos
:	';' NL*
|	NL
;

//
// Begin lexer
// (Note: 1st match wins)

SYMBOL: ':' IDENT ;

fragment
IDENT_CMN: [a-zA-Z_] [a-zA-Z0-9_]* ;

IDENTQ: IDENT_CMN '?' ;
IDENT:  IDENT_CMN ;

fragment
DIGITS: [0-9] [0-9_]* ;

FLOAT
:	DIGITS '.' DIGITS ([eE] ('-'|'+')? DIGITS)? 
|	DIGITS [eE] ('-'|'+')? DIGITS 
;

INTEGER: DIGITS ;

STRING
:   '"' ('\\'. | ~'"')* '"' 
|   '\'' ('\\'. | .)? '\''
;

REGEXP
:	'/' ('\\' . | ~'/')+ '/' [i]?
|   '%r{' ('\\' . | ~'}')+ '}' [i]?
;

WS: [ \t]+ -> skip ;

LINE_COMMENT:   '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT:  '/*' .*? '*/' -> skip ;

NL: '\r'? '\n' ;

