package visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Arginfo {
	public HashMap<String,Table > labelno;
	public String labelname;
	public Integer tempnumber;
	public Set<Integer> exptemps;
	public Set<Integer> temptemps;
	public int isstar,jumpint,premaxargs;
	public String funcname;
	public String toprint,expregnum;
	public int isreturn,movetempno;
	public int flag,rgh,tostore;
	public Vector<Integer> store;
	public int lfg;
	
	public String str;
	public int isprint;
	
	public Arginfo(){
		this.labelno=new HashMap<String,Table >();
		this.labelname="";
		
		this.str="";
		this.tempnumber=-1;
		this.exptemps=new HashSet<Integer>();
		this.temptemps=new HashSet<Integer>();
		this.isstar=0;
		this.funcname="";
		this.premaxargs=0;
		this.isprint=0;
		this.isreturn=0;
		this.flag=0;
		this.rgh=1;
		this.tostore=0;
		this.store=new Vector<Integer>();
		this.lfg=0;
	}
}