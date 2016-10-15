package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

import java.util.List;

/**
 * This viewer shows all results (also for each topic) for a given topic.
 */
public class TopicResultViewer implements ResultViewer {
    private String topicId;

    /**
     * TopicResultViewer constructor.
     * @param topicId topic id.
     */
    public TopicResultViewer(String topicId){
        this.topicId = topicId;
    }

    /**
     * This method shows all results (also for each topic) for a given topic.
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        System.out.println("************************************************");
        System.out.println("*************** Topic " + topicId + " output ****************");
        System.out.println("************************************************");

        if (results.getType() == ResultComponent.Type.GENERAL) {

            for (ResultComponent runResult : results.getResults()) {

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
