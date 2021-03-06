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
public class ConverttoMicro extends GJDepthFirst<Arginfo,Arginfo> {
   //
   // Auto class visitors--probably don't need to be overridden.
	public int prectr=5000;
	public Vector vec=new Vector();
   //
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
      if ( n.present() )
         return n.node.accept(this,argu);
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
	      
      System.out.println(" MAIN ");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      
      System.out.println(" END ");
      
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
      argu.labelprint=1;
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
      
      argu.delay=1;
      argu.labelprint=0;
      n.f0.accept(this, argu);
      argu.labelprint=1;
      System.out.println(n.f0.f0.tokenImage+" ["+n.f2.f0.tokenImage+"]"+" BEGIN ");
      
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      argu.delay=0;
      Arginfo temp=n.f4.accept(this, argu);
      System.out.println(" RETURN "+temp.toprint+" END ");
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
      Arginfo temp=n.f0.accept(this, argu);
      return temp;
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

   /**
    * f0 -> "CJUMP"
    * f1 -> Exp()
    * f2 -> Label()
    */
   public Arginfo visit(CJumpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      
      
      n.f0.accept(this, argu);
      
      argu.where=1;
      
      Arginfo temp=n.f1.accept(this, argu);
      
      argu.where=-1;
      
      System.out.println(" CJUMP "+temp.toprint+" "+n.f2.f0.tokenImage);
      
      argu.delay=1;
      argu.labelprint=0;
      n.f2.accept(this, argu);
      argu.labelprint=1;
      argu.delay=0;
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public Arginfo visit(JumpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      System.out.println(" JUMP "+n.f1.f0.tokenImage);
      argu.delay=1;
      n.f0.accept(this, argu);
      argu.labelprint=0;
      n.f1.accept(this, argu);
      argu.labelprint=1;
      argu.delay=0;
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Exp()
    * f2 -> IntegerLiteral()
    * f3 -> Exp()
    */
   public Arginfo visit(HStoreStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      
      argu.where=1;
      n.f0.accept(this, argu);
      Arginfo temp1=n.f1.accept(this, argu);
      argu.delay=1;
      n.f2.accept(this, argu);
      argu.delay=0;
      Arginfo temp2=n.f3.accept(this, argu);
      argu.where=-1;
      
      System.out.println(" HSTORE "+temp1.toprint+" "+n.f2.f0.tokenImage+"  "+temp2.toprint+" ");
      
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Exp()
    * f3 -> IntegerLiteral()
    */
   public Arginfo visit(HLoadStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      //System.out.println(" HLOAD ");
      
      n.f0.accept(this, argu);
      argu.delay=1;
      Arginfo temp1=n.f1.accept(this, argu);
      argu.delay=0;
      argu.where=1;
      
      Arginfo temp2=n.f2.accept(this, argu);
      
      argu.where=-1;
      argu.delay=1;
      n.f3.accept(this, argu);
      argu.delay=0;
      
      System.out.println(" HLOAD  "+temp1.toprint+"  "+temp2.toprint+" "+n.f3.f0.tokenImage+" ");
      
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public Arginfo visit(MoveStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      //System.out.print(" MOVE ");
      argu.delay=1;
      n.f0.accept(this, argu);
      Arginfo temp1=n.f1.accept(this, argu);
      argu.delay=0;
      Arginfo temp2=n.f2.accept(this, argu);
      
      System.out.print(" MOVE "+temp1.toprint+" "+"  "+temp2.toprint);
      
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> Exp()
    */
   public Arginfo visit(PrintStmt n, Arginfo argu) {
      Arginfo _ret=null;
      
      
      
      n.f0.accept(this, argu);
      
      argu.where=2;
      
      Arginfo temp=n.f1.accept(this, argu);
      
      argu.where=-1;
      
      System.out.println(" PRINT "+temp.toprint);
      return _ret;
   }

   /**
    * f0 -> StmtExp()
    *       | Call()
    *       | HAllocate()
    *       | BinOp()
    *       | Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public Arginfo visit(Exp n, Arginfo argu) {
      Arginfo _ret=null;
      int flag=argu.iscall;
      argu.iscall=0;
      argu.labelprint=0;
      argu.delay=1;
      Arginfo temp=n.f0.accept(this, argu);
      argu.labelprint=1;
      int b=prectr++;
      if(flag==1){
    	  //System.exit(0);
    	  System.out.print(" MOVE TEMP "+b+" "+temp.toprint+" ");
    	  argu.iscall=flag;
    	  argu.vec.add(b);
    	  return argu;
      }
//      if(argu.where==-1) {      
//    	  n.f0.accept(this, argu);
//      }
//      else if(argu.where==1){
//    	  System.out.print(" MOVE TEMP "+a+" ");
//    	  argu.where=-1;
//    	  n.f0.accept(this, argu);
//    	  argu.where=1;
//    	  Arginfo ret=new Arginfo("");
//    	  ret.ans=a;
//    	  return ret;
//      }
//      else if(argu.where==2){
//    	  int abc=n.f0.which;
//    	  Arginfo ret=new Arginfo("");
//    	  if(abc==4||abc==5||abc==6){
//    		  argu.flag=1;
//    		  argu.where=-1;
//    		  ret=n.f0.accept(this, argu);
//    		  argu.where=2;
//    		  return ret;
//    	  }
//    	  else{
//    		  System.out.print(" MOVE TEMP "+a+" ");
//    		  argu.where=-1;
//    		  
//    		  n.f0.accept(this, argu);
//    		  argu.where=2;
//    		  ret.ans=a;
//        	  return ret;
//    	  }
//      }
      int a=prectr++;
      Arginfo ret=new Arginfo("");
      System.out.println(" MOVE TEMP "+a+" "+temp.toprint+" ");
      ret.val=a;
      ret.toprint=(" TEMP "+a);
      return ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> Exp()
    * f4 -> "END"
    */
   public Arginfo visit(StmtExp n, Arginfo argu) {
      Arginfo _ret=null;
//      int a=prectr++;
//      System.out.println(" BEGIN ");
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      argu.where=2;
      Arginfo temp=n.f3.accept(this, argu);
      argu.where=-1;
      n.f4.accept(this, argu);
      
//      if(temp.flag==0){
//    	  System.out.println("RETURN TEMP "+temp.val+" END");
//      }
//      else{
//    	  System.out.println("RETURN "+temp.str+" END");
//      }
//      Arginfo ret=new Arginfo("");
//      System.out.println("MOVE TEMP "+a+" "+temp.toprint);
//      System.out.println(" RETURN TEMP "+a+" END ");
//      ret.val=a;
//      ret.toprint=(" TEMP "+a);
      return temp;
   }

   /**
    * f0 -> "CALL"
    * f1 -> Exp()
    * f2 -> "("
    * f3 -> ( Exp() )*
    * f4 -> ")"
    */
   public Arginfo visit(Call n, Arginfo argu) {
      Arginfo _ret=null;
      vec.clear();
      int a=prectr++;
      
      n.f0.accept(this, argu);
      
      int temp=argu.where;
      
      argu.where=2;
      
      Arginfo temp1=n.f1.accept(this, argu);
      
      argu.where=temp;
      
      n.f2.accept(this, argu);
      
      argu.iscall=1;
      Arginfo temp3=new Arginfo("");
      temp3.iscall=1;
      n.f3.accept(this, temp3);
      temp3.iscall=0;
      argu.iscall=0;
      
      n.f4.accept(this, argu);
      
//      System.out.print(" CALL ");
//      
//      if(temp1.flag==0){
//    	  System.out.print("TEMP "+temp1.ans+" ");
//      }
//      else{
//    	  System.out.print(" "+temp1.str+" ");
//      }
//      if(temp3==null){
//    	  System.out.println("noo");
//    	  System.exit(0);
//      }
//      System.out.print(" ( ");
//      for(int i=0;i<temp3.vec.size();i++){
//    	  System.out.print(" "+temp3.vec.get(i)+" ");
//      }
//      System.out.print(" ) ");
//      System.out.println(" ");
//      temp3.vec.clear();
      
      Arginfo ret=new Arginfo("");
      
      System.out.println(" MOVE TEMP "+a+" CALL "+temp1.toprint+" ");
      System.out.println(" ( ");
	    for(int i=0;i<temp3.vec.size();i++){
	    	System.out.println(" TEMP "+temp3.vec.get(i)+" ");
	    }
	    System.out.println(" ) ");
	    System.out.println(" ");
	    ret.val=a;
	    ret.toprint=(" TEMP "+a);
      return ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> Exp()
    */
   public Arginfo visit(HAllocate n, Arginfo argu) {
      Arginfo _ret=null;
      int a=prectr++;
      n.f0.accept(this, argu);
      argu.where=2;
      Arginfo temp=n.f1.accept(this, argu);
      argu.where=-1;
      
//      System.out.print(" HALLOCATE ");
//      
//      if(temp.flag==0){
//    	  System.out.println("TEMP "+temp.ans);
//      }
//      else{
//    	  System.out.println(temp.str);
//      }
//      
      Arginfo ret=new Arginfo("");
      System.out.println("MOVE TEMP "+a+" HALLOCATE "+temp.toprint);
      ret.val=a;
      ret.toprint=(" TEMP "+a);
      return ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Exp()
    * f2 -> Exp()
    */
   public Arginfo visit(BinOp n, Arginfo argu) {
      Arginfo _ret=null;
      int a=prectr++;
      Arginfo temp1=n.f0.accept(this, argu);
      argu.where=1;
      Arginfo temp2=n.f1.accept(this, argu);
      argu.where=2;
      Arginfo temp3=n.f2.accept(this, argu);
      argu.where=-1;
      Arginfo ret=new Arginfo("");
      System.out.println(" MOVE TEMP "+a+" "+temp1.toprint+" "+temp2.toprint+" "+temp3.toprint);
      ret.val=a;
      ret.toprint=(" TEMP "+a);
      return ret;
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
      
      Arginfo temp=new Arginfo("");
      //temp.toprint=(" "+n.f0.toString()+" ");
      if(n.f0.which==0){
    	  temp.toprint=(" LE ");
      }
      if(n.f0.which==1){
    	  temp.toprint=(" NE ");
      }
      if(n.f0.which==2){
    	  temp.toprint=(" PLUS ");
      }
      if(n.f0.which==3){
    	  temp.toprint=(" MINUS ");
      }
      if(n.f0.which==4){
    	  temp.toprint=(" TIMES ");
      }
      if(n.f0.which==5){
    	  temp.toprint=(" DIV ");
      }
      n.f0.accept(this, argu);
      return temp;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public Arginfo visit(Temp n, Arginfo argu) {
      Arginfo _ret=null;
      
      argu.delay=1;
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
//      
//      if(argu.flag==1){
//    	  Arginfo temp=argu;
//    	  temp.str=n.f0.tokenImage+" "+n.f1.f0.tokenImage;
//    	  return temp;
//      }
      if(argu.delay==0){
    	  System.out.println(" "+n.f0.tokenImage+" "+n.f1.f0.tokenImage+" ");
      }
      Arginfo ret=new Arginfo("");
      ret.toprint=" TEMP "+n.f1.f0.tokenImage+" ";
      int result = Integer.parseInt(n.f1.f0.tokenImage);
      ret.val=result;
      return ret;
      
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public Arginfo visit(IntegerLiteral n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      
//      if(argu.flag==1){
//    	  Arginfo temp=argu;
    	  //temp.str=
//    	  return temp;
//      }
      if(argu.delay==0){
    	  System.out.println(" "+n.f0.tokenImage+" ");
      }
      
      Arginfo ret=new Arginfo("");
      ret.toprint=n.f0.tokenImage;
      
      return ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public Arginfo visit(Label n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      
//      if(argu.flag==1){
//    	  Arginfo temp=argu;
//    	  temp.str=n.f0.tokenImage;
//    	  return temp;
//      }
//      if(argu.delay==0){
//    	  System.out.println(" "+n.f0.tokenImage+" ");
//      }
      Arginfo ret=new Arginfo("");
      ret.toprint=n.f0.tokenImage;
      if(argu.labelprint==1){
    	  System.out.println(" "+n.f0.tokenImage);
      }
      return ret;
   }

}
