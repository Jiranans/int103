package porkaew.kriengkrai;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StreamTest {
    
    // print utilities ---------------------------------
    static final DecimalFormat DECIMAL = new DecimalFormat("0.##");
    static final DoubleConsumer PRINT_DOUBLE = d -> System.out.print("[:" + DECIMAL.format(d) + "]");
    static final IntConsumer PRINT_INT = i -> System.out.print("[:" + i + "]");

    private static DoubleConsumer printDouble(String s, String e) { 
        return t -> System.out.print(s + DECIMAL.format(t) + e); 
    }
    private static IntConsumer printInt(String s, String e) { 
        return t -> System.out.print(s + t + e); 
    }
    private static <T> Consumer<T> print(String s, String e) { 
        return t -> System.out.print(s + t.toString() + e); 
    }
    private static <T> String asString(Stream<T> strm) {
        return strm.map(T::toString).collect(Collectors.joining("],[","[" ,"]"));
    }
    private static String asIntString(IntStream strm) {
        return strm.mapToObj(String::valueOf).collect(Collectors.joining("],[","[" ,"]"));
    }
    private static String asDoubleString(DoubleStream strm) {
        return strm.mapToObj(DECIMAL::format).collect(Collectors.joining("],[","[" ,"]"));
    }
    // end of print utilities

    public static void main(String[] args) {
        test01StreamCreation();
        test02StreamNonTerminal();
        test03StreamTerminal();
        test04StreamCollectors();
    }
    
    static void test01StreamCreation() {
        System.out.println("[1] Stream Creation =================");
        
        // Stream from Varargs (Variable Arguments)
        System.out.println("1.1) Stream.of(varargs), Stream.of(array), Arrays.stream(array) ----");
        Stream<String> strStrm0 = Stream.of("One", "Two", "Three", "Four");
        IntStream intStrm0 = IntStream.of(2473, -384, 453, 94, 532);
        System.out.print("   IntStream.of(..., ..., ...): ");
        intStrm0.forEach(PRINT_INT);
        System.out.println();

        // Stream from an array: Stream.of(array)
        String[] strArr = {"First", "Second", "Third"};
        Stream<String> strStrm1 = Stream.of(strArr);

        // Stream from an array: Arrays.stream(array)
        Stream<String> strStrm2 = Arrays.stream(strArr);
        System.out.println("   Arrays.stream(array): " + asString(strStrm2));
        
        // Stream from two streams: Stream.concat(stream0,stream1)
        System.out.println("1.2) Stream.concat(stream0, stream1) ----");
        Stream<String> strStrm3 = Stream.concat(strStrm0, strStrm1);
        System.out.println("   concat Stream.of(String[]): " + asString(strStrm3));
        
        // reuse a pseudo random number generator
        Random random = new Random();
        
        // Stream from a Supplier: Stream.generate(()->T) vs. random.ints()
        System.out.println("1.3) Stream.generate(supplier) vs. random.ints(count, min, max) ----");
        IntStream intStrm1 = IntStream.generate(random::nextInt).limit(5);
        System.out.println("   IntStream.generate(random::nextInt).limit(5): " + asIntString(intStrm1));
        IntStream intStrm2 = IntStream.generate(() -> 50 + random.nextInt(3)).limit(4); // start 50 51 52 -> 3 
        System.out.println("   IntStream.generate(() -> 50 + random.nextInt(20)).limit(4): " + asIntString(intStrm2));
        IntStream intStrm3 = random.ints(4, 50, 70); // 4 ints in [50,70)
        System.out.println("   random.ints(4, 50, 70);: " + asIntString(intStrm3));
        DoubleStream doubleStrm0 = random.doubles(4, 50.0, 70.0); // 4 doubles in [50.0,70.0)
        System.out.println("   random.doubles(4, 50.0, 70.0): " + asDoubleString(doubleStrm0));
        
        // Stream from a UnaryOperator: Stream.iterate(start, i -> f(i)); = i, f(i), f(f(i)), f(f(f(i))), ...
        System.out.println("1.4) Stream.iterate(start, [hasNextPredicate,] nextUnaryOperator) ----");
        IntStream intStrm4 = IntStream.iterate(2, i -> i * i).limit(5); // 2, 4, 16, 256, 65536
        System.out.println("   Stream.iterate(2, i -> i*i).limit(5): " + asIntString(intStrm4));
        IntStream intStrm5 = IntStream.iterate(0, i -> i < 5, i -> i +1);//0 1 2 3 4
        System.out.println("   Stream.iterate(0, i -> i < 5, i -> i + 1): " + asIntString(intStrm5));
        Stream<int[]> factorial = Stream.iterate(new int[] {1,1} , i -> new int[] { i[0]+1, i[1] * (i[0]+1) } );
        System.out.println("   sequence of factorials => " + asIntString(factorial.limit(7).mapToInt(i -> i[1])));
        
        // Stream from int/long range
        System.out.println("1.5) Int/LongStream.range(start, end) and Int/LongStream.rangeClosed(start,end) ----");
        System.out.println("   Stream.range(10,15): " + asIntString(IntStream.range(10,15)));
        System.out.println("   Stream.rangeClosed(10,15): " + asIntString(IntStream.rangeClosed(10,15)));
        
        // Stream from Collection
        System.out.println("1.6) collection.stream() ----");
        List<String> ls = List.of("None", "One", "Second", "End");
        Set<String> ss = Set.of("Cat", "Rat", "Bat", "Hat", "Mat");
        Map<String,Integer> lives = Map.ofEntries(
                Map.entry("Dog", 22), Map.entry("Cat", 25), Map.entry("Mouse", 4));
        System.out.println("   list.stream(): " + asString(ls.stream()));
        System.out.println("   set.stream(): " + asString(ss.stream()));
        System.out.println("   map.entrySet().stream(): " + asString(lives.entrySet().stream()));
        
        System.out.println("1.7) empty stream ----");
        System.out.println("   Stream.empty(): <<" + asString(Stream.empty()) + ">>");
    }
    
    static void test02StreamNonTerminal() {
        System.out.println("[2] Stream Non-Terminal Processing =================");
        
        // peek(), skip(), limit()
        System.out.println("2.1) peek(), skip(), limit() ------------");
        IntStream iStrm0 = IntStream.rangeClosed(1,10)
                .peek(PRINT_INT) // show each element that passes by
                .skip(3) // ignore the first 3 elements
                .limit(4); // take only 4 elements
        System.out.println("   Notice the output of IntStream.rangeClosed(1,10).peek(intPeek).skip(3).limit(4);");
        System.out.println("\n   [1...10].skip(3).limit(4): " + asIntString(iStrm0));

        // filter()
        System.out.print("2.2) filter() ------------\n   ");
        Stream.of("A1","A2","A3","B4","A5","A6","B7","A8")
                .peek(print("&",";"))
                .filter(s->s.contains("A")) // filter only strings containing "A"
                .forEach(print("[select: ","] "));
        System.out.println();

        // takeWhile()
        System.out.print("2.3) takeWhile() ------------\n   ");
        Stream.of("A1","A2","A3","B4","A5","A6","B7","A8")
                .peek(print("",";"))
                .takeWhile(s->s.contains("A")) // take while strings containing "A"
                .forEach(print("[select: ","] "));
        System.out.println();

        // dropWhile()
        System.out.print("2.4) dropWhile() ------------\n   ");
        Stream.of("A1","A2","A3","B4","A5","A6","B7","A8")
                .peek(print("",";"))
                .dropWhile(s->s.contains("A")) // drop while strings containing "A"
                .forEach(print("[select: ","] "));
        System.out.println();

        // distinct()
        System.out.print("2.5) distinct() ------------\n   ");
        Stream.of("A","B","D","F","C","D","A","E")
                .peek(print("",";"))
                .distinct() // remove duplicate values
                .forEach(print("[select: ","] "));
        System.out.println();
        
        // sorted()
        System.out.print("2.6) sorted() ------------\n   ");
        Stream.of("A","B","D","F","C","D","A","E")
                .peek(print("",";"))
                .sorted() // sorted in the natural order
                .peek(print("<",">"))
                .sorted(Comparator.reverseOrder()) // sorted in the reverse order
                .forEach(print("[","]"));
        System.out.println();

        // map(), mapToObj(), mapToInt()
        System.out.print("2.7) map() ------------\n   ");
        Stream.of("one", "second", "three", "four", "fifth")
                .mapToInt(String::length)
                .forEach(PRINT_INT);
        System.out.println();

        // flatMap(), flatMapToInt()
        System.out.print("2.8) flatMap() ------------\n   ");
        Arrays.stream(new int[][] {{1},{2,3},{4,5,6},{7,8},{9}})
                .flatMapToInt(Arrays::stream)
                .forEach(PRINT_INT);
        System.out.println();

        // mapMulti(), 
        // mapToInt(), mapToObj(), asDoubleStream(), boxed()
        // parallel(), sequential(), unordered()
    }
    
    static void test03StreamTerminal() {
        System.out.println("[3] Stream Terminal Processing =================");
        
        // allMatch(), anyMatch(), noneMatch()
        System.out.println("3.1) allMatch(), anyMatch(), noneMatch() ----");
        System.out.println("   [0...9].allMatch(i -> i<10): " // everyone in [0..9] <10 -> true
               + IntStream.range(0,10).allMatch(i -> i<10));
        System.out.println("   [0...9].allMatch(i -> i<7): " 
               + IntStream.range(0,10).allMatch(i -> i<7));

        System.out.println("   [0...9].anyMatch(i -> i<7): " // someone in [0..9] <7 -> true
               + IntStream.range(0,10).anyMatch(i -> i<7));
        System.out.println("   [0...9].anyMatch(i -> i<0): " 
               + IntStream.range(0,10).anyMatch(i -> i<0));   // not have element in [0..9] <0 -> false

        System.out.println("   [0...9].noneMatch(i -> i<0): " // all element is not < 0 ->true
               + IntStream.range(0,10).noneMatch(i -> i<0));
        System.out.println("   [0...9].noneMatch(i -> i<7): " // have 1 2 3 ... 6 <7 ->false
               + IntStream.range(0,10).noneMatch(i -> i<7));

        System.out.println("   [].allMatch(i -> false): " 
           + IntStream.range(0,0).allMatch(i -> false));
        System.out.println("   [].allMatch(i -> true): " 
           + IntStream.range(0,0).allMatch(i -> true));

        System.out.println("   [].anyMatch(i -> false): " 
           + IntStream.range(0,0).anyMatch(i -> false));
        System.out.println("   [].anyMatch(i -> true): " 
           + IntStream.range(0,0).anyMatch(i -> true));

        System.out.println("   [].noneMatch(i -> false): " 
           + IntStream.range(0,0).noneMatch(i -> false));
        System.out.println("   [].noneMatch(i -> true): " 
           + IntStream.range(0,0).noneMatch(i -> true));
        
        // findFirst(), findAny()
        System.out.println("3.2) findFirst(), findAny() ----");
        System.out.println("   [5,6,7].findFirst().orElse(NONE): "
           + Stream.of("Five","Six","Seven").findFirst().orElse("NONE"));
        System.out.println("   [5,6,7].findAny().orElse(NONE): "
           + Stream.of("Five","Six","Seven").findAny().orElse("NONE"));
        System.out.println("   [].findFirst().orElse(NONE): "
           + Stream.empty().findFirst().orElse("NONE"));
        System.out.println("   [].findAny().orElse(NONE): "
           + Stream.empty().findAny().orElse("NONE"));
        
        // count(), min(), max(), sum(), average(), summaryStatistics()
        System.out.println("3.3) count(),min(),max(),sum(),average(),summaryStatistics() ----");
        int[] stat = {6,7,4,8,5};
        System.out.println("   IntStream.of(6,7,4,8,5)");
        System.out.println("     .count(): " + IntStream.of(stat).count());
        System.out.println("     .sum(): " + IntStream.of(stat).sum());
        System.out.println("     .min(): " + IntStream.of(stat).min());
        System.out.println("     .max(): " + IntStream.of(stat).max());
        System.out.println("     .average(): " + IntStream.of(stat).average());
        System.out.println("     .summaryStatistics():\n       " 
           + IntStream.of(stat).summaryStatistics());
        var stat2 = List.of("I","Me","My","Mine");
        System.out.println("   Stream.of(I,My,Me,Mine)");
        System.out.println("     .count(): " + stat2.stream().count());
        System.out.println("     .min(comparator): " + stat2.stream().min(Comparator.naturalOrder()));
        System.out.println("     .max(comparator): " + stat2.stream().max(Comparator.naturalOrder()));

        // forEach(), forEachOrdered()
        System.out.println("3.4) forEach(), forEachOrdered() ----");
        System.out.println("   IntStream.range(0,10).parallel().forEach(PRINT): ");
        System.out.print("     ");
        IntStream.range(0,10).parallel().forEach(PRINT_INT);
        System.out.println();
        System.out.println("   IntStream.range(0,10).parallel().forEachOrdered(PRINT): ");
        System.out.print("     ");
        IntStream.range(0,10).parallel().forEachOrdered(PRINT_INT);
        System.out.println();
        
        // iterator()
        System.out.println("3.5) iterator() ----");
        System.out.print("   [1-10].boxed().iterator(): {");
        for (var i = IntStream.range(1,11).boxed().iterator(); i.hasNext();) {
           System.out.print(" " + i.next());
        }
        System.out.println("}");

        // spliterator()
        System.out.println("3.6) spliterator() ----");
        var split = IntStream.range(1,9).boxed().parallel().spliterator();
        var split2 = split.trySplit();
        System.out.print("   [1-8].boxed().parallel().spliterator() [trySplit()].tryAvance(): {");
        while (split.tryAdvance(System.out::print)) System.out.print(",");
        System.out.println("}");

        if (split2 != null) {
           var split4 = split2.trySplit();
           System.out.print("   split2 [trySplit()].tryAdvance(): {");
           while (split2.tryAdvance(System.out::print)) System.out.print(",");
           System.out.println("}");
           if (split4 != null) {
              System.out.print("   split4.tryAdvance(): {");
              while (split4.tryAdvance(System.out::print)) System.out.print(",");
              System.out.println("}");
           }
        }

        // toArray(), toList()
        System.out.println("3.7) toArray(), toList() ----");
        Object[] array = IntStream.range(1,9).boxed().toArray();
        System.out.print("   [1-8].toArray() : {");
        for (var object : array) System.out.print("," + object);
        System.out.println("}");

        Integer[] typeArray = IntStream.range(1,9).boxed().toArray(Integer[]::new);
        System.out.print("   [1-8].toArray(Integer[]::new) : {");
        for (Integer integer : typeArray) System.out.print("," + integer);
        System.out.println("}");

        List<Integer> list = IntStream.range(1,9).boxed().toList();
        System.out.print("   [1-8].toList() : {");
        for (Integer i : list) System.out.print("," + i);
        System.out.println("}");     

        // reduce(accumulator) 
        // reduce(identity,accumulator)
        // reduce(identity,accumulator,combiner)
        System.out.println("3.8) reduce() ----");
        System.out.println("   Sum: [1-5].reduce((a,b)->a+b): " + IntStream.range(1,6).reduce(Integer::sum)); //1 2 3..5
        System.out.println("   Sum: [1-5].reduce(0,(a,b)->a+b): " + IntStream.range(1,6).reduce(0,Integer::sum));
        System.out.println("   Product: [1-5].reduce((a,b)->a*b): " + IntStream.range(1,6).reduce((a,b)->a*b));
        System.out.println("   Product: [1-5].reduce(1,(a,b)->a*b): " + IntStream.range(1,6).reduce(1,(a,b)->a*b));
        IntBinaryOperator sumSquare = (a,e) -> a + e * e;
        BinaryOperator<Integer> sumSquare2 = (a,e) -> a + e * e;
        System.out.println("   Sum Square: [1-5].reduce((a,e)->a+e*e): " 
           + IntStream.range(1,6).reduce(sumSquare));
        System.out.println("   Sum Square: [1-5].reduce(0,(a,e)->a+e*e): " 
           + IntStream.range(1,6).reduce(0,sumSquare));
        System.out.println("   Sum Square: [1-5].parallel().boxed().reduce(0,(a,e)->a+e*e,(a,b)->a+b): " 
           + IntStream.range(1,6).parallel().boxed().reduce(0,sumSquare2,Integer::sum));

        var strs = List.of("On","Tw","Th","Fo","Fi","Si","Se","Ei","Ni","Te");
        BinaryOperator<String> join = (a,s)->a+","+s;
        System.out.println("   [On,Tw,Th,...].reduce((a,s)->a+\",\"+s).orElse(\"\"):\n     {" 
           + strs.stream().reduce(join).orElse("") + "}");
        System.out.println("   [On,Tw,Th,...].reduce(\"\",(a,s)->a+\",\"+s):\n     {"
           + strs.stream().reduce("",join) + "}");

        System.out.println("   [On,Tw,Th,...].parallel().reduce((a,s)->a+\",\"+s).orElse(\"\"):\n     {"
           + strs.parallelStream().reduce(join).orElse("") + "}");
        System.out.println("   [On,Tw,Th,...].parallel().reduce(\"\",(a,s)->a+\",\"+s):\n     {"
           + strs.parallelStream().reduce("",join) + "}");
        System.out.println("   [On,Tw,Th,...].parallel().reduce(\"\",(a,s)->a+\",\"+s,String::concat):\n     {"
           + strs.parallelStream().reduce("",join,String::concat) + "}");

        // collect()
        System.out.println("3.9) collect() ----");
        System.out.println("   [On,Tw,Th,...].parallel().collect(starter,accumulator,combiner):\n     "
           + strs.parallelStream()
              .collect(
                 () -> new StringJoiner(",","{","}"),
                 StringJoiner::add,      // (j,s) -> j.add(s)
                 StringJoiner::merge));  // (j,k) -> j.merge(k)

        System.out.println("   [On,Tw,Th,...].parallel.collect(Collector):\n     "
           + strs.parallelStream().collect(
              Collector.of(
                 () -> new StringJoiner(",","{","}"),
                 StringJoiner::add,
                 StringJoiner::merge,
                 Collector.Characteristics.IDENTITY_FINISH,
                 Collector.Characteristics.UNORDERED
              )));
    }
    
    static void test04StreamCollectors() {
        System.out.println("[4] Stream Collectors =================");
        var days = List.of("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");

        // count,minBy,maxBy,summingInt,averagingInt,summarizingInt
        System.out.println("4.1) count,min,max,sum,average,summarize ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.counting()): "
           + days.stream().collect(Collectors.counting()));
        System.out.println("     .collect(Collectors.minBy(comparator)): "
           + days.stream().collect(Collectors.minBy(Comparator.naturalOrder())));
        System.out.println("     .collect(Collectors.maxBy(comparator)): "
           + days.stream().collect(Collectors.maxBy(Comparator.naturalOrder())));
        System.out.println("     .collect(Collectors.summingInt(String::length)): "
           + days.stream().collect(Collectors.summingInt(String::length)));
        System.out.println("     .collect(Collectors.averagInt(String::length)): "
           + days.stream().collect(Collectors.averagingInt(String::length)));
        System.out.println("     .collect(Collectors.summarizingInt(String::length)):\n       "
           + days.stream().collect(Collectors.summarizingInt(String::length)));

        // joining(),joining(delimiter),joining(delimiter,prefix,suffix)
        System.out.println("4.2) joining ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.joining()):\n       "
           + days.stream().collect(Collectors.joining()));
        System.out.println("     .collect(Collectors.joining(delimiter)):\n       "
           + days.stream().collect(Collectors.joining(",")));
        System.out.println("     .collect(Collectors.joining(delim,prefix,suffix)):\n       "
           + days.stream().collect(Collectors.joining(",","{","}")));

        // filter,map,flatMap
        System.out.println("4.3) filter,map,flatMap ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.filtering(length<7,Collectors.joining())):\n       "
           + days.stream().collect(Collectors.filtering(s->s.length()<7,Collectors.joining(","))));
        System.out.println("     .collect(Collectors.mapping(first3,Collectors.joining())):\n       "
           + days.stream().collect(Collectors.mapping(s->s.substring(0,3),Collectors.joining(","))));

        // reduce
        System.out.println("4.4) reduce ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.reducing(String::concat)):\n       "
           + days.stream().collect(Collectors.reducing(String::concat)));
        System.out.println("     .collect(Collectors.reducing(\"\",String::concat)):\n       "
           + days.stream().collect(Collectors.reducing("",String::concat)));
        System.out.println("     .collect(Collectors.reducing(\"\",s->s.subString(0,3),String::concat)):\n       "
           + days.stream().collect(Collectors.reducing("",s->s.substring(0,3),String::concat)));

        // tee
        System.out.println("4.5) tee ----");
        System.out.println("   [1,2,3,...]");
        System.out.println("     .collect(Collectors.teeing(sum,count,sum/count)): "
           + IntStream.range(1,11).boxed().collect(
              Collectors.teeing(
                 Collectors.summingInt(Integer::valueOf), 
                 Collectors.counting(), 
                 (a,b) -> a /(double) b)));
        System.out.println("     .collect(Collectors.averagingInt(...)): "
           + IntStream.range(1,11).boxed().collect(
              Collectors.averagingInt(Integer::valueOf)));

        // collectingAndThen
        System.out.println("4.6) collectingAndThen ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.collectingAndThen(collector,finisher): "
           + days.stream().collect(Collectors.collectingAndThen(
              Collectors.summingInt(String::length), 
              s -> s / (double) days.size())));
        
        // partitioningBy : Predicate<T> -> Map<Boolean,List<T>>
        // partitioningBy : Predicate<T>, Collector<T,D> -> Map<Boolean,D>
        System.out.println("4.7) partitioningBy ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.partitioningBy(contains u):\n       "
           + days.stream().collect(Collectors.partitioningBy(s->s.contains("u"))));
        //
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.partitioningBy(contains u, counting collector):\n       "
           + days.stream().collect(Collectors.partitioningBy(s->s.contains("u"),Collectors.counting())));
        
        // groupingBy : Function<T,K> -> Map<K,List<T>>
        // groupingBy : Function<T,K>, Collector<K,D> -> Map<K,D>
        // groupingBy : Function<T,K>, Supplier<map>, Collector<K,D> -> Map<K,D>
        System.out.println("4.8) groupingBy ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.groupingBy(String::length):\n       "
           + days.stream().collect(Collectors.groupingBy(String::length)));
        //
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.groupingBy(String::length, counting collector):\n       "
           + days.stream().collect(Collectors.groupingBy(String::length,Collectors.counting())));
        //
        System.out.println("   [Mon,Tue,Wed,...]");
        System.out.println("     .collect(Collectors.groupingBy(String::length, mapFactory, counting collector):\n       "
           + days.stream().collect(Collectors.groupingBy(String::length,HashMap::new,Collectors.counting())));

        // toCollection, toSet, toList, toMap
        System.out.println("4.8) toCollection,toList,toSet,toMap ----");
        System.out.println("   [Mon,Tue,Wed,...]");
        var arrayList = days.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println("     .collect(Collectors.toCollection(ArrayList::new)): " + arrayList.getClass());
        var linkedList = days.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println("     .collect(Collectors.toCollection(LinkedList::new)): " + linkedList.getClass());
        var hashSet = days.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println("     .collect(Collectors.toCollection(HashSet::new)): " + hashSet.getClass());
        var treeSet = days.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println("     .collect(Collectors.toCollection(TreeSet::new)): " + treeSet.getClass());
        var list = days.stream().collect(Collectors.toList());
        System.out.println("     .collect(Collectors.toList()): " + list.getClass());
        var set = days.stream().collect(Collectors.toSet());
        System.out.println("     .collect(Collectors.toSet()): " + set.getClass());
        //
        var map1 = days.stream().collect(Collectors.toMap(String::toUpperCase,String::length));
        System.out.println("     .collect(Collectors.toMap(String::toUpperCase,String::length)):\n       " 
           + map1.getClass() + "\n       " + map1);
        var map2 = days.stream().collect(Collectors.toMap(String::length,v->v,String::concat));
        System.out.println("     .collect(Collectors.toMap(String::length,v->v,String::concat)):\n       " 
           + map2.getClass() + "\n       " + map2);
        var map3 = days.stream().collect(Collectors.toMap(String::length,v->v,String::concat,TreeMap::new));
        System.out.println("     .collect(Collectors.toMap(String::length,v->v,String::concat,TreeMap::new)):\n       "
           + map3.getClass() + "\n       " + map3);
    }

}
