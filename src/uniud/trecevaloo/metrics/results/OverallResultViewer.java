package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * This viewer shows  the overall results for every run and every topic.
 */
public class OverallResultViewer implements ResultViewer {

    /**
     * This method shows the overall results for every run and every topic.
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        ResultViewer resultViewer = new TopicResultViewer(Result.ALL);
        resultViewer.show(results);
    }
}
