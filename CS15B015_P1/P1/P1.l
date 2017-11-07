%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "P1.tab.h"
%}

%%

[\t \n] ;
[(] { 
	//printf("lpar found\n"); exit(0);
	yylval.strval=strdup(yytext);
	return LPAR; } 
[)] {
	//printf("%s\n",yytext); 
	yylval.strval=strdup(yytext); return RPAR; } 
"class" {
	//printf("%s\n",yytext);
 yylval.strval=strdup(yytext); return CLASS; } //
[{] { yylval.strval=strdup(yytext); return LFLOWER; } 
[}] { yylval.strval=strdup(yytext); return RFLOWER; } 
"public" { yylval.strval=strdup(yytext); return PUBLIC; } //
"static" { yylval.strval=strdup(yytext); return STATIC; } //
"void" { yylval.strval=strdup(yytext); return VOID; } //
"main" { yylval.strval=strdup(yytext); return MAIN; } //
"String" { yylval.strval=strdup(yytext); return STRING; } //
[\[] { yylval.strval=strdup(yytext); return LSQUARE; } 
[\]] { yylval.strval=strdup(yytext); return RSQUARE; } 
"System.out.println" { yylval.strval=strdup(yytext); return PRINT; } //
"\;" { yylval.strval=strdup(yytext); return SEMICOLON; } 
"extends" { yylval.strval=strdup(yytext); return EXTENDS; } //
"\," { 
	//printf("comma found\n");
	yylval.strval=strdup(yytext);
	return COMMA; } 
"int" { yylval.strval=strdup(yytext); return INT; } //
"boolean" { yylval.strval=strdup(yytext); return BOOLEAN; } //
"\=" { 
	//printf("eq found\n");
	yylval.strval=strdup(yytext);
	return EQUAL; } 
"if" { yylval.strval=strdup(yytext); return IF; } //
"else" { yylval.strval=strdup(yytext); return ELSE; } //
"while" { yylval.strval=strdup(yytext); return WHILE; } //
"&&" { yylval.strval=strdup(yytext); return AND; } //
"||" { yylval.strval=strdup(yytext); return OR; } //
"!=" { yylval.strval=strdup(yytext); return NOTEQUAL; }
"<=" { yylval.strval=strdup(yytext); return LESSEQUAL; }
[+] { //printf("plus found\n"); 
	yylval.strval=strdup(yytext);
	return PLUS; } 
[-] { yylval.strval=strdup(yytext); return MINUS; } 
[*] { yylval.strval=strdup(yytext); return STAR; } 
[/] { yylval.strval=strdup(yytext); return DIV; } //
[.] { //printf("lexdot found\n");
		yylval.strval=strdup(yytext);
					 return DOT; }
"length" { yylval.strval=strdup(yytext); return LENGTH; } //
"true" { yylval.strval=strdup(yytext); return TRUE; } //
"false" { yylval.strval=strdup(yytext); return FALSE; } //
"this" { yylval.strval=strdup(yytext); return THIS; } //
"new" { yylval.strval=strdup(yytext); return NEW; } //
[!] { 
	yylval.strval=strdup(yytext);
	//printf("not found\n"); exit(0);
	return NOT;
	 } //
"#define" { yylval.strval=strdup(yytext); return DEFINE; } //
"return" { yylval.strval=strdup(yytext); return RETURN; } //
[0-9]* { yylval.strval=strdup(yytext); 
	// printf("%s\n",yytext);
	//printf("%s num found\n",yytext); 
	//exit(0);
	return NUM; }
[a-zA-Z_][a-zA-Z0-9_]* { yylval.strval=strdup(yytext);
	//printf("%s\n var found",yytext);
	// fflush(stdout);
	//	exit(0);
 	return VAR; }
\/\/.*\n {
	//printf("%s\n comment found",yytext);
	//exit(0);
}
(\/\*(.|\n)*\*\/) ;


%%

void yyerror(char *s){
  printf("// Failed to parse macrojava code.");
}
