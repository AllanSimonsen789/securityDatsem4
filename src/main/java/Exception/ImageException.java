package Exception;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public class ImageException extends Exception {
    public ImageException(String message) {
        super(message);
    }

    public ImageException() {
        super("Image could not upload");
    }
}
