package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.metrics.definitions.Metric;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a set of metric results.
 */
public class ResultSet extends ResultComponent{

    List<ResultComponent> results = new ArrayList<>();

    /**
     * The constructor (is always used internally).
     * @param runName run name.
     * @param metric metric.
     * @param type the type of metric set (GENERAL, METRIC, STATISTIC).
     */
    public ResultSet(String runName, Metric metric, Type type){
        super("",metric,runName,type);
    }

    /**
     * Add a result (or result set) to this result set.
     * @param component the result (or result set) to add.
     */
    @Override
    public void add(ResultComponent component) {
        results.add(component);
    }

    /**
     *
     * Remove a result (or result set) to this result set.
     * @param component the result (or result set) to remove.
     */
    @Override
    public void remove(ResultComponent component) {
        results.remove(component);
    }

    /**
     * Iterate the result set creating the string of all results contained in this result set.
     * @return the results in string format.
     */
    @Override
    public String toString() {

       String resultStr = "";

       for (ResultComponent result: results) {

           if(result==null){
               continue;
           }

           if(result.getType() == Type.RUN){
               resultStr += "\nResults for run: " + result.getRunName() + "\n";
           }
           resultStr += result.toString();
       }

       return resultStr;
    }

    /**
     * Get the list of results.
     * @return all the results.
     */
    public List<ResultComponent> getResults() {
        return results;
    }

    /**
     * Return the result list of a specific metric.
     * @param metricName metric name.
     * @return result list.
     */
    @Override
    public List<Result>  getResultsByMetric(String metricName) {
        List<Result> resultsList = new ArrayList<>();

        for (ResultComponent result: results) {
            if(result!=null) {
                List<Result> metricResults = result.getResultsByMetric(metricName);
                resultsList.addAll(metricResults);
            }
        }

        return resultsList;
    }

    /**
     * Return the result list of a specific topic.
     * @param idTopic topic id.
     * @return the result list.
     */
    @Override
    public List<Result>  getResultsByIdTopic(String idTopic) {

        List<Result> resultsList = new ArrayList<>();

        for (ResultComponent result: results) {
            if(result != null){
                resultsList.addAll(result.getResultsByIdTopic(idTopic));
            }
        }

        return resultsList;
    }

    /**
     * Return the result of a metric for a specific topic.
     * @param metricName metric name.
     * @param idTopic topic id.
     * @return the result.
     */
    @Override
    public Result getResult(String metricName, String idTopic) {
        Result r = null;
        for (ResultComponent result: results) {
            r = result.getResult(metricName,idTopic);
            if(r!=null){
                break;
            }
        }

        return r;
    }
}
