package collection;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Predicate;

public class Container<E> {
   private E[] array;
   private int count;
   private static final int MIN_CAPACITY = 4;
   private static final int NOT_FOUND = -1;
   
   public Container() { this(MIN_CAPACITY); }
   public Container(int i) { 
      array = (E[]) new Object[i>MIN_CAPACITY ? i : MIN_CAPACITY]; 
   }
   public boolean add(E e) {
      if (e == null) return false;
      grow();
      array[count++] = e;
      return true;
   }
   private void grow() {
      if (count==array.length) array = Arrays.copyOf(array, count << 1);
   }
   private int locate(Predicate<E> p) {
      if (p == null) return NOT_FOUND;
      for (int i = 0; i < count; i++) {
         if (p.test(array[i])) return i;
      }
      return NOT_FOUND;
   }
   public boolean contains(E e) {
      if (e == null) return false;
      return locate(Predicate.isEqual(e)) != NOT_FOUND;
   }
   public E find(Predicate<E> p) {
      int i = locate(p);
      return i == NOT_FOUND ? null : array[i];
   }
   public E remove(E e) {
      if (e == null) return null;
      int i = locate(Predicate.isEqual(e));
      if (i == NOT_FOUND) return null;
      var result = array[i];
      count--;
      for (int j = i; j < count; j++) {
         array[j] = array[j+1];
      }
      array[count] = null;
      return result;
   }
   public String inspect() {
      var s = new StringJoiner(",","{inspect[" + count + "/" + array.length + "]:","}");
      for (E e : array) s.add(e == null ? "_" : e.toString());
      return s.toString();
   }
}
