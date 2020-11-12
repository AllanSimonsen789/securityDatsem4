package Exception;

/**
 * Created by IntelliJ IDEA.
 *   User: rando
 *   Date: 24/09/2020
 *   Time: 21:16
 *   To change this template use File | Settings | File Templates.
 */
public class MySQLDuplicateEntryException extends Exception{
    public MySQLDuplicateEntryException(String message) {
        super(message);
    }

    public MySQLDuplicateEntryException() {
        super("There is a duplicate entry in the DB");
    }
}
