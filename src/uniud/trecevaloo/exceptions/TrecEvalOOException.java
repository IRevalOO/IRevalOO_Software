package uniud.trecevaloo.exceptions;

/**
 *  Generic system exception.
 */
public class TrecEvalOOException extends RuntimeException{
    /**
     * Constructor for a general TrecEvalOO exception.
     * @param message the error message.
     */
    public TrecEvalOOException(String message){
        super("Error: " + message);
    }
}
