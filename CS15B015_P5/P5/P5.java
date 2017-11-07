import java.util.*;


import syntaxtree.*;
import visitor.*;

public class P5 {
   public static int prestackno;
   public static void main(String [] args) {
      try {
         Node root = new microIRParser(System.in).Goal();
         //System.out.println("Program parsed successfully");
         Arginfo temp1=root.accept(new ConverttoMiniRA1(),new Arginfo()); // Your assignment part is invoked here.
         //printlabelnumbers(temp1);
         //printdefnumbers(temp1);
         //printusenumbers(temp1);
         Arginfo temp2=root.accept(new ConverttoMiniRA2(),temp1);
         //printedgenumbers(temp2);
         computeinout(temp2);
         //printinnumbers(temp2);
         //printoutnumbers(temp2);
         //printargs(temp2);
         computelastin(temp2);
         computefirstout(temp2);
         //printliveness(temp2);
         getliveness(temp2);
         RegisterAllocate(temp2);
         get3rdnumber(temp2);
         //printreglocation(temp2);
         //printstacklocation(temp2);
         //LinearScanRegisterAllocation(temp2);
         //print3numbers(temp2);
         root.accept(new ConverttoMiniRA3(),temp2);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
   
   private static void printreglocation(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,String > use=t.registerloc;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();
				System.out.print(a2+" ");
				String t2=(String) pair2.getValue();
				System.out.println(t2+" ");
			}
		}   
  }
   
