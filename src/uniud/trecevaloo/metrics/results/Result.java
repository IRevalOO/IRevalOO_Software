package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOResultException;
import uniud.trecevaloo.metrics.definitions.Metric;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single result.
 */
public abstract class Result extends ResultComponent {

    /**
     * The Result constructor.
     * @param idTopic topic id.
     * @param metric metric.
     */
    public Result(String idTopic, Metric metric){
        super(idTopic,metric,"",Type.RESULT);
    }

    /**
     * This method can be implemented by the sub classes if possible. It return the result
     * in a numeric format.
     * @return result in double.
     */
    public abstract double toDouble();

    /**
     *  This method is disabled for single result.
     * @param component
     */
    @Override
    public void add(ResultComponent component) {
        throw new TrecEvalOOResultException("Impossible to remove in result");
    }

    /**
     * This method is disabled for single result.
     * @param component
     */
    @Override
    public void remove(ResultComponent component) {
        throw new TrecEvalOOResultException("Impossible to remove in result");
    }


    /**
     *  This method is disabled for single result.
     * @return
     */
    @Override
    public List<ResultComponent> getResults() {
        return null;
    }

    /**
     * Return the result list of a specific metric.
     * @param metricName metric name.
     * @return result list.
     */
    @Override
    public List<Result>  getResultsByMetric(String metricName) {
        List<Result> results = new ArrayList<>();

        if(getMetricAcronym().equals(metricName)) {
            results.add(this);
        }

        return results;
    }


    /**
     * Return the result list of a specific topic.
     * @param idTopic topic id.
     * @return result list.
     */
    @Override
    public List<Result> getResultsByIdTopic(String idTopic) {
        List<Result> results = new ArrayList<>();

        if(this.getIdTopic().equals(idTopic)) {
            results.add(this);
        }

        return results;
    }

    /**
     * Return the result.
     * @param metricName metric name.
     * @param idTopic topic id.
     * @return the result.
     */
    @Override
    public Result getResult(String metricName, String idTopic) {
        return this;
    }

}
