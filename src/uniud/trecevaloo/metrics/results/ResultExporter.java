package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * Implement this interface to manage different types of exportation of your results (to file, db, ...).
 */
public interface ResultExporter extends ResultManager{

    /**
     * Implement this method to manage a specific kind of exportation.
     * @param results the results to export.
     * @throws TrecEvalOOException if you need to throw an exception pass it to a sub class of TrecEvalOOException like:
     *  throw new TrecEvalOOResultException(e.toString()); where e is the exception catched.
     */
    void export(ResultComponent results) throws TrecEvalOOException;
}
