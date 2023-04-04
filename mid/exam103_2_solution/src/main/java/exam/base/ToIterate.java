package exam.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ToIterate<T> implements Iterable<T>{ // implements Iterable so that ToIterate can be iterated
    private final T[] internal;

    public static final <T> ToIterate<T> of(T ... ts) {
        T[] array = (T[]) new Object[ts.length];
        System.arraycopy(ts, 0, array, 0, ts.length);
        return new ToIterate(array);
    }

    private ToIterate(T[] ts) { internal = ts; }

    @Override
    public Iterator<T> iterator() {
       return new Iterator(){
          private int cursor;
          @Override
          public boolean hasNext() {
          return cursor < internal.length;
          }
          @Override
          public Object next() {
              return internal[cursor++];
           
          }
      };  
    }

    

}
