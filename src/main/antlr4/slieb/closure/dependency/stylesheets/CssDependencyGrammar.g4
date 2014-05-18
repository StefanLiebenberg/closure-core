grammar CssDependencyGrammar;

stylesheet: ( expression | ignored | comment | anything)* EOF?;

expression: provide | require;

provide: '@provide' space+ namespace (space+)? (colon)?;
require: '@require' space+ namespace (space+)? (colon)?;

ignored: newline | space;
space: (' ' | '\t');
newline: ('\n' | '\r' )+;
colon: ';' ;

namespace: ('\'' NAME '\'') | ('"' NAME '"') | NAME;
anything: (.)+?;

comment: comment_line | comment_multi_line;
comment_line:  '//' (~NEWLINE)* (NEWLINE)?;
comment_multi_line: COMMENT_BLOCK_START (~COMMENT_BLOCK_END)* COMMENT_BLOCK_END;

NEWLINE: [\r\n]+;
COMMENT_BLOCK_START: '/*' ;
COMMENT_BLOCK_END: '*/' ;

NAME: ('a'..'z' | '-' | 'A'..'Z' ) +;
