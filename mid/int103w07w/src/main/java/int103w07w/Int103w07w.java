package int103w07w;

import collection.ImCollection;
import collection.ImIterable;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Int103w07w {

    public static void main(String[] args) {
        //testIterable();
        testCollection();
    }
    static void testCollection() {
        Collection<String> c = new ImCollection<>(new String[] {"One", "Five", "Seven"});
        Collection<String> c2 = new ImCollection<>(new String[] {"Seven", "One"});
        Collection<String> c3 = new ImCollection<>(new String[] {"Seven", "Nine"});
        System.out.println(".size(): " + c.size());
        System.out.println(".isEmpty(): " + c.isEmpty());
        System.out.println(".contains(Five): " + c.contains("Five"));
        System.out.println(".contains(Six): " + c.contains("Six"));
        System.out.println(".containsAll(Seven,One): " + c.containsAll(c2));
        System.out.println(".containsAll(Seven,Nine): " + c.containsAll(c3));
        Object[] array = c.toArray();
       for (Object object : array) System.out.println("..." + object);
        System.out.println("All contains e: " + c.stream().allMatch(s->s.contains("e")));
        System.out.println("All contains n: " + c.stream().allMatch(s->s.contains("n")));
        System.out.println("Select n: " + 
                c.stream().filter(s->s.contains("n")).toList());
        System.out.println("String:length: " +
                c.stream().mapToInt(String::length).average().orElse(Integer.MAX_VALUE));
        System.out.println("Average of empty stream: " 
                + IntStream.empty().min().orElse(Integer.MAX_VALUE));
        try {
            c.add("Six");
            c.add("Eight");
        } catch (UnsupportedOperationException e) {
            System.out.println("Calling Unsupported Operation.");
        }
        System.out.println("Content after add: " + c.toString());
        c.addAll(List.of("TWO","THREE","FOUR"));
        System.out.println("Content after addAll: " + c.toString());
        
    }
    static void testIterable() {
        Iterable<String> iterable = new ImIterable<>(new String[] {"One", "Five", "Seven"});
        var it = iterable.iterator();
        
        while (it.hasNext()) { System.out.println("while: " + it.next()); }
        
        for (var i = iterable.iterator(); i.hasNext(); ) {
            System.out.println("for: " + i.next());
        }
        //lamda
        for (var s : iterable) { System.out.println("foreach: " + s); }
        //fucntional
        iterable.forEach(s -> System.out.println("Functional forEach: " + s));
        iterable.forEach(System.out::println);
    }
}
