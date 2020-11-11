package Exception;

public class MySQLDuplicateEntryException extends Exception{
    public MySQLDuplicateEntryException(String message) {
        super(message);
    }

    public MySQLDuplicateEntryException() {
        super("There is a duplicate entry in the DB");
    }
}
