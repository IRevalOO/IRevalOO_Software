package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * This viewer shows all results (also for each topic) for every run and every topic.
 */
public class CompleteResultViewer implements ResultViewer {

    /**
     * This method shows all results (also for each topic) for every run and every topic.
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        System.out.println("************************************************");
        System.out.println("*************** Complete output ****************");
        System.out.println("************************************************");
        System.out.println(results.toString());
    }
}
