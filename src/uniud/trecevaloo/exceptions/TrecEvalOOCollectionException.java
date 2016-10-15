package uniud.trecevaloo.exceptions;

/**
 * Exception for the collections.
 */
public class TrecEvalOOCollectionException extends TrecEvalOOException {

    /**
     * Constructor for a collection exception.
     * @param message the error message.
     */
    public TrecEvalOOCollectionException(String message) {
        super("Collection - " + message);
    }
}
