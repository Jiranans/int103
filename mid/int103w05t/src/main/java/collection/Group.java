package collection;

import java.util.Arrays;
import java.util.Comparator;

public class Group<T> { // type parameter
    private static final int SIZE = 5; 
    private static final int NOTFOUND = -1;
    private T[] box = (T[]) new Object[SIZE];
    private int count;
    
    public boolean add(T t) {
        if (t == null || locate(t) != NOTFOUND) return false;
        box[count++] = t;
        return true;
    }
    
    public T find(T t) {
        int i = locate(t);
        return i == NOTFOUND ? null : box[i];
    }
    
    private int locate(T t) {
        if (t == null) return NOTFOUND;
        for (int i = 0; i < count; i++) {
            if (box[i].equals(t)) return i;// new t = t return i 
        }
        return NOTFOUND;
    }
        
    public Group sort() {
        if (count < 2) return this;
        Arrays.sort(box, 0, count);
        return this;
    }
    
    public Group sort(Comparator<T> c) {
        if (count < 2) return this;
        Arrays.sort(box, 0, count, c);
        return this;
    }
    
    @Override 
    public String toString() {
        var b = new StringBuilder();
        b.append("Group{");
        for (int i = 0; i < count; i++) {
            b.append("\n  ").append(box[i]);
        }
        b.append("}");
        return b.toString();
    }
}
