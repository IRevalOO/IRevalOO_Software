package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * This viewer shows all results (also for each topic) for a given run.
 **/
public class CompleteRunResultViewer implements ResultViewer {


    private String runName;

    /**
     * CompleteRunResultViewer constructor.
     * @param runName the name of the run.
     */
    public CompleteRunResultViewer(String runName){
        this.runName = runName;
    }

    /**
     * This method shows  all results (also for each topic) for a given run.
     **/
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {

        System.out.println("************************************************");
        System.out.println("*************** " + runName + " output ****************");
        System.out.println("************************************************");

        for (ResultComponent result : results.getResults()) {

            if (result.getRunName().equals(runName)) {
                System.out.println(result.toString());
            }
        }
    }
}
