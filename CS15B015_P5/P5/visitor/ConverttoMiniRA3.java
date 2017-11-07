//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;

import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class ConverttoMiniRA3 extends GJDepthFirst<Arginfo,Arginfo>{
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public Arginfo glbarginfo;
	public String funcname;
	
   public Arginfo visit(NodeList n, Arginfo argu) {
      Arginfo _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public Arginfo visit(NodeListOptional n, Arginfo argu) {
      if ( n.present() ) {
         Arginfo _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public Arginfo visit(NodeOptional n, Arginfo argu) {
      if ( n.present() ){
    	  Label temp=(Label) n.node;
    	  //premap.put(temp.f0.tokenImage,stmtctr);
    	  System.out.println(" "+temp.f0.tokenImage+" ");
    	  return n.node.accept(this,argu);
      }
      else
         return null;
   }

   public Arginfo visit(NodeSequence n, Arginfo argu) {
      Arginfo _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public Arginfo visit(NodeToken n, Arginfo argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public Arginfo visit(Goal n, Arginfo argu) {
      Arginfo _ret=null;
      glbarginfo=argu;
      int a=argu.labelno.get(n.f0.tokenImage).numargs;
      int b=argu.labelno.get(n.f0.tokenImage).numstackslots;
      int c=argu.labelno.get(n.f0.tokenImage).nummaxcallargs;
      funcname=n.f0.tokenImage;
      System.out.println(" MAIN["+a+"]["+b+"]["+c+"] ");
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      
      System.out.println("END");
      if(glbarginfo.labelno.get(funcname).isspill==0) System.out.println("// NOTSPILLED");
      else System.out.println("// SPILLED");
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public Arginfo visit(StmtList n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public Arginfo visit(Procedure n, Arginfo argu) {
      Arginfo _ret=null;
      funcname=n.f0.f0.tokenImage;
      int a=argu.labelno.get(n.f0.f0.tokenImage).numargs;
      int b=argu.labelno.get(n.f0.f0.tokenImage).numstackslots;
      int c=argu.labelno.get(n.f0.f0.tokenImage).nummaxcallargs;
      
      System.out.println(" "+n.f0.f0.tokenImage+"["+a+"]["+b+"]["+c+"] ");
      int pre=glbarginfo.labelno.get(n.f0.f0.tokenImage).numargs-4;
      if(pre<0) pre=0;
      for(int i=pre;i<pre+8;i++){
    	  System.out.println(" ASTORE SPILLEDARG "+i+" s"+(i-pre));
      }
      for(int i=pre+8;i<pre+18;i++){
    	  System.out.println(" ASTORE SPILLEDARG "+i+" t"+(i-pre-8));
      }
      for(int i=0;i<4&&i<a;i++){
    	  if(argu.labelno.get(n.f0.f0.tokenImage).registerloc.containsKey(i)){
    		  System.out.println(" MOVE "+argu.labelno.get(n.f0.f0.tokenImage).registerloc.get(i)+" a"+i);
    	  }
    	  else if(argu.labelno.get(n.f0.f0.tokenImage).stackloc.containsKey(i)){
    		  System.out.println(" ALOAD a"+i+" SPILLEDARG "+argu.labelno.get(n.f0.f0.tokenImage).stackloc.get(i));
    	  }
      }
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      for(int i=pre;i<pre+8;i++){
    	  System.out.println(" ALOAD s"+(i-pre)+" SPILLEDARG "+i);
      }
      for(int i=pre+8;i<pre+18;i++){
    	  System.out.println(" ALOAD t"+(i-pre-8)+" SPILLEDARG "+i);
      }
      System.out.println(" END ");
      if(glbarginfo.labelno.get(funcname).isspill==0) System.out.println("// NOTSPILLED");
      else System.out.println("// SPILLED");
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public Arginfo visit(Stmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public Arginfo visit(NoOpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      System.out.println(" NOOP ");
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public Arginfo visit(ErrorStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      
      System.out.println(" ERROR ");
      
      return _ret;
   }
   
   public String helper(int a,int regno){
	   String ret;
	   if(glbarginfo.labelno.get(funcname).registerloc.containsKey(a)){
		   ret=glbarginfo.labelno.get(funcname).registerloc.get(a);
	   }
 	   else if(glbarginfo.labelno.get(funcname).stackloc.containsKey(a)){
 		  //System.out.println(a);
 		  System.out.println(" ALOAD v"+regno+" SPILLEDARG "+glbarginfo.labelno.get(funcname).stackloc.get(a));
 		  ret="v"+regno;
 	   }
 	   else{
 		   String str="";
 		   return str;
 	   }
	   return ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public Arginfo visit(CJumpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo temp=n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int tempno=temp.tempnumber;
      String str=helper(tempno,1);
      System.out.println(" CJUMP "+str+" "+n.f2.f0.tokenImage);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public Arginfo visit(JumpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      System.out.println(" JUMP "+n.f1.f0.tokenImage);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public Arginfo visit(HStoreStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo temp1=n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      Arginfo temp2=n.f3.accept(this, argu);
      String str1=helper(temp1.tempnumber,1);
      String str2=helper(temp2.tempnumber,0);
      System.out.println(" HSTORE "+str1+" "+n.f2.f0.tokenImage+" "+str2);
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public Arginfo visit(HLoadStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo temp1=n.f1.accept(this, argu);
      Arginfo temp2=n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String str1=helper(temp1.tempnumber,1);
      String str2=helper(temp2.tempnumber,0);
      if(str1.equals("")) return _ret;
      System.out.println(" HLOAD "+str1+" "+str2+" "+n.f3.f0.tokenImage);
      if(glbarginfo.labelno.get(funcname).stackloc.containsKey(temp1.tempnumber)){
    	  int abc=glbarginfo.labelno.get(funcname).stackloc.get(temp1.tempnumber);
    	  System.out.println(" ASTORE SPILLEDARG "+abc+" "+str1+"\n");
      }
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public Arginfo visit(MoveStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo temp=n.f1.accept(this, argu);
      argu.movetempno=temp.tempnumber;
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public Arginfo visit(PrintStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      argu.isprint=1;
      Arginfo temp=n.f1.accept(this, argu);
      argu.isprint=0;
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public Arginfo visit(Exp n, Arginfo argu) {
      Arginfo _ret=null;
      String regnum1=helper(argu.movetempno,1);
      if(regnum1.equals("")){
    	  return _ret;
      }
      argu.flag=1;
      n.f0.accept(this, argu);
      argu.flag=0;
      
      if(n.f0.which==0){
    	  
    	  String regnum=helper(argu.movetempno,1);
    	  System.out.println(" MOVE "+regnum+" v0 ");
    	 // System.out.println("");
    	  if(glbarginfo.labelno.get(funcname).stackloc.containsKey(argu.movetempno)){
        	  int abc=glbarginfo.labelno.get(funcname).stackloc.get(argu.movetempno);
        	  System.out.println(" ASTORE SPILLEDARG "+abc+" "+regnum+"\n");
          }
      }
      if(n.f0.which==1){
    	  
    	  String regnum=helper(argu.movetempno,0);
    	  System.out.println(" MOVE "+regnum+" HALLOCATE "+argu.expregnum);
    	  if(glbarginfo.labelno.get(funcname).stackloc.containsKey(argu.movetempno)){
        	  int abc=glbarginfo.labelno.get(funcname).stackloc.get(argu.movetempno);
        	  System.out.println(" ASTORE SPILLEDARG "+abc+" "+regnum+"\n");
          }
      }
      if(n.f0.which==2){
    	  if(glbarginfo.labelno.get(funcname).stackloc.containsKey(argu.movetempno)){
        	  int abc=glbarginfo.labelno.get(funcname).stackloc.get(argu.movetempno);
        	  System.out.println(" MOVE v1 "+argu.expregnum);
        	  System.out.println(" ASTORE SPILLEDARG "+abc+" v1"+"\n");
          }
    	  else if(glbarginfo.labelno.get(funcname).registerloc.containsKey(argu.movetempno)){
    		  String regnum=helper(argu.movetempno,1);
        	  System.out.println(" MOVE "+regnum+" "+argu.expregnum);
    	  }
      }
      if(n.f0.which==3){
    	  String regnum=helper(argu.movetempno,0);
    	  System.out.println(" MOVE "+regnum+" "+argu.expregnum);
    	  if(glbarginfo.labelno.get(funcname).stackloc.containsKey(argu.movetempno)){
        	  int abc=glbarginfo.labelno.get(funcname).stackloc.get(argu.movetempno);
        	  System.out.println(" ASTORE SPILLEDARG "+abc+" "+regnum+"\n");
          }
      }
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public Arginfo visit(StmtExp n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      argu.isreturn=1;
      n.f3.accept(this, argu);
      argu.isreturn=0;
      n.f4.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public Arginfo visit(Call n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      argu.rgh=0;
      n.f1.accept(this, argu);
      String xyz=argu.expregnum;
      argu.rgh=1;
      n.f2.accept(this, argu);
      argu.tostore=1;
      argu.store.clear();
      n.f3.accept(this, argu);
      argu.tostore=0;
      n.f4.accept(this, argu);
      
      for(int i=0;i<argu.store.size();i++){
    	  if(i<4){
    		  String pre=helper(argu.store.get(i),1);
    		  System.out.println(" MOVE a"+i+" "+pre);
    	  }
    	  else{
    		  String pre=helper(argu.store.get(i),1);
    		  System.out.println(" PASSARG "+(i-3)+" "+pre);
    	  }
      }
      
      System.out.println(" CALL "+xyz);
      //System.out.println(argu.expregnum);
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public Arginfo visit(HAllocate n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      argu.flag=1;
      n.f1.accept(this, argu);
      argu.flag=0;
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public Arginfo visit(BinOp n, Arginfo argu) {
      Arginfo _ret=null;
      String ans="";
      n.f0.accept(this, argu);
      ans=(ans+argu.expregnum);
      n.f1.accept(this, argu);
      ans=(ans+" "+argu.expregnum);
      argu.rgh=0;//to allocate v0 in case of two spills
      n.f2.accept(this, argu);
      argu.rgh=1;
      ans=(ans+" "+argu.expregnum);
      argu.expregnum=ans;
      return _ret;
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public Arginfo visit(Operator n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      if(n.f0.which==0) argu.expregnum="LE";
      if(n.f0.which==1) argu.expregnum="NE";
      if(n.f0.which==2) argu.expregnum="PLUS";
      if(n.f0.which==3) argu.expregnum="MINUS";
      if(n.f0.which==4) argu.expregnum="TIMES";
      if(n.f0.which==5) argu.expregnum="DIV";
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public Arginfo visit(SimpleExp n, Arginfo argu) {
      Arginfo _ret=null;
      Arginfo ret=n.f0.accept(this, argu);
      if(argu.isprint==1) System.out.println(" PRINT "+ret.toprint);
      if(argu.isreturn==1) System.out.println(" MOVE v0 "+ret.toprint);
      return ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public Arginfo visit(Temp n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      if(argu.tostore==1){
    	  argu.store.add(Integer.valueOf(n.f1.f0.tokenImage));
    	  return _ret;
      }
      Arginfo ret=new Arginfo();
      ret.tempnumber=Integer.valueOf(n.f1.f0.tokenImage);
      if(argu.isreturn==1||argu.isprint==1) ret.toprint=helper(ret.tempnumber,1);
      if(argu.flag==1) argu.expregnum=helper(ret.tempnumber,argu.rgh);
      return ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public Arginfo visit(IntegerLiteral n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo ret=new Arginfo();
      ret.toprint=n.f0.tokenImage;
      if(argu.flag==1) argu.expregnum=n.f0.tokenImage;
      return ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public Arginfo visit(Label n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo ret=new Arginfo();
      ret.toprint=n.f0.tokenImage;
      if(argu.flag==1) argu.expregnum=n.f0.tokenImage;
      return ret;
   }

}