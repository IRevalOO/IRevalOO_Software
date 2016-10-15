package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

import java.util.List;

/**
 * This viewer shows specific run results (also for each topic) for a given topic.
 */
public class TopicRunResultViewer implements ResultViewer {


    private String topicId;
    private String runName;

    /**
     * TopicRunResultViewer constructor.
     * @param topicId topic id.
     * @param runName run name.
     */
    public TopicRunResultViewer(String topicId, String runName){
        this.topicId = topicId;
        this.runName = runName;
    }

    /**
     * This method shows  specific run results (also for each topic) for a given topic.
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        System.out.println("************************************************");
        System.out.println("*************** Topic " + topicId + " output ****************");
        System.out.println("************************************************");

        if (results.getType() == ResultComponent.Type.GENERAL) {

            for (ResultComponent runResult : results.getResults()) {

                if (runResult.getRunName().equals(runName)) {

                    List<Result> resultList = runResult.getResultsByIdTopic(topicId);

                    System.out.println("\nResults for run: " + runResult.getRunName());
                    if (resultList.isEmpty()) {
                        System.out.println("No results for topic " + topicId);
                    }

                    for (Result result : resultList) {
                        System.out.print(result.toString());
                    }
                }
            }
        }
    }
}
