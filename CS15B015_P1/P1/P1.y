%{
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <limits.h>	
#include <string.h>
extern int yylex();
extern void yyerror(char*s);

int sizeofstr(char *str){
	if(str==NULL) return 0;
	else return sizeof(str);
}

char *concat2strings(char * str1,char *str2){
	if(str2==NULL) return str1;
	if(str1==NULL) return str2;
	char *str=(char *)malloc(sizeofstr(str1)+sizeofstr(str2)+20000);
	strcpy(str,str1);
	strcat(str,str2);
	free(str1); free(str2);
	return str;
}
char *concat3strings(char *str1,char *str2,char *str3){
	char *str=(char *)malloc(sizeofstr(str1)+sizeofstr(str2)+sizeofstr(str3)+20000);
 	strcpy(str,str1);
 	strcat(str,str2);
 	strcat(str,str3);
 	free(str1); free(str2); free(str3);
	//printf("%s %s %s\n",str1,str2,str3);
	// char *str=concat2strings(str1,str2);
	// char *ans=concat2strings(str,str3);
	return str;
}
char *initialize(){
	char *ans=(char *)malloc(10000*sizeof(char));
	strcpy(ans,"");
	return ans;
}
char *assign(char *str){
	char *ans=(char *)malloc(10000*sizeof(char));
	strcpy(ans,str);
	return ans;
}
struct Var{
	char *lhs;
	char *rhs;
	char **args;
	int numargs;
	bool isst;
};

typedef struct Var var;

var arr[1000];
int pre=0;

bool isvalidchar(char ch){
	if(('0'<=ch&&ch<='9')||('a'<=ch&&ch<='z')||('A'<=ch&&ch<='Z')||(ch=='_')) return true;
	else return false;
}

void terminatepr(){
	printf("// Failed to parse macrojava code.");
	exit(0);
}

void assignmacro(char *from,char *to,char *argstr,bool isstment){

	for(int i=0;i<pre;i++){
		if(strcmp(from,arr[i].lhs)==0) terminatepr();
	}
	arr[pre].lhs=malloc(1000*sizeof(char));
	arr[pre].rhs=malloc(1000*sizeof(char));
	strcpy(arr[pre].lhs,from);
	strcpy(arr[pre].rhs,to);
	arr[pre].isst=isstment;
	if(argstr==NULL||strlen(argstr)==0) arr[pre].numargs=0;
	else{
		arr[pre].numargs=1;
		for(int i=0;argstr[i]!='\0';i++){
			if(argstr[i]==',') arr[pre].numargs++;
		}	
	}

	if(arr[pre].numargs==0) arr[pre].args=NULL;
	else{
		arr[pre].args=(char **)malloc(arr[pre].numargs*sizeof(char *));
		int i,j=0,sz=0;
		for(i=0;i<arr[pre].numargs;i++){
			arr[pre].args[i]=(char *)malloc(1000*sizeof(char));
		}
		for(i=0;argstr[i]!='\0';i++){
			if(argstr[i]==','){
				arr[pre].args[j][sz]='\0';
				j++;
				sz=0;
			}
			else{
				arr[pre].args[j][sz]=argstr[i];
				sz++;
			}
		}
		arr[pre].args[j][sz]='\0';
	}
	pre++;
}

char *findidx(char *prestr,char *explist,bool isstment){
	int num=0;
	if(explist==NULL||strlen(explist)==0) num=0;
	else{
		num=1;
		for(int i=0;explist[i]!='\0';i++){
			if(explist[i]==',') num++;
		}	
	}
	char **exps;
	int i,j=0,sz=0;
	if(num==0) exps=NULL;
	else{
		exps=(char **)malloc(num*sizeof(char *));
		
		for(i=0;i<num;i++){
			exps[i]=(char *)malloc(1000*sizeof(char));
		}
		for(i=0;explist[i]!='\0';i++){
			if(explist[i]==','){
				exps[j][sz]='\0';
				j++;
				sz=0;
			}
			else{
				exps[j][sz]=explist[i];
				sz++;
			}
		}
		exps[j][sz]='\0';
	}
	j=0;

	for(i=0;i<pre;i++){
		if(strcmp(arr[i].lhs,prestr)==0){
			if((arr[i].isst==isstment)&&(arr[i].numargs==num)) break;
		}
	}
	if(i==pre){
		terminatepr();
	}
	int found=i;
	// for(int i=0;i<num;i++){
	// 	printf("%s\n",exps[i]);
	// }
	char *ans=(char *)malloc(1000*sizeof(char));
	char *rhsexp=arr[found].rhs;
	for(i=0;rhsexp[i]!='\0';i++){
		if(isvalidchar(rhsexp[i])){
			char *temp=(char *)malloc(1000*sizeof(char));
			int tempsz=0;
			while(isvalidchar(rhsexp[i])){
				temp[tempsz++]=rhsexp[i];
				i++;
			}
			i--;
			temp[tempsz]='\0';
			int k;
			for(k=0;k<arr[found].numargs;k++){
				if(strcmp(arr[found].args[k],temp)==0){
					break;
				}
			}
			int ii;
			if(k==arr[found].numargs){
				for(ii=0;ii<tempsz;ii++){
					ans[j++]=temp[ii];
				}
			}
			else{
				for(ii=0;ii<strlen(exps[k]);ii++){
					ans[j++]=(exps[k])[ii];
				}	
			}
		}
		else{
			ans[j++]=rhsexp[i];
		}
	}
	ans[j]='\0';
	return ans;
}

%}

