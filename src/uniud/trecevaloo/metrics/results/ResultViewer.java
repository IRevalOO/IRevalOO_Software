package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * This interface manages the results visualizations, implement this class for create a new result viewer.
 */
public interface ResultViewer extends ResultManager {

    /**
     * Implement this method to manage a specific kind of visualization.
     * @param results the results to show.
     * @throws TrecEvalOOException if you need to throw an exception pass it to a sub class of TrecEvalOOException like:
     *  throw new TrecEvalOOResultException(e.toString()); where e is the exception catched.
     */
    void show(ResultComponent results) throws TrecEvalOOException;
}
