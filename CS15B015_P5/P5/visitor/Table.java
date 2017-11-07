package visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Table {
	public HashMap<String,Integer> prefunc;//stores the label line number pairs
	public HashMap<Integer,Set<Integer> > use,def,in,out,succ;//stores the instruction line no, temporaries.
	public int numargs,numstackslots,nummaxcallargs;
	public HashMap<Integer,Integer> firstout,lastin;
	public HashMap<Integer,String> registerloc;
	public HashMap<Integer,Integer> stackloc;
	public Vector<myclass> intervals;
	public int isspill;
	
	public Table(){
		this.prefunc=new HashMap<String,Integer>();
		this.out=new HashMap<Integer,Set<Integer> >();
		this.in=new HashMap<Integer,Set<Integer> >();
		this.use=new HashMap<Integer,Set<Integer> >();
		this.def=new HashMap<Integer,Set<Integer> >();
		this.succ=new HashMap<Integer,Set<Integer> >();
		this.numargs=0;
		this.firstout=new HashMap<Integer,Integer>();
		this.lastin=new HashMap<Integer,Integer>();
		this.registerloc=new HashMap<Integer,String>();
		this.stackloc=new HashMap<Integer,Integer>();
		this.intervals=new Vector<myclass>();
		this.nummaxcallargs=0;
		this.isspill=0;
	}
}