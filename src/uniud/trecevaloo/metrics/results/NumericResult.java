package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.metrics.definitions.Metric;
import uniud.trecevaloo.testcollection.Collection;

import java.util.List;

/**
 * This class creates a numeric result. It is used for most of the metrics.
 */
public class NumericResult extends Result {


    private double value;
    private static final double MIN_GEO_MEAN = 0.00001;

    /**
     * NumericResult constructor.
     * @param idTopic the topic id.
     * @param metric the metric that use this result.
     * @param value the result value.
     */
    public NumericResult(String idTopic, Metric metric, double value){
        super(idTopic, metric);
        this.value = value;
    }

    /**
     *  This method return a printable string of the result value.
     * @return he string of the result.
     */
    @Override
    public String toString() {

        String valueStr;
        double decimal = value - Math.floor(value);

        if(decimal!=0){
           valueStr = String.format("%.4f",value);
        } else {
            valueStr = String.format("%.0f",value);
        }


        if(isOverallMetric()){
            if(getIdTopic().equals(ALL)){
                return "Topic: " + getIdTopic() + "\t-\t" + getMetricAcronym() + ": \t" + valueStr + "\n";
            } else {
                return  "";
            }

        } else {
            return "Topic: " + getIdTopic() + "\t-\t" + getMetricAcronym() + ": \t" + valueStr + "\n";
        }

    }

    /**
     * This method return  the value.
     * @return the numeric value.
     */
    @Override
    public double toDouble() {
        return value;
    }

    /**
     * This method computes the arithmetic mean over a set of metric results.
     * @param results metric result set.
     * @param metric the metric.
     * @return a numeric result.
     */
    public static NumericResult arithmeticMeanResults(ResultSet results, Metric metric, Collection collection){

        double value = 0;

        List<Result> resultsList = results.getResultsByMetric(metric.getMetricAcronym());

        for (Result result: resultsList) {
            value += result.toDouble();
        }

        double topicNum;
        if(EvaluatorManager.avgOverAllTopicsInCollection){
            topicNum = collection.getTopicQrels().size();
        } else {
            topicNum = resultsList.size();
        }

        if(resultsList.size() > 0){
            value = value/topicNum;
        } else {
            value = Double.NaN;
        }

        return new NumericResult(ALL,metric,value);
    }

    /**
     * This method computes the geometric mean over a set of metric results.
     * @param results metric result set.
     * @param metric  the metric.
     * @return a numeric result.
     */
    public static NumericResult geometricMeanResults(ResultSet results, Metric metric,Collection collection){
        double value = 0;

        List<Result> resultsList = results.getResultsByMetric(metric.getMetricAcronym());


        double topicNum;
        if(EvaluatorManager.avgOverAllTopicsInCollection){
            topicNum = collection.getTopicQrels().size();
        } else {
            topicNum = resultsList.size();
        }

        for (int i = 0; i<topicNum; i++) {
            if(i<resultsList.size()){
                double res = resultsList.get(i).toDouble();
                value += Math.log(Math.max(res,MIN_GEO_MEAN));
            } else {
                value += Math.log(Math.max(0,MIN_GEO_MEAN));
            }

        }

        if(resultsList.size() > 0){
            value = Math.exp(value/topicNum);
        } else {
            value = Double.NaN;
        }

        return new NumericResult(ALL,metric,value);
    }

    /**
     * This method computes the sum over a set of metric results.
     * @param results metric result set.
     * @param metric  the metric.
     * @return a numeric result.
     */
    public static NumericResult sumResults(ResultSet results, Metric metric){
        int value = 0;

        List<Result> resultsList = results.getResultsByMetric(metric.getMetricAcronym());
        for (Result result: resultsList) {
            value += result.toDouble();
        }

        return  new NumericResult(ALL, metric,value);
    }
}
