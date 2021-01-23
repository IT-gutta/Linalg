package regex;

import java.util.regex.Pattern;

public abstract class RegexUtils {

    public static boolean isValidName(String name){
        return Pattern.matches("\\w[a-zA-Z0-9_]*", name);
    }
}
