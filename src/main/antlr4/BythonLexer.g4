lexer grammar BythonLexer;

DEF: 'def';
LAMBDA: 'lambda';
RETURN: 'return';
FOR: 'for';
WHILE: 'while';
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
FROM: 'from';
IMPORT: 'import';
AS: 'as';
ASSIGN : '=';
SEMI: ';';
LPAR: '(' ;
RPAR: ')' ;
LBRACK: '[';
RBRACK: ']';
LBRACE: '{';
RBRACE: '}';
COMMA: ',';
COLON: ':';

CLASS: 'class';
SELF: 'self';

DOT: '.';
STAR: '*';
SLASH: '/';
PLUS: '+';
MINUS: '-';

PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
STAR_ASSIGN: '*=';
SLASH_ASSIGN: '/=';


CallableLiteral
    : STRING_LITERAL
    | TRUE
    | FALSE
    | NONE
    ;

NUMBER_LITERAL
    : INTEGER_LITERAL
    | DOUBLE_LITERAL
    ;

EQUAL: '==';
NOTEQUAL: '!=';
LESS: '<';
GREATER: '>';
LESSEQUAL: '<=';
GREATEREQUAL: '>=';
AND: 'and';
OR: 'or';
NOT : 'not';
IN: 'in';
IS: 'is';
UNION: '|';
TRUE: 'True';
FALSE: 'False';
NONE: 'None';
BREAK: 'break';
CONTINUE: 'continue';
PASS: 'pass';
TRY: 'try';
EXCEPT: 'except';
FINALLY: 'finally';

ID
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

INTEGER_LITERAL
    : '-'? [0-9]+
    ;

DOUBLE_LITERAL
    : '-'? [0-9]+'.'[0-9]+
    ;

STRING_LITERAL
    : '"' .*? '"'
    | '\'' .*? '\''
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

COMMENT
    : '//' ~[\r\n]* -> skip
    ;
