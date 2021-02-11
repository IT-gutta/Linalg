package regex;

import java.util.regex.Pattern;

public abstract class RegexUtils {
    private static Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    public static boolean isValidName(String name){
        return namePattern.matcher(name).matches();
    }

}
