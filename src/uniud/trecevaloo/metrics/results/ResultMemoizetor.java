package uniud.trecevaloo.metrics.results;

import java.util.HashMap;

/**
 * This singleton class store temporarily metric results. So if a metric needs a metric result already calculated it can take it
 * from here faster, instead to recompute the metric. (It's just for performance).
 */
public class ResultMemoizetor {

    private HashMap<String,Result> results = new HashMap<>();

    private static ResultMemoizetor resultMemoizetor;

    /**
     * Get the ResultMemoizetor instance.
     * @return the instance.
     */
    public static ResultMemoizetor getInstance(){
        if(resultMemoizetor ==null){
            resultMemoizetor = new ResultMemoizetor();
        }
        return resultMemoizetor;
    }

    /**
     * ResultMemoizetor constructor.
     */
    private ResultMemoizetor(){}

    /**
     * Get the metric result.
     * @param idTopic topic id.
     * @param metricName metric name.
     * @return the metric result.
     */
    public Result getMetricResult(String idTopic, String metricName) {
        return results.get(idTopic + "!_&&_!" + metricName);
    }

    /**
     * Save the metric result in ResultMemoizetor.
     * @param idTopic topic id.
     * @param metricName metric name.
     * @param result the metric result.
     */
    public void saveMetric(String idTopic, String metricName, Result result){
        results.put(idTopic + "!_&&_!" + metricName,result);
    }

    /**
     * Initialize the ResultMemoizetor (clear the results).
     */
    public void init(){
        results.clear();
    }

}
