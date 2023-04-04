package school;

public enum Level {
    FRESHMAN(1 ,"Entry Year") {
    @Override public String toString(){ return super.toString() + " {YEAR ONE}" ;}
    },
    SOPHOMORE(2,"Intermediate Year"),
    JUNIOR(3 ,"Intermediate Year"),
    SENIOR(4 ,"Final Year");
    private final int year;
    private final String desc;
    private Level(int year , String desc){ this.year = year; this.desc = desc;
}
    public int getYear(){ return year ;}
     public String getDesc(){ return  desc;}
    
//    FRESHMAN(1, "Entry Year") {
//        @Override public String toString() { return "Year One"; }
//    },
//    SOPHOMORE(2, "Intermediate Year"),
//    JUNIOR(3, "Intermediate Year"),
//    SENIOR(4, "Final Year");
//    
//    private final int year;
//    private final String desc; 
//    
//    private Level(int year, String desc) { this.year = year; this.desc = desc; }
//    public int getYear() { return year; }
//    public String getDesc() { return desc; }

}
