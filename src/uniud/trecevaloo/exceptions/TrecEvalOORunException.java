package uniud.trecevaloo.exceptions;

/**
 * Exception for the runs.
 */
public class TrecEvalOORunException extends TrecEvalOOException {

    /**
     * Constructor for a run exception.
     * @param message the error message.
     */
    public TrecEvalOORunException(String message) {
        super("Run - " + message);
    }
}
