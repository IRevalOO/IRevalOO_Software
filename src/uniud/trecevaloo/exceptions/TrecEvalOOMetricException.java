package uniud.trecevaloo.exceptions;

/**
 * Exception for the metrics.
 */
public class TrecEvalOOMetricException extends TrecEvalOOException {

    /**
     * Constructor for a metric exception.
     * @param message the error message.
     */
    public TrecEvalOOMetricException(String message) {
        super("Metric - " + message);
    }
}
