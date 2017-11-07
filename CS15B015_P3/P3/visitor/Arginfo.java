package visitor;

import java.util.HashMap;
import java.util.Vector;

public class Arginfo {
	
	public String str,str2;
	public String inhstr;
	public HashMap<String,Table> symboltable;
	public Vector<String> vecstr;
	public int type;
	
	public String classname;
	public String methodname;
	public int isreq;
	public int ispri;
	public Arginfo(String s){
		
		this.symboltable=new HashMap<String,Table>();
		this.str="";
		this.vecstr=new Vector<String>();
		
		classname=s;
		methodname=new String("");
		isreq=0;
		ispri=0;
	}
	public Arginfo(){
		
		this.symboltable=new HashMap<String,Table>();
		this.str="";
		this.str2="";
		this.inhstr="";
		this.vecstr=new Vector<String>();
		
		classname=new String("");
		methodname=new String("");
		isreq=0;
	}
}
