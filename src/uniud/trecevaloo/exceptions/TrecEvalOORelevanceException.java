package uniud.trecevaloo.exceptions;

/**
 * Exception for the relevance.
 */
public class TrecEvalOORelevanceException extends TrecEvalOOException {

    /**
     * Constructor for a relevance exception.
     * @param message the error message.
     */
    public TrecEvalOORelevanceException(String message){
        super("Relevance - " + message);
    }
}