%union {
    char * strval;
}

%type <strval> MAINCLASS
%type <strval> TYPEDEC
%type <strval> METHODDEC
%type <strval> TYPE
%type <strval> STATEMENT
%type <strval> EXPRESSION
%type <strval> PRIMARYEXP
%type <strval> MACRODEF
%type <strval> MACRODEFSTATEMENT
%type <strval> STATEMENTSTAR
%type <strval> MACRODEFEXP
%type <strval> MACRODEFSTAR
%type <strval> TYPEDECSTAR
%type <strval> EXPSUB
%type <strval> COMMAEXPSTAR
%type <strval> MACRODEFEXPSUB
%type <strval> COMMAVARSTAR
%type <strval> TYPEIDSCSTAR
%type <strval> METHODDECSTAR
%type <strval> METHODDECSUB1
%type <strval> METHODDECSUB2
%type <strval> COMMATYPEIDSTAR
%type <strval> TYPENUMSEMISTAR

%token <strval> NUM
%token <strval> VAR

%token <strval> LPAR RPAR CLASS LFLOWER RFLOWER PUBLIC STATIC VOID MAIN STRING LSQUARE
	   			RSQUARE PRINT SEMICOLON EXTENDS COMMA INT BOOLEAN EQUAL IF ELSE WHILE
	   			AND OR NOTEQUAL LESSEQUAL PLUS MINUS STAR DIV DOT LENGTH TRUE FALSE THIS
	   			NEW NOT DEFINE RETURN

%start GOAL

%%

GOAL : //MAINCLASS { printf("%s\n",$1); return 0;}
		//METHODDECSUB2 {return 0;}
		//STATEMENTSTAR {return 0;}
		//EXPRESSION {return 0;}
		MACRODEFSTAR MAINCLASS TYPEDECSTAR {
			printf("%s\n%s\n%s\n",$1,$2,$3); 
			// for(int i=0;i<pre;i++){
			// 	printf("%s %s %d\n",arr[i].lhs,arr[i].rhs,arr[i].numargs);
			// 	for(int j=0;j<arr[i].numargs;j++){
			// 		printf("%s\n",arr[i].args[j]);
			// 	}
			// }
			return 0;}
MACRODEFSTAR : {//printf("noo\n");

				$$=assign("\n");}
			   | MACRODEF MACRODEFSTAR {
			   	$$=concat2strings($1,assign("\n"));
			   	$$=concat2strings($$,$2);
			   }
TYPEDECSTAR  : {$$=assign("\n");}
			   | TYPEDEC TYPEDECSTAR {
			   	$$=concat2strings($1,assign("\n"));
			   	$$=concat2strings($$,assign("\n"));
			    $$=concat2strings($$,$2);
			}	

