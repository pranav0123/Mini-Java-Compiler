import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import syntaxtree.*;
import visitor.*;
// Sample Main.java
public class P3 {
	
	public static Arginfo finaltable;
	public static void main(String [] args) {
		try {
			Goal root = new MiniJavaParser(System.in).Goal();
			//System.out.println("Program parsed successfully");
			finaltable=root.accept(new ConvertVisitor1(), new Arginfo(""));
			constructtable();
			//printtable(); System.exit(0);
			root.accept(new ConvertVisitor2(),finaltable);
			
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}

	private static void printtable() {
		// TODO Auto-generated method stub
		Iterator it = finaltable.symboltable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"---------------");
			HashMap<String,Integer> varnum=t.varnum;
			HashMap<String,Integer> methodnum=t.methodnum;
			Iterator it2 = varnum.entrySet().iterator();
			Iterator it3 = methodnum.entrySet().iterator();
			System.out.println("vars*****************");
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				String a2=(String) pair2.getKey();
				int t2=(int) pair2.getValue();
				System.out.println(a2+"->"+t2);
			}
			System.out.println("methods*****************");
			while(it3.hasNext()){
				Map.Entry pair3 = (Map.Entry)it3.next();
				String a3=(String) pair3.getKey();
				int t3=(int) pair3.getValue();
				System.out.println(a3+"->"+t3);
			}
		}
		
	}

	public static int addvars(String cname, HashMap<String, Integer> varnum,int prectr) {
		// TODO Auto-generated method stub
		if(cname==null) return 0;
		
		Table tb=finaltable.symboltable.get(cname);
		
		prectr=addvars(tb.parent,varnum,0);
		
		HashMap<String,String> varmap=tb.varmap;
		Iterator it = varmap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			varnum.put(cname+"_"+a,4*(++prectr));
		}
		return prectr;
	}
	
	public static String findclass(String cname,String methodname){
		if(cname==null) return null;
		if(finaltable.symboltable.get(cname).funcdec.containsKey(methodname)){
			return cname;
		}
		else{
			return findclass(finaltable.symboltable.get(cname).parent,methodname);
		}
	}
	
	public static int addmets(String cname,HashMap<String, Integer> methodnum,int prectr,HashMap<String,Integer> visited){
		// TODO Auto-generated method stub
		if(cname==null) return 0;
		
		Table tb=finaltable.symboltable.get(cname);
		
		prectr=addmets(tb.parent,methodnum,0,visited);
		
		HashMap<String,HashMap<String,String> > methodvars=tb.methodvars;
		Iterator it = methodvars.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			if(!visited.containsKey(a)){
				methodnum.put(cname+"_"+a,4*(prectr++));
				visited.put(a,0);
			}
			else{
				String temp=findclass(tb.parent,a);
//				if(!methodnum.containsKey(temp+"_"+a)){
//					System.err.println(temp+" "+a+" "+cname);
//					System.exit(1);
//				}
//				if(temp==null) System.exit(0);
				int temp2=methodnum.get(temp+"_"+a);
				methodnum.remove(temp+"_"+a);
				methodnum.put(cname+"_"+a,temp2);
			}
		}
		return prectr;
	}
	
	public static void constructtable() {
		// TODO Auto-generated method stub
		Iterator it = finaltable.symboltable.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			Table a = (Table) pair.getValue();
			String cname=(String) pair.getKey();
			
			//adding variables
			HashMap<String,Integer> varnum=finaltable.symboltable.get(cname).varnum;
			addvars(cname,varnum,0);
			
			//adding methods
			HashMap<String,Integer> methodnum=finaltable.symboltable.get(cname).methodnum;
			HashMap<String,Integer> visited=new HashMap<String,Integer>();
			addmets(cname,methodnum,0,visited);
			
		}
	}
}