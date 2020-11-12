package Exception;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationException extends Exception {
    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException() {
        super("Something went wrong during registration, try again later.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
