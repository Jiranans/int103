package int103w08w;

import collection.ImCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Int103w08w {

    public static void main(String[] args) {
        //testCollection();
       //testStream();
        //testReduce();
        testList();
       // Set<String> x= new TreeSet<>();
    }
    static void testReduce() {
        var ia = new Integer[] {3,4,5,6};
        System.out.println("Reduce (sum): " + Arrays.stream(ia).reduce((a,b) -> a + b));
        System.out.println("Reduce (sum square): " 
                + Arrays.stream(ia).reduce(0,(a,b) -> a + b*b));
    }
    static void testList(){
        List<String> li = new LinkedList<>();
        List<String> al = new ArrayList<>(3);
        li.add("one");
        li.add(0,"two");
        al.add(0,"two");
        al.add(1,"one");
        al.add(2,"three");
        al.add(3,"one");
        for (String string : al) {
            System.out.println(" " + string);
        }
    }
     static void testStream() {
        var c = List.of("zero","one","two","three","four","five","six","seven");
        System.out.println("All < 10: " + c.stream().allMatch(s->s.length()<10));
        System.out.println("Any < 4: " + c.stream().anyMatch(s->s.length()<4));
        System.out.println("None < 3: " + c.stream().noneMatch(s->s.length()<3));
        List<String> e = List.of();
        System.out.println("All: " + e.stream().allMatch(s->false));
        System.out.println("None: " + e.stream().noneMatch(s->false));
        List<Integer> il = List.of();//1,2,3,4,5,6);
        System.out.println("Summary: " 
                + il.stream().mapToInt(Integer::valueOf).summaryStatistics());
    }
    static void testCollection() {
        var c = new ImCollection<String>(new String[] {"Zero","One","Two","Three","Four","Five"});

        var arr = c.toArray();
        System.out.println("arr type: " + arr);
        System.out.print("arr content:");
        for (Object object : arr) System.out.print(" " + ((String) object).charAt(0)); //casting
        System.out.println();

        var ar2 = c.toArray(new String[0]); // arrays String
        System.out.println("ar2 type: " + ar2);
        System.out.print("ar2 content:");
        for (String string : ar2) System.out.print(" " + string.charAt(0));
        System.out.println();
        
        var ar3 = c.toArray(String[]::new); // i -> new String[0]
        System.out.println("ar3 type: " + ar3);
        System.out.print("ar3 content:");
        Arrays.stream(ar3).forEach(s->System.out.print(" "+s.charAt(0)));
        System.out.println();
        
        System.out.println("Stream");
        c.stream()
                .filter(s-> s.contains("e"))
                .map(String::length)
                .parallel()
                .sequential()
                .forEach(st->System.out.print(" " + st));
        System.out.println(); //internal iterator
        
        System.out.println("external interator");
        for (String string : c) {
            if (string.contains("e"))System.out.print(" " + string);    //external interator
        }
//        System.out.print("Collection to Stream:");
//        c.stream().forEach(s->System.out.print(" "+s.charAt(0)));
//        System.out.println();
//        
//        System.out.println("Containing e:");
//        for (String string : c) {
//            System.out.println("[" + string + "](" + Thread.currentThread().getId() + ")");
//            if (string.contains("e")) System.out.println(" " + string);
//        }
//        System.out.println();
//        
//        System.out.println("...");
//        System.out.println("functional: Containing e:");
//        c.stream()
//                .parallel()
//                .peek(s->System.out.println("peek[" + s + "](" + Thread.currentThread().getId() + ")"))
//                .filter(s->s.contains("e"))
//                .map(s->s.substring(1,2))
//                .forEach(s->System.out.println("forEach " + s));
//        System.out.println();
//        
    } 
}