MAINCLASS : CLASS VAR LFLOWER PUBLIC STATIC VOID MAIN LPAR STRING LSQUARE RSQUARE VAR RPAR  
				LFLOWER	PRINT LPAR EXPRESSION RPAR SEMICOLON RFLOWER RFLOWER{

					//printf("%s\n",$2); 
					//exit(0);
					$$=concat2strings($1,assign(" "));
					//exit(0);
					$$=concat2strings($$,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$4);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$5);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$6);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$7);
					$$=concat2strings($$,$8);
					$$=concat2strings($$,$9);
					$$=concat2strings($$,$10);
					$$=concat2strings($$,$11);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$12);
					$$=concat2strings($$,$13);
					$$=concat2strings($$,$14);
					$$=concat2strings($$,assign("\n\t\t"));
					$$=concat2strings($$,$15);
					$$=concat2strings($$,$16);
					$$=concat2strings($$,$17);
					$$=concat2strings($$,$18);
					$$=concat2strings($$,$19);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$20);
					$$=concat2strings($$,assign("\n"));
					$$=concat2strings($$,$21);
					//exit(0);

				}

TYPEDEC : CLASS VAR LFLOWER TYPEIDSCSTAR METHODDECSTAR RFLOWER
			{
				//exit(0);
				$$=concat2strings($1,assign(" "));
				$$=concat2strings($$,$2);
				$$=concat2strings($$,$3);
				$$=concat2strings($$,assign("\n\t"));
				$$=concat2strings($$,$4);
				$$=concat2strings($$,assign("\n\t"));
				$$=concat2strings($$,$5);
				$$=concat2strings($$,assign(" "));
				$$=concat2strings($$,$6);
			}
			| CLASS VAR EXTENDS VAR LFLOWER TYPEIDSCSTAR METHODDECSTAR RFLOWER
			{
				$$=concat2strings($1,assign(" "));
				$$=concat2strings($$,$2);
				$$=concat2strings($$,assign(" "));
				$$=concat2strings($$,$3);
				$$=concat2strings($$,assign(" "));
				$$=concat2strings($$,$4);
				$$=concat2strings($$,$5);
				$$=concat2strings($$,assign("\n\t"));
				$$=concat2strings($$,$6);
				$$=concat2strings($$,assign("\n"));
				$$=concat2strings($$,$7);
				$$=concat2strings($$,assign("\n"));
				$$=concat2strings($$,$8);
			}

TYPEIDSCSTAR : { $$=initialize();}
			 | TYPE VAR SEMICOLON TYPEIDSCSTAR {
			 	//exit(0);
			 	$$=concat2strings($1,assign(" "));

				$$=concat2strings($$,$2);
				$$=concat2strings($$,$3);
				$$=concat2strings($$,assign("\n\t"));
				$$=concat2strings($$,$4);

			 	}

METHODDECSTAR : {$$=initialize();}
				| METHODDEC METHODDECSTAR {

					$$=concat2strings($1,assign("\n\t"));
					$$=concat2strings($$,$2);
				;}

METHODDEC : PUBLIC TYPE VAR LPAR METHODDECSUB1 RPAR LFLOWER METHODDECSUB2 RFLOWER{

					$$=concat2strings($1,assign(" "));
					$$=concat2strings($$,$2);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					$$=concat2strings($$,$5);
					$$=concat2strings($$,$6);
					$$=concat2strings($$,$7);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$8);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$9);
					//printf("%s\n",$$); exit(0);
				}

METHODDECSUB1 : {$$=initialize();}
				| TYPE VAR COMMATYPEIDSTAR{
					$$=concat2strings($1,assign(" "));
					$$=concat2strings($$,$2);
					$$=concat2strings($$,$3);
				}

COMMATYPEIDSTAR : {$$=initialize();}
				  | COMMA TYPE VAR COMMATYPEIDSTAR{
				  	$$=concat2strings($1,$2);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
				  }

METHODDECSUB2 : TYPENUMSEMISTAR STATEMENTSTAR RETURN EXPRESSION SEMICOLON{
					$$=concat2strings($1,assign("\n\t"));
					$$=concat2strings($$,$2);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$3);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$4);
					$$=concat2strings($$,$5);
				  }

TYPENUMSEMISTAR : {$$=initialize();}
				  |TYPENUMSEMISTAR TYPE VAR SEMICOLON {
				  	$$=concat2strings($1,assign("\n\t"));
					$$=concat2strings($$,$2);
					$$=concat2strings($$,assign(" "));
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);	
				  }

TYPE : INT LSQUARE RSQUARE{
				$$=concat2strings($1,$2);
				$$=concat2strings($$,$3);
			}
		| BOOLEAN{
			$$=$1;
		}
		| INT{
			$$=$1;
		}
		| VAR{
			$$=$1;
		}

