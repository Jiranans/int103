package collection;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImCollection<E> extends AbstractCollection<E> {
    
    private int count; 
    private E[] array;
    public ImCollection(E[] array) {
        this.array = Arrays.copyOf(array, array.length);
        count = array.length;
    }
    @Override public boolean add(E e){
        if(e == null)return false;
        expand();
        array[count++] = e;
    return true;
    }
     @Override public boolean addAll(Collection<? extends E> c){
        if(c == null) return false;
        for (E e : c) if(e != null) this.add(e);
        return true;
    }
    private  void expand(){
        if (count < array.length) return; // if data in array less than sizeof array -> expand don't work
            array = Arrays.copyOf(array, count << 1 ); //size of new array is *2
    }
    @Override
    public Iterator<E> iterator() {
      return new Iterator(){
          private int cursor;
         
          @Override
          public boolean hasNext() {
          return cursor < count;
          }
          @Override
          public Object next() {
              if (cursor < count) return array[cursor++];
              throw new NoSuchElementException();
          }
      };
    }
    @Override
    public int size() { return count ; }
}
