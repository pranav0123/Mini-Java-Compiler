package visitor;

import java.util.HashMap;
import java.util.Vector;

public class Arginfo {
	
	public String str;
	public HashMap<String,Table> symboltable;
	public Vector<String> vecstr;
	public int type;
	
	public String classname;
	public String methodname;
	public int isreq;
	public Arginfo(String s){
		
		this.symboltable=new HashMap<String,Table>();
		this.str="";
		this.vecstr=new Vector<String>();
		
		classname=s;
		methodname=new String("");
		isreq=0;
	}
	public Arginfo(){
		
		this.symboltable=new HashMap<String,Table>();
		this.str="";
		this.vecstr=new Vector<String>();
		
		classname=new String("");
		methodname=new String("");
		isreq=0;
	}
}
