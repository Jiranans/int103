package tools;

public class Util {
    private Util() { }
    public static final String BLANK = "_";
    public static final boolean isValid(String s) { return s != null && ! s.isBlank(); }
    public static final boolean isInvalid(String s) { return s == null || s.isBlank(); }
}
