package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

import java.util.List;

/**
 * This viewer shows all results (also for each topic) for a given metric .
 */
public class MetricResultViewer implements ResultViewer {

    private String metricName;

    /**
     * MetricResultViewer constructor.
     * @param metricName the metric name.
     */
    public MetricResultViewer(String metricName){
        this.metricName = metricName;
    }

    /**
     * This method shows all results (also for each topic) for a given metric .
     */
    @Override
    public void show(ResultComponent results) throws TrecEvalOOException {
        System.out.println("************************************************");
        System.out.println("*************** " + metricName + " output ****************");
        System.out.println("************************************************");

        if (results.getType() == ResultComponent.Type.GENERAL) {

            for (ResultComponent runResult : results.getResults()) {

                List<Result> resultList = runResult.getResultsByMetric(metricName);

                System.out.println("\nResults for run: " + runResult.getRunName());
                if (resultList.isEmpty()) {
                    System.out.println("No results for metric " + metricName);
                }

                for (Result result : resultList) {
                    System.out.print(result.toString());
                }
            }
        }
    }
}
