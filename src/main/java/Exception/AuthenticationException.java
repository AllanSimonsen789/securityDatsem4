package Exception;

/**
 * Created by IntelliJ IDEA.
 *   User: rando
 *   Date: 24/09/2020
 *   Time: 19:28
 *   To change this template use File | Settings | File Templates.
 */
public class AuthenticationException extends Exception{
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
        super("Could not be Authenticated");
    }
}
