package visitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class Arginfo {	
	public String str,toprint;
	public int where,ans,delay,flag,iscall,val,labelprint;
	public Vector vec;
	public LinkedList<String> list;
	public Arginfo(String str){
		this.str=str;
		this.where=-1;
		this.labelprint=1;
		this.ans=-1;
		this.flag=-1;
		this.delay=0;
		this.iscall=0;
		this.toprint="";
		this.val=-1;
		this.vec=new Vector();
	}
}

//1 CJUMP,HSTORE,HLOAD TEMP
//2 PRINT SIMPLEEXP

