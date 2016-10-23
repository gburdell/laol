grammar Laol;

file: contents? EOF ;

contents: require_statement* file_item+ ;

require_statement: 'require' STRING EOS ;

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
    '}' EOS
;

class_name: IDENT ;

class_declaration:
    // Scala style
    access_modifier?
    'abstract'?
    'class' class_name
        method_param_decl?
        class_extends?
        '{' NL?
            class_body
        '}' EOS
;

//1st statement is initializer
class_body: base_class_initializer? statement* ;

base_class_initializer: 'super' method_param_decl ; 

method_param_decl: '(' method_param_decl_list? ')' ;

access_modifier: 'private' | 'protected' | 'public' ;

mutability: 'const' | 'var' ; 

method_param_decl_modifier: access_modifier? mutability? ;

method_param_decl_ele:
    //NOTE: only last in list can have STAR (marks as varargs)
    // AND (&) marks as function parameter
    method_param_decl_modifier ('&' | '*')? IDENT method_param_decl_default? ;

method_param_decl_default: '=' expression ;

method_param_decl_list: method_param_decl_ele (',' method_param_decl_ele)* ; 

class_extends
:   'extends' class_name ('implements' class_name_list)?
|	'implements' class_name_list
;

class_name_list: class_name (',' class_name)* ;

anonymous_function: '->' method_param_decl '{' method_body '}' ;

method_declaration:
    access_modifier?
    ('abstract' | 'static')?
    'def' method_name (method_param_decl)?
        ('{' method_body '}')?
        //NOTE: abstract declaration if empty
		EOS
;
    
method_name
:   IDENT? method_name_op
|   IDENT
;

method_name_op
:   '[]='
|   '[]'
|   '()'
|   unary_op
|   binary_op
|   '?'
|   '!'
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

mixin_statement: 'mixin' module_name (',' module_name)* EOS ;

case_statement:
    'case' expression '{' NL?
        ('when' expression_list ':' NL? statement)*
        ('else' NL? statement)?
    '}' EOS
;

if_statement:
    ('if' / 'unless') expression NL? statement
        ('elsif' expression NL? statement)*
        ('else' NL? statement)?
;

while_statement: 'while' expression NL? statement EOS ;
until_statement: 'until' expression NL? statement EOS ;
for_statement: 'for' IDENT 'in' enumerable_expression NL? statement EOS ;
block_statement: '{' NL* statement* NL* '}' ;
break_statement: 'break' EOS ;
next_statement:  'next' EOS ;
alias_statement: 'alias' method_name method_name EOS ;
return_statement: 'return' expression? EOS ;
throw_statement: 'throw' expression EOS ;
try_statement: 'try' NL? statement (NL? catch_statement)* (NL? finally_statement)? EOS ;
catch_statement: 'catch' expression NL? statement EOS ;
finally_statement: 'finally' NL? statement EOS ;

assign_statement: assignment_lhs assignment_op assignment_rhs EOS ;
var_decl_statement: var_decl EOS ;
expression_statement: expression EOS ;

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
|   variable_name
|   NUMBER
|   hash_primary
|   array_primary
|   regexp_primary
|	html_primary
|	sass_primary
;

postfix_expression
:   postfix_expression '[' array_select_expression ']' block?
|   postfix_expression '(' param_expression_list? ')' block?
|   postfix_expression dot_suffix block?
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
named_param: named_param_ele (',' named_param_ele)* (',' expression)* ;

// Any named params are at the end
param_expression_list
:   unnamed_param named_param
|	named_param
|	unnamed_param
;

array_select_expression
:   expression '..' expression
|   expression_list
;

enumerable_expression: expression ;

var_decl: lhs_decl IDENT (',' IDENT)* EOS ;

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
|   lhs_ref '(' param_expression_list? ')'
|   lhs_ref '.' IDENT
|   lhs_ref ('++' | '--') 
|   variable_name
;

variable_name: IDENT ('::' IDENT)* ;

dot_suffix: '.' ('nil' | 'new' | IDENT) ;

array_primary
:   '[' (expression_list)? ']'
    //array of (w) words or (s) symbols
|   ('%w{'|'%s{') IDENT* '}'
;

hash_primary: '{' (hash_key_value (',' hash_key_value)* )? '}' ;

hash_key: STRING | IDENT ;

hash_key_value: hash_key ':' expression ;

// optional modifier: a-z (overkill...)
regexp_primary
:   '%r{' ('\\' . | ~'}')+ '}' //TODO: [a-z]*
|   '/' ('\\' . | ~'/')+ '/'   //TODO: [a-z]*
;

here_doc: '%h{}' ;  //TODO
html_primary: '%html{}' ; //TODO
sass_primary:  '%sass{}' ;//TODO

// Begin lexer
IDENT:  [a-zA-Z_] [a-zA-Z0-9_]* ;

//TODO
NUMBER: [0-9] [0-9_]* ;

STRING
:   '"' ('\\'. | ~'"')* '"' 
|   '\'' ('\\'. | .)? '\''
;

EOS: (';' NL*) | NL+ ;

NL: '\r'? '\n' ;

WS: [ \t]+ -> skip ;

//TODO: put on channel(HIDDEN)?
COMMENT
:   LINE_COMMENT
|   BLOCK_COMMENT
;

LINE_COMMENT:   '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT:  '/*' .*? '*/' -> skip ;
