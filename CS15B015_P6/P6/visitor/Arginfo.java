package visitor;

public class Arginfo {
	public String str;
	public String regname;
	public String spilledarg;
	public int a,b,c;
	public String opstr;
	public int simpleexptype;
	public String simpleexpstring; 
	public String movreg;
	public int isexp;
	public int isbinop;
	public int omg;
	public Arginfo(String str){
		this.str=str;
		this.isexp=0;
		this.omg=1;
		this.isbinop=0;
	}
}
