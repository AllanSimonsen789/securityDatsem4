package Exception;

public class ForumException extends Exception{

    public ForumException(String message) {
    super(message);
}

    public ForumException() {
        super("Something went wrong");
    }
}