STATEMENT : LFLOWER STATEMENTSTAR RFLOWER {
				//printf("ekj\n"); exit(0);
				$$=concat2strings($1,assign("\n\t"));
				$$=concat2strings($$,$2);
				$$=concat2strings($$,assign("\n\t"));
				$$=concat2strings($$,$3);
			}
			| PRINT LPAR EXPRESSION RPAR SEMICOLON {
					//printf("enkjf\n"); exit(0);
					$$=concat2strings($1,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					$$=concat2strings($$,$5);
				}
			| VAR EQUAL EXPRESSION SEMICOLON {
				//printf("%s %s %s %s\n",$1,$2,$3,$4);
					$$=concat2strings($1,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
			}
			| VAR LSQUARE EXPRESSION RSQUARE EQUAL EXPRESSION SEMICOLON
					{
						$$=concat2strings($1,$2);
						$$=concat2strings($$,$3);
						$$=concat2strings($$,$4);
						$$=concat2strings($$,$5);
						$$=concat2strings($$,$6);
						$$=concat2strings($$,$7);
					}
			| IF LPAR EXPRESSION RPAR STATEMENT{
					$$=concat2strings($1,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$5);
			}
			| IF LPAR EXPRESSION RPAR STATEMENT ELSE STATEMENT{
					$$=concat2strings($1,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$5);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$6);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$7);
			}
			| WHILE LPAR EXPRESSION RPAR STATEMENT{
					$$=concat2strings($1,$2);
					$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					$$=concat2strings($$,assign("\n\t"));
					$$=concat2strings($$,$5);
			}
			| VAR LPAR EXPSUB RPAR SEMICOLON{
				//printf("%s %s %s %s\n",$1,$2,$3,$4);
				//exit(0);
					$$=concat2strings($1,$2);
					//$$=concat2strings($$,$3);
					$$=concat2strings($$,$4);
					//while(strcmp($$,findidx(assign($$)))!=0){
						$$=findidx(assign($$),$3,true);
						//printf("%sfjnviboheiurhe4oiuh\n",$$);
					//}
					//$$=concat3strings(assign("("),$$,assign(")"));
					//printf("%s\n",$$);
					//$$=concat2strings($$,$5);
			}


STATEMENTSTAR : {$$=initialize();}
  			    | STATEMENT STATEMENTSTAR {
  			    	//printf("%s\n",$1);
  			    	//exit(0);
  			    	$$=concat2strings($1,assign("\n\t"));
					$$=concat2strings($$,$2);
  			    }

EXPSUB : 	{
				//printf("nio"); exit(0);
				$$=initialize();
			}
			| EXPRESSION COMMAEXPSTAR {
				$$=concat2strings($1,$2);
		 		//exit(0);
		 	};

EXPRESSION : PRIMARYEXP AND PRIMARYEXP {$$=concat3strings($1,$2,$3);
					//exit(0);
				}
			 | PRIMARYEXP OR PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 		}
			 | PRIMARYEXP NOTEQUAL PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 		}
			 | PRIMARYEXP LESSEQUAL PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 }
			 | PRIMARYEXP PLUS PRIMARYEXP {$$=concat3strings($1,$2,$3);	
			 			//exit(0);
			 }
			 | PRIMARYEXP MINUS PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 			//printf("%s-%s\n",$1,$3);
			 		}
			 | PRIMARYEXP STAR PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 }
			 | PRIMARYEXP DIV PRIMARYEXP {$$=concat3strings($1,$2,$3);
			 			//exit(0);
			 }
			 | PRIMARYEXP LSQUARE PRIMARYEXP RSQUARE {
			 	$$=concat3strings($1,$2,$3);
			 	$$=concat2strings($$,$4);
			 	//printf("%s\n",$$);fflush(stdout);
			 	//exit(0);
			 }
			 | PRIMARYEXP DOT LENGTH {
			 		$$=concat3strings($1,$2,$3);
			 		
			 			//exit(0);
			 }
			 | PRIMARYEXP //SHIFT REDUCE WARNING 
			 {
			 	$$=$1;
			 	//printf("%s\n",$$);
			 	//exit(0);
			 	
			 	// exit(0);
			 	// if((++cnt)==2){
			 	// 	printf("%s\n",$$);fflush(stdout);
			 	// 	exit(0);
			 	// }
			 	//printf("%s\n",$$);fflush(stdout);
			}
			/*| PRIMARYEXP NUM{
				// $$=concat2strings($1,$2);
				// printf("sbjs\n" );
				//exit(0);
			}*/
			 | PRIMARYEXP DOT VAR LPAR EXPSUB RPAR {
			 	//printf("%s\n %s\n %s\n %s\n %s\n %s\n",$1,$2,$3,$4,$5,$6);
			 	$$=concat3strings($1,$2,$3);
			 	$$=concat3strings($$,$4,$5);
			 	$$=concat2strings($$,$6);
			 	//printf("%s\n",$1);
			 	//exit(0);
			 } 
			 | VAR LPAR EXPSUB RPAR {
			 	//if($1==NULL){printf("ndci");exit(0);} 
			 	//printf("%s %s %s %s\n",$1,$2,$3,$4); exit(0);
			 	//printf("%s\n",$3);
			 	//exit(0);
			 	$$=concat2strings($1,$2);
			 	//$$=concat2strings($$,$3);
			 	$$=concat2strings($$,$4);
			 	//while(strcmp($$,findidx(assign($$)))!=0){
			 		//$$=findidx(assign($$));
			 		$$=findidx(assign($$),$3,false);
			 		//printf("%sfjnviboheiurhe4oiuh\n",$$);
			 	//}
			 	//$$=concat3strings(assign("("),$$,assign(")"));
			 	//exit(0);
			 	//printf("%s\n",$$);
			 }

