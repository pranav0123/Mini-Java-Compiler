package visitor;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class Table {
	public HashMap<String,String> varmap;
	public HashMap<String,Vector<String> > funcdec;
	public HashMap<String,HashMap<String,String> > methodvars;
	public String parent;
	public Table(){
		this.varmap=new HashMap<String,String>();
		this.funcdec=new HashMap<String,Vector<String> >();
		this.methodvars=new HashMap<String,HashMap<String,String> >();
		this.parent=null;
	}
}