   private static void printstacklocation(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Integer > use=t.stackloc;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();
				System.out.print(a2+" ");
				Integer t2=(Integer) pair2.getValue();
				System.out.println(t2+" ");
			}
		}   
  }
   
   private static void get3rdnumber(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			//System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Integer > use=t.stackloc;
			if(a.equals("MAIN")) t.numstackslots=use.size();
			else t.numstackslots=18+(use.size());
			int args=temp1.labelno.get(a).numargs;
			if(args<4){
				if(use.size()>0) t.isspill=1; 
			}
			else{
				if(use.size()>(args-4)) t.isspill=1;
			}
		}   
 }
   
   private static void print3numbers(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println(a+" ["+t.numargs+"]["+t.numstackslots+"]["+t.nummaxcallargs+"]");
			
		}   
}
   
   private static void RegisterAllocate(Arginfo param){
	   Iterator it = param.labelno.entrySet().iterator();
	   while(it.hasNext()){
		    Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			
			for(int i=4;i<t.numargs;i++){
				t.stackloc.put(i,i-4);
			}
			if(t.numargs<=4) prestackno=0;
			else prestackno=t.numargs-4;
					
			LinearScanRegisterAllocation(t);
	   }
   }
   
   private static void LinearScanRegisterAllocation(Table param){
	   Vector<myclass> intervals=param.intervals;
	   Comparator<myclass> comparator = new myclassComparator();
	   Collections.sort(intervals, comparator);
	   Queue<String> freereg=new LinkedList<String>();
	   Set<myclass> active=new HashSet<myclass>();
	   int i;
	   for(i=0;i<10;i++){
		   freereg.add("t"+i);
	   }
	   for(i=0;i<8;i++){
		   freereg.add("s"+i);
	   }
	   for(i=0;i<intervals.size();i++){
		   ExpireOldIntervals(intervals.get(i),active,freereg,param.registerloc);
		   if(active.size()>=18){
			   SpillAtInterval(intervals.get(i),param,active);
		   }
		   else{
			   //Iterator<String> itr = freereg.iterator();
			   //String aaa=itr.next();
			   String aaa=freereg.peek();
			   freereg.remove();
			   param.registerloc.put(intervals.get(i).tempno,aaa);
			   active.add(intervals.get(i));
		   }
	   }
   }
   
   private static void SpillAtInterval(myclass preinter,Table param,Set<myclass> active){
	   Vector<myclass> vec=new Vector<myclass>();
	   Iterator<myclass> itr = active.iterator();
		while(itr.hasNext()){
			vec.add(itr.next());
		}
		Comparator<myclass> comparator = new myclassComparator2();
		Collections.sort(vec, comparator);
		myclass spill=vec.get(vec.size()-1);
		if(spill.death>preinter.death){
			if(param.registerloc.containsKey(spill.tempno)){
				param.registerloc.put(preinter.tempno,param.registerloc.get(spill.tempno));
				param.registerloc.remove(spill.tempno);
			}
			else{
				System.exit(1);
			}
			param.stackloc.put(spill.tempno,prestackno++);
			active.remove(spill);
			active.add(preinter);
			
		}
		else{
			param.stackloc.put(preinter.tempno,prestackno++);
		}
	   
   }
   
   private static void ExpireOldIntervals(myclass preinter,Set<myclass> active,Queue<String> freereg,HashMap<Integer,String> regloc){
	    Iterator<myclass> itr = active.iterator();
		//Vector<myclass> temp;
	    while(itr.hasNext()){
			myclass pre=itr.next();
			Integer tempno=pre.tempno;
			if(pre.death<preinter.birth){
				if(regloc.containsKey(tempno)) freereg.add(regloc.get(tempno));
				//active.remove(pre);
				itr.remove();
			}
		}
   }
   
   private static void getliveness(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			//System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Integer > use=t.firstout;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();//tempno
				//System.out.print(a2+" ");
				
				Integer t2=(Integer) pair2.getValue();//firstout
				//System.out.print(t2+" ");
				Integer t3=t.lastin.get(a2);//lastin
				//System.out.println(t3);
				myclass temp=new myclass(a2,t2,t3);
				t.intervals.add(temp);
			}
		}   
  }
   
   private static void printliveness(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Integer > use=t.firstout;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();
				System.out.print(a2+" ");
				Integer t2=(Integer) pair2.getValue();
				System.out.print(t2+" ");
				Integer t3=t.lastin.get(a2);
				System.out.println(t3);
			}
		}   
   }
   
   private static void computefirstout(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();//funcname
			Table t=(Table) pair.getValue();
			HashMap<Integer,Set<Integer> > prein=t.out;//in
			Integer numargs=t.numargs;//number of arguments in the function
			Iterator it2 = prein.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();//line number
				Set<Integer> t2=(Set<Integer>) pair2.getValue();
				//System.out.println(a2+"->"+t2);
				Iterator<Integer> itr = t2.iterator();
				while(itr.hasNext()){
					Integer tempno=itr.next();
					//itr.next() has temp number
					if(t.firstout.containsKey(tempno)){
						Integer prelastin=t.firstout.get(tempno);
						if(prelastin>a2){
							t.firstout.put(tempno, a2);
						}
					}
					else if(numargs<=tempno){
						t.firstout.put(tempno,a2);
					}
				}
			}
		}   
  }
   
   private static void computelastin(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();//funcname
			Table t=(Table) pair.getValue();
			HashMap<Integer,Set<Integer> > prein=t.in;//in
			Integer numargs=t.numargs;//number of arguments in the function
			Iterator it2 = prein.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();//line number
				Set<Integer> t2=(Set<Integer>) pair2.getValue();
				//System.out.println(a2+"->"+t2);
				Iterator<Integer> itr = t2.iterator();
				while(itr.hasNext()){
					Integer tempno=itr.next();
					//itr.next() has temp number
					if(t.lastin.containsKey(tempno)){
						Integer prelastin=t.lastin.get(tempno);
						if(prelastin<a2){
							t.lastin.put(tempno, a2);
						}
					}
					else if(numargs<=tempno||tempno<4){
						t.lastin.put(tempno,a2);
					}
				}
			}
			for(int i=0;i<4;i++){
				if(t.lastin.containsKey(i)&&i<numargs) t.firstout.put(i,0);
			}
		}   
   }
   
   private static void computeinout(Arginfo temp1) {
		// TODO Auto-generated method stub
		   Iterator it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();//funcname
				Table t=(Table) pair.getValue();
				//System.out.println("\n\n\n\n"+a+"---------------");
				HashMap<Integer,Set<Integer> > varin=t.in;
				HashMap<Integer,Set<Integer> > varout=t.out;
				HashMap<Integer,Set<Integer> > use=t.use;
				
				Iterator it2 = use.entrySet().iterator();
				while(it2.hasNext()){
					Map.Entry pair2 = (Map.Entry)it2.next();
					Integer a2=(Integer) pair2.getKey();//line number
					//System.out.print(a2+" ");
					varin.put(a2,new HashSet<Integer>());//adding empty sets to in and out
					varout.put(a2,new HashSet<Integer>());
				}
			}  
		   it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();//funcname
				Table t=(Table) pair.getValue();
				int flag=1;
				while(true){
					HashMap<Integer,Set<Integer> > varin=t.in;
					HashMap<Integer,Set<Integer> > varout=t.out;
					HashMap<Integer,Set<Integer> > succ=t.succ;
					Iterator it2 = varin.entrySet().iterator();
					while(it2.hasNext()){
						Map.Entry pair2 = (Map.Entry)it2.next();
						Integer a2=(Integer) pair2.getKey();//line number
						Set<Integer> inset=(Set<Integer>) pair2.getValue();
						Set<Integer> outset=t.out.get(a2);
						Set<Integer> defset=t.def.get(a2);
						Set<Integer> succset=t.succ.get(a2);
						Set<Integer> useset=t.use.get(a2);
						Set<Integer> dupin=new HashSet<Integer>();
						Set<Integer> dupout=new HashSet<Integer>();
						dupin.addAll(inset); dupout.addAll(outset); 
						Set<Integer> aa=new HashSet<Integer>();
						aa.addAll(outset); aa.removeAll(defset);
						aa.addAll(useset);// aa had new inset
						Iterator<Integer> itr = succset.iterator();
						Set<Integer> bb=new HashSet<Integer>();
						while(itr.hasNext()){
						  bb.addAll(t.in.get(itr.next()));
						}//bb has new outset
						if(!dupin.equals(aa)||!dupout.equals(bb)) flag=0;
						t.out.put(a2,bb);
						t.in.put(a2,aa);
						
					}
					if(flag==0) flag=1;
					else break;
					//break;
				}
			}
	}
   
   private static void printinnumbers(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Set<Integer> > use=t.in;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();
				System.out.print(a2+" ");
				Set<Integer> t2=(Set<Integer>) pair2.getValue();
				//System.out.println(a2+"->"+t2);
				Iterator<Integer> itr = t2.iterator();
				while(itr.hasNext()){
				  System.out.print(itr.next()+" ");
				}
				System.out.println("");
			}
		}   
    }
   
   private static void printoutnumbers(Arginfo temp1) {
		// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"--------------------------------------------");
			HashMap<Integer,Set<Integer> > use=t.out;
			Iterator it2 = use.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				Integer a2=(Integer) pair2.getKey();
				System.out.print(a2+" ");
				Set<Integer> t2=(Set<Integer>) pair2.getValue();
				//System.out.println(a2+"->"+t2);
				Iterator<Integer> itr = t2.iterator();
				while(itr.hasNext()){
				  System.out.print(itr.next()+" ");
				}
				System.out.println("");
			}
		}   
	   }

   private static void printlabelnumbers(Arginfo temp1) {
	// TODO Auto-generated method stub
	   Iterator it = temp1.labelno.entrySet().iterator();
	   while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String a=(String) pair.getKey();
			Table t=(Table) pair.getValue();
			System.out.println("\n\n\n\n"+a+"---------------");
			HashMap<String,Integer> varnum=t.prefunc;
			Iterator it2 = varnum.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pair2 = (Map.Entry)it2.next();
				String a2=(String) pair2.getKey();
				int t2=(int) pair2.getValue();
				System.out.println(a2+"->"+t2);
			}
		}   
   }
   
   private static void printdefnumbers(Arginfo temp1) {
		// TODO Auto-generated method stub
		   Iterator it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();
				Table t=(Table) pair.getValue();
				System.out.println("\n\n\n\n"+a+"--------------------------------------------");
				HashMap<Integer,Set<Integer> > use=t.def;
				Iterator it2 = use.entrySet().iterator();
				while(it2.hasNext()){
					Map.Entry pair2 = (Map.Entry)it2.next();
					Integer a2=(Integer) pair2.getKey();
					System.out.print(a2+" ");
					Set<Integer> t2=(Set<Integer>) pair2.getValue();
					//System.out.println(a2+"->"+t2);
					Iterator<Integer> itr = t2.iterator();
					while(itr.hasNext()){
					  System.out.print(itr.next()+" ");
					}
					System.out.println("");
				}
			}   
	   }
   
   private static void printusenumbers(Arginfo temp1) {
		// TODO Auto-generated method stub
		   Iterator it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();
				Table t=(Table) pair.getValue();
				System.out.println("\n\n\n\n"+a+"--------------------------------------------");
				HashMap<Integer,Set<Integer> > use=t.use;
				Iterator it2 = use.entrySet().iterator();
				while(it2.hasNext()){
					Map.Entry pair2 = (Map.Entry)it2.next();
					Integer a2=(Integer) pair2.getKey();
					System.out.print(a2+" ");
					Set<Integer> t2=(Set<Integer>) pair2.getValue();
					//System.out.println(a2+"->"+t2);
					Iterator<Integer> itr = t2.iterator();
					while(itr.hasNext()){
					  System.out.print(itr.next()+" ");
					}
					System.out.println("");
				}
			}   
	   }
   private static void printargs(Arginfo temp1) {
		// TODO Auto-generated method stub
		   Iterator it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();
				Table t=(Table) pair.getValue();
				System.out.print("\n\n\n\n"+a+"-------");
				System.out.println(t.numargs);
			}   
	   }
   private static void printedgenumbers(Arginfo temp1) {
		// TODO Auto-generated method stub
		   Iterator it = temp1.labelno.entrySet().iterator();
		   while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String a=(String) pair.getKey();
				Table t=(Table) pair.getValue();
				System.out.println("\n\n\n\n"+a+"--------------------------------------------");
				HashMap<Integer,Set<Integer> > use=t.succ;
				Iterator it2 = use.entrySet().iterator();
				while(it2.hasNext()){
					Map.Entry pair2 = (Map.Entry)it2.next();
					Integer a2=(Integer) pair2.getKey();
					System.out.print(a2+" ");
					Set<Integer> t2=(Set<Integer>) pair2.getValue();
					//System.out.println(a2+"->"+t2);
					Iterator<Integer> itr = t2.iterator();
					while(itr.hasNext()){
					  System.out.print(itr.next()+" ");
					}
					System.out.println("");
				}
			}   
	   }
}