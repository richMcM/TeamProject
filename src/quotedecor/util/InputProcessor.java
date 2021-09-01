package quotedecor.util;

import java.util.regex.Pattern;

public class InputProcessor {
    /* INPUT SANITIZERS ***********************************************************************************************/
    /**
     * Trims leading + trailing whitespace and capitalises the leading character of individual String str
     * @param str - String: the string to be sanitised.
     * @return sanitised string
     */
    public static String sanitise(String str) {

        String s = str.trim();
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        return s;
    }
    /**
     * Trims leading + trailing whitespace, Splits sentence string into single words capitalising the leading character of each.
     * @param str - String: the string to be sanitised.
     * @return sanitised string
     */
    public static String strSanitise(String str) {

        String [] splitStr;
        str = str.trim();  // trim whitespace
        splitStr = str.split("[ ,]+");  // split String where a space or , are detected
        String s = "";  // container for new sanitised String

        for(String word: splitStr){  // for each word in String
            word = word.trim();  // trim any potential extras whitespace
            if(word.length()==1)   // if 1st word:
                s += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();  // cap initial char
            else if(word.length()>1)  // for remaining words in String:
                s += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()+" "; // cap and add space
        }
        return s.trim();
    }
    /* INPUT VALIDATERS (REGEX) ***************************************************************************************/
    /**
     * Used to validate user email input String contains @ and . (dot)
     * @param strEmail - String: users email String for validation
     * @return boolean valid or not valid
     */
    public static boolean validEmail(String strEmail) {
        boolean valid = false;
        if(strEmail.contains("@") && strEmail.contains("."))
            valid = true;
        return valid;
    }
    /**
     * Used to validate user input String matches parameter String
     * @param strUser - String: user input String for validation
     * @param strAuthor - String: authors desired input String
     * @return boolean valid or not valid
     */
    public static boolean validMatch(String strUser, String strAuthor) {
        boolean valid = false;
        strUser = strSanitise(strUser);
        strAuthor = strSanitise(strAuthor);
        if(strUser.toLowerCase().equals(strAuthor.toLowerCase()))
            valid = true;
        return valid;
    }
    /**
     * Utilises regular expressions to validate individual elements of name construct.
     * May only contain letters a-z with optional hyphens.
     * @param str - String: name element string for validation
     * @return boolean valid or not valid
     */
    public static boolean validAlpha(String str) {

        boolean valid = false;
        if(Pattern.matches("[a-zA-Z\']+", str))
            valid = true;
        return valid;
    }
    /**
     * Utilises regular expressions to validate monetary elements i.e. balance | overdraft etc.
     * May only contain numbers 0-9 with optional decimal with 1-2 places maximum.
     * @param figure - String: monetary string for validation
     * @return boolean valid or not valid
     */
    public static boolean validMonetaryElement(String figure) {

        boolean valid = false;
        if(Pattern.matches("[0-9]+(\\.[0-9]{1,2})?", figure))
            valid = true;
        return valid;
    }
    /**
     * Utilises regular expressions to validate date elements.
     * Must be in format dd/mm/yyy.
     * @param date - String: date string for validation
     * @return boolean valid or not valid
     */
    public static boolean validDate(String date) {

        boolean valid = false;
        if(Pattern.matches("([0-9]{2})\\/([0-9]{2})\\/([0-9]{4})", date))
            valid = true;
        return valid;
    }
}