PRIMARYEXP : NUM {
				$$=$1;
				//printf("%s\n",$$);
			 	//fflush(stdout)
			 	//exit(0);
			 	//exit(0);
			 }
			 | TRUE{$$=$1;}
			 | FALSE{$$=$1;}
			 | VAR {
			 	$$=$1;
			 	//printf("%s\n",$$);
			 	//fflush(stdout);
			 	//exit(0);
			 }
			 | THIS{$$=$1;}
			 | NEW INT LSQUARE EXPRESSION RSQUARE{
			 	$$=concat3strings($1,assign(" "),$2);
			 	$$=concat2strings($$,$3);
			 	$$=concat3strings($$,$4,$5);
			 	//printf("%s %s %s %s %s\n",$1,$2,$3,$4,$5);
			 	//printf("%s\n",$$);
			 	//exit(0);
			 }
			 | NEW VAR LPAR RPAR{
			 	$$=concat3strings($1,assign(" "),$2);
			 	//printf("%s\n",$$);
			 	//printf("%s %s\n",$3,$4);
			 	$$=concat3strings($$,$3,$4);
			 	//printf("%s\n",$$);
			 	//exit(0);
			 }
			 | NOT EXPRESSION{$$=concat2strings($1,$2);}
			 | LPAR EXPRESSION RPAR{$$=concat3strings($1,$2,$3);}


COMMAEXPSTAR : 	{$$=initialize();};
				|COMMA EXPRESSION COMMAEXPSTAR{
					$$=concat3strings($1,$2,$3);
			   	//exit(0);
			   };
			 
MACRODEF : MACRODEFEXP{$$=initialize();}
 		   | MACRODEFSTATEMENT{$$=initialize();}

MACRODEFSTATEMENT : DEFINE VAR LPAR MACRODEFEXPSUB RPAR LFLOWER STATEMENTSTAR RFLOWER //infinite loop
					{
						// $$=concat2strings($1,$2);
						// $$=concat2strings($$,$3);
						// $$=concat2strings($$,$4);
						// $$=concat2strings($$,$5);
						// $$=concat2strings($$,$6);
						// $$=concat2strings($$,$4);
						// $$=concat2strings($$,$5);
						char *temp=concat3strings($2,$3,$5);
						assignmacro(temp,concat3strings($6,$7,$8),$4,true);	//true for statement
					}
MACRODEFEXP : DEFINE VAR LPAR MACRODEFEXPSUB RPAR  LPAR EXPRESSION RPAR //infinite loop
				{
					$$=initialize();
					char *temp=concat3strings($2,$3,$5);
					assignmacro(temp,concat3strings($6,$7,$8),$4,false); //false for expression
					//assignmacro(temp,$7,0);
				}
MACRODEFEXPSUB : {$$=initialize();}
 				 |  VAR COMMAVARSTAR {$$=concat2strings($1,$2);}

COMMAVARSTAR : {$$=initialize();}
 			   | COMMA VAR COMMAVARSTAR {$$=concat3strings($1,$2,$3);}


%%

int yywrap(){return 1;}
int main(){
  yyparse();
  return 0;
}


 
