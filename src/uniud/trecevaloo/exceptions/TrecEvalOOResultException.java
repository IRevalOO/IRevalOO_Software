package uniud.trecevaloo.exceptions;

/**
 * Exception for the results.
 */
public class TrecEvalOOResultException extends TrecEvalOOException{

    /**
     * Constructor for a result exception.
     * @param message the error message.
     */
    public TrecEvalOOResultException(String message) {
        super("Result - " + message);
    }
}
