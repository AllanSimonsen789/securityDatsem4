package Exception;

public class IllegalArgumentException extends Exception{
    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException() {
        super("Illegal argument!");
    }
}
