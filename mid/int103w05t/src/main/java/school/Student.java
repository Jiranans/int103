package school;

import human.*;
import java.util.Objects;

public class Student extends Person {
   private Level level;
    
    public Student(Gender sex, String firstname, String lastname, Level level) {
        super(sex, firstname, lastname);
        this.level = Objects.requireNonNull(level, "Student's level cannot be null.");
    }
    
    @Override 
    public String toString() {
        return super.toString() + "[level:" + level + "]";
    }
    
    
}
