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
public class ConverttoMiniRA1 extends GJDepthFirst<Arginfo,Arginfo>{
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public int stmtctr=0;
	HashMap<String,Integer> premap;
	HashMap<Integer,Set<Integer> > preuse;
	HashMap<Integer,Set<Integer> > predef;
	
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
    	  premap.put(temp.f0.tokenImage,stmtctr);
    	  
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
	  
	  argu.labelno.put(n.f0.tokenImage,new Table());
	  premap=argu.labelno.get(n.f0.tokenImage).prefunc;
	  preuse=argu.labelno.get(n.f0.tokenImage).use;
	  predef=argu.labelno.get(n.f0.tokenImage).def;
	  stmtctr=0;
	  argu.labelno.get(n.f0.tokenImage).numargs=0;
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      
      
      return argu;
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
      Arginfo temp=n.f0.accept(this, argu);
      
      argu.labelno.put(temp.labelname,new Table());
	  premap=argu.labelno.get(temp.labelname).prefunc;
	  preuse=argu.labelno.get(temp.labelname).use;
	  predef=argu.labelno.get(temp.labelname).def;
	  stmtctr=0;
	  argu.labelno.get(temp.labelname).numargs=Integer.valueOf(n.f2.f0.tokenImage);
      
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
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
      stmtctr++;
      preuse.put(stmtctr-1,new HashSet<Integer>());
      predef.put(stmtctr-1,new HashSet<Integer>());
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public Arginfo visit(NoOpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public Arginfo visit(ErrorStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public Arginfo visit(CJumpStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo tempacc=n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      Set<Integer> temp=preuse.get(stmtctr-1);
      temp.add(tempacc.tempnumber);
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
      Arginfo tempacc1=n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      Arginfo tempacc2=n.f3.accept(this, argu);
      Set<Integer> temp=preuse.get(stmtctr-1);
      temp.add(tempacc1.tempnumber);
      temp.add(tempacc2.tempnumber);
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
      Arginfo tempacc1=n.f1.accept(this, argu);
      Arginfo tempacc2=n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      
      Set<Integer> temp1=preuse.get(stmtctr-1);
      Set<Integer> temp2=predef.get(stmtctr-1);
      temp2.add(tempacc1.tempnumber);
      temp1.add(tempacc2.tempnumber);
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
      Arginfo tempacc1=n.f1.accept(this, argu);
      argu.temptemps.clear();
      argu.exptemps.clear();
      Arginfo tempacc2=n.f2.accept(this, argu);
      
      Set<Integer> temp1=preuse.get(stmtctr-1);
      Set<Integer> temp2=predef.get(stmtctr-1);
      temp2.add(tempacc1.tempnumber);
      //add tempacc2.exptemps to temp1
      temp1.addAll(tempacc2.exptemps);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public Arginfo visit(PrintStmt n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo tempacc=n.f1.accept(this, argu);
      if(tempacc.tempnumber!=-1){
    	  Set<Integer> temp1=preuse.get(stmtctr-1);
    	  temp1.add(tempacc.tempnumber);
      }
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
      Arginfo ret=n.f0.accept(this, argu);
      return ret;
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
      stmtctr++;
      preuse.put(stmtctr-1,new HashSet<Integer>());
      predef.put(stmtctr-1,new HashSet<Integer>());
      Arginfo tempacc=n.f3.accept(this, argu);
      if(tempacc.tempnumber!=-1){
    	  Set<Integer> temp1=preuse.get(stmtctr-1);
    	  temp1.add(tempacc.tempnumber);
      }
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
      Arginfo tempacc1=n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      argu.isstar=1;
      n.f3.accept(this, argu);
      argu.isstar=0;
      n.f4.accept(this, argu);
      Arginfo ret=new Arginfo();
      if(tempacc1.tempnumber!=-1){
    	  ret.exptemps.add(tempacc1.tempnumber);
      }
      ret.exptemps.addAll(argu.temptemps);
      return ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public Arginfo visit(HAllocate n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo tempacc=n.f1.accept(this, argu);
      Arginfo ret=new Arginfo();
      if(tempacc.tempnumber!=-1){
    	  ret.exptemps.add(tempacc.tempnumber);
      }
      return ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public Arginfo visit(BinOp n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo tempacc1=n.f1.accept(this, argu);
      Arginfo tempacc2=n.f2.accept(this, argu);
      Arginfo ret=new Arginfo();
      ret.exptemps.add(tempacc1.tempnumber);
      if(tempacc2.tempnumber!=-1){
    	  ret.exptemps.add(tempacc2.tempnumber);
      }
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
      n.f0.accept(this, argu);
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
      if(ret.tempnumber!=-1){
    	  ret.exptemps.add(ret.tempnumber);
      }
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
      Arginfo ret=new Arginfo();
      ret.tempnumber=Integer.valueOf(n.f1.f0.tokenImage);
      if(argu.isstar==1){
    	  argu.temptemps.add(ret.tempnumber);
      }
      return ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public Arginfo visit(IntegerLiteral n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo ret=new Arginfo();
      return ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public Arginfo visit(Label n, Arginfo argu) {
      Arginfo _ret=null;
      n.f0.accept(this, argu);
      Arginfo ret=new Arginfo();
      ret.labelname=n.f0.tokenImage;
      return ret;
   }

}