import syntaxtree.*;
import visitor.*;

public class P4 {
   public static void main(String [] args) {
      try {
         Node root = new MiniIRParser(System.in).Goal();
         //System.out.println("Program parsed successfully");
         root.accept(new ConverttoMicro(),new Arginfo("")); // Your assignment part is invoked here.
         
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



