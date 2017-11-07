import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import syntaxtree.*;
import visitor.*;
// Sample Main.java
public class P2 {
	public static void main(String [] args) {
		try {
			Goal root = new MiniJavaParser(System.in).Goal();
			
			Arginfo finaltable=root.accept(new PrintIdVisitor(), new Arginfo(""));
			root.accept(new PrintIdVisitor2(),finaltable);
			
			
			
			Iterator it = finaltable.symboltable.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				Table a = (Table) pair.getValue();
				String cname=(String) pair.getKey();
				//System.out.println(cname);
				String orig=cname;
				HashMap<String,String> done=new HashMap<String,String>();
				done.put(orig,"");
				while(a.parent!=null){
					if(done.containsKey(a.parent)){
					//if(orig.equals(a.parent)){
						System.out.print("Type error");
						System.exit(0);
					}
					else{
						done.put(a.parent,"");
						a=finaltable.symboltable.get(a.parent);
					}
				}
				
			}
			
			it = finaltable.symboltable.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				Table a = (Table) pair.getValue();
				String cname=(String) pair.getKey();
				Iterator it2=finaltable.symboltable.get(cname).funcdec.entrySet().iterator();
				
				//if(!pair.getKey().equals("A")) continue;
				
				while(it2.hasNext()){
					Map.Entry pair2 = (Map.Entry)it2.next();
					Vector<String> orig = (Vector<String>) pair2.getValue();
					String funcname=(String) pair2.getKey();
					Table aa=a;
					while(aa.parent!=null){
						//System.out.println(aa.parent+"  "+funcname);
						aa=finaltable.symboltable.get(aa.parent);
						if(aa.funcdec.containsKey(funcname)){
							Vector<String> dup = (Vector<String>) aa.funcdec.get(funcname);
							if(dup.size()!=orig.size()){
								System.out.print("Type error");
								System.exit(0);
							}
							for(int i=0;i<dup.size();i++){
								//System.out.print("Type error");
								if(!dup.get(i).equals(orig.get(i))){
									if(finaltable.symboltable.containsKey(orig.get(i))){
							    		  //String name=vartype;
							    		  int x=0;
										  int itr2=4;
							    		  String vartype=orig.get(i);
							    		  String tempstr=dup.get(i);
							    		  while(true){
							    			  itr2--;
							    			  //if(itr2==0) break;
							    			  if(vartype.equals(tempstr)){
							    				  x=1;
							    				  break;
							    			  }
							    			  else{
							    				  vartype=finaltable.symboltable.get(vartype).parent;
							    				  if(vartype==null) break;
							    			  }
							    		  }
							    		  if(x==0){
							    			  System.out.print("Type error");
											  System.exit(0);
							    		  }
							    	 }
									else{
										System.out.print("Type error");
										System.exit(0);
									}
									
								}
							}
						}
					}
				}
				
				//if(pair.getKey().equals("A")) System.exit(0);
			
			}
			
			/*while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey()+"*****************");
				Table a = (Table) pair.getValue();
//				Iterator it2 = a.funcdec.entrySet().iterator();
//				while (it2.hasNext()) {
//			  		 Map.Entry pair2 = (Map.Entry)it2.next();
//			  		 System.out.print("\tfunction Name: ");
//			  		 System.out.format("%-15s",pair2.getKey());
//			  		 
//			  		Vector<String> vec = (Vector<String>) pair2.getValue();
//			  		 for(int i=0;i<vec.size();i++){
//			  			 System.out.print(vec.get(i)+"  ");
//			  		 }
//			  		 System.out.println();
//			  		 //System.out.print("Type: ");
//			  		 //System.out.format("%-15s \n",(String)pair2.getValue());
//			  		 it2.remove(); // avoids a ConcurrentModificationException
//			  	 }
//				
//				
//				it.remove();
//			}
//				
//			  	 while (it2.hasNext()) {
//			  		 Map.Entry pair2 = (Map.Entry)it2.next();
//			  		 System.out.print("\tVariable Name: ");
//			  		 System.out.format("%-15s",pair2.getKey());
//			  		 System.out.print("Type: ");
//			  		 System.out.format("%-15s \n",(String)pair2.getValue());
//			  		 it2.remove(); // avoids a ConcurrentModificationException
//			  	 }
//			  	 
//			 System.out.println("");
			  	 Iterator it3 = a.methodvars.entrySet().iterator();
			  	 while (it3.hasNext()) {
			  		 Map.Entry pair3 = (Map.Entry)it3.next();
			  		 HashMap<String,String> b = (HashMap<String,String>)pair3.getValue();
			  		 System.out.print("\tFunction Name: ");
			  		 System.out.println(pair3.getKey());
			  		 //System.out.print("Return Type: ");
			  		 //System.out.format("%-15s \n",b.ret_type);
			  		Iterator it4 = b.entrySet().iterator();
				  	 while (it4.hasNext()) {
				  		 Map.Entry pair2 = (Map.Entry)it4.next();
				  		 System.out.print("\tVariable Name: ");
				  		 System.out.format("%-15s",pair2.getKey());
				  		 System.out.print("Type: ");
				  		 System.out.format("%-15s \n",(String)pair2.getValue());
				  		 it4.remove(); // avoids a ConcurrentModificationException
				  	 }
			  		 
			  		 
			  		 it3.remove(); // avoids a ConcurrentModificationException
			  	 }
			  	 it.remove(); // avoids a ConcurrentModificationException
			  	 System.out.println("---------------------");			 
			 }*/
			
			
			System.out.println("Program type checked successfully");
		}
		catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}