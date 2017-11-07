package visitor;

import java.util.HashMap;
import java.util.Vector;

public class Retinfo {
	public String str;
	public HashMap<String,Table> symboltable;
	public Vector<String> vecstr;
	public int type;
	public Retinfo(){
		this.symboltable=new HashMap<String,Table>();
		this.str="";
		this.vecstr=new Vector<String>();
	}
}
