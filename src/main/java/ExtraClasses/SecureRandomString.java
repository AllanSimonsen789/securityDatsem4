package ExtraClasses;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class SecureRandomString {
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    private static String lastSecureString;

    public static String genSecureRandomString() {
        byte[] buffer = new byte[20];
        random.nextBytes(buffer);
        lastSecureString = encoder.encodeToString(buffer);
        return encoder.encodeToString(buffer);
    }

    public static boolean validateSecureString(String sentSecureString) {
        if (lastSecureString.equals(sentSecureString)) {
            return true;
        }
        return false;
    }

}
