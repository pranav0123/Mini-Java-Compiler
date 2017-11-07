package visitor;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class Table {
	public HashMap<String,String> varmap;
	public HashMap<String,Vector<String> > funcdec;
	public HashMap<String,Vector<String> > funcdec2;
	public HashMap<String,HashMap<String,String> > methodvars;
	
	public HashMap<String,Integer> varnum;
	public HashMap<String,Integer> methodnum;
	
	public String parent;
	public Table(){
		this.varmap=new HashMap<String,String>();
		this.funcdec=new HashMap<String,Vector<String> >();
		this.funcdec2=new HashMap<String,Vector<String> >();
		this.methodvars=new HashMap<String,HashMap<String,String> >();
		this.parent=null;
		this.varnum=new HashMap<String,Integer>();
		this.methodnum=new HashMap<String,Integer>();
	}
}
