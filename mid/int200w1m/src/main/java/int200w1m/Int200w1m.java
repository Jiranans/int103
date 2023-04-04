package int200w1m;

import collection.Container;

public class Int200w1m {

   public static void main(String[] args) {
      testContainer();
   }
   static void testContainer() {
      var c = new Container<String>();
      c.add("FIRST");
      c.add("SECOND");
      c.add("THIRD");
      c.add("FORTH");
      System.out.println("add: FIRST, SECOND, THIRD, FORTH");
      System.out.println(c.inspect());
      c.remove("SECOND");
      System.out.println("Remove SECOND");
      System.out.println(c.inspect());
      c.remove("FIRST");
      System.out.println("Remove FIRST");
      System.out.println(c.inspect());
   }
}
