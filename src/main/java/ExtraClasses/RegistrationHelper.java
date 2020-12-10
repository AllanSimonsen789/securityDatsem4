package ExtraClasses;

import Exception.RegistrationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationHelper {
    public static boolean checkPassword(String str) throws RegistrationException {
        //Checks to see if the password has at least one upper case, one lower case, one number, and has a length of 12 letters
        char ch;
        boolean upperCaseInPsw = false;
        boolean lowerCaseInPsw = false;
        boolean numberInPsw = false;
        int pswLength = str.length();
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberInPsw = true;
            } else if (Character.isUpperCase(ch)) {
                upperCaseInPsw = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseInPsw = true;
            }
            if (numberInPsw && upperCaseInPsw && lowerCaseInPsw && pswLength >= 12 && pswLength <= 32) {
                return true;
            }
        }
        throw new RegistrationException("Password must contain between 12 and 32 characters. One upper case, one lower case and one number");
    }

    public static boolean checkEmail(String email) throws RegistrationException {
        //Checks to see if we were provided with an email. - OWASP provided regex. https://owasp.org/www-community/OWASP_Validation_Regex_Repository
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        throw new RegistrationException("Email is invalid.");
    }

}
