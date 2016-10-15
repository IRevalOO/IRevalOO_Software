package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 *  This viewer shows overall results for a given run
 */
public class OverallRunResultViewer implements ResultViewer {


    private String runName;

    /**
     * OverallRunResultViewer constructor.
     * @param runName the run name.
     */
    public OverallRunResultViewer(String runName){
        this.runName = runName;
    }

    /**
     *  This method shows overall results for a given run
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        ResultViewer resultViewer = new TopicRunResultViewer(Result.ALL,runName);
        resultViewer.show(results);
    }
}
