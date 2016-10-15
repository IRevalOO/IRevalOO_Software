package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.exceptions.TrecEvalOOMetricException;
import uniud.trecevaloo.metrics.results.ResultMemoizetor;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultComponent;
import uniud.trecevaloo.metrics.results.ResultSet;

import java.util.List;
import java.util.ArrayList;


/**
 * This class represents a single metric. Extend this class to create a new metric.
 * Remember to create the public constructor that define the metric attributes and
 * implement the abstract methods: computeTopicResult(...) and computeOverallResult(...).
 */
public abstract class Metric implements MetricComponent {

    public String acronym = "";
    public String completeName = "";
    public String description = "";
    public boolean overallMetric = false;
    protected Collection collection;
    protected List<TopicRun> run;

    /**
     * This method computes the metric for each topic in the intersection between topics in qrels and run.
     * At the end it computes the over all result of the metric across the run and save all the results in a metric result set.
     *
     * @param collection the test collection used in the evaluation process.
     * @param run the run evaluated in the process.
     * @param results the result set where the metric can save its results.
     */
    @Override
    public void compute(Collection collection, List<TopicRun> run, ResultComponent results){
        //System.out.println("Start comupting " + name + "...");

        this.collection = collection;
        this.run = run;

        // the metric run set
        ResultSet metricResults  = new ResultSet("", this, ResultComponent.Type.METRIC);

        for (TopicRun topicRun: run) {

            // cycle only over the intersection of topics in run and qrels
            if(topicRun == null){
                 continue;
            } else if(collection.getQrelByTopic(topicRun.getIdTopic()) == null){
                 continue;
            }

            // compute the metric result for each topic (using momoization)
            Result result = compute(topicRun, collection);
            ResultMemoizetor.getInstance().saveMetric(topicRun.getIdTopic(), getMetricAcronym(),result);
            metricResults.add(result);
        }

        // compute over all metric result
        Result avaragedResult = computeOverallResult(metricResults, collection);
        metricResults.add(avaragedResult);

        // save metric results to the run result set
        results.add(metricResults);

       // System.out.println(name + " done!");
    }

    /**
     * This method computes the single metric for a single topic in the run and return its result.
     * This method before computing the metric result, it search if the metric was already computed somewhere and
     * it gives back the result if founded.
     *
     * @param topicRun the run for a specifc topic.
     * @param collection the test collection used in the evaluation process.
     * @return the metric result.
     */
    public Result compute(TopicRun topicRun, Collection collection){
        Result result = ResultMemoizetor.getInstance().getMetricResult(topicRun.getIdTopic(),getMetricAcronym());

        if(result!=null){
            return result;
        } else {
            return computeTopicResult(topicRun,collection);
        }
    }

    /**
     * The implementation of this method must compute the metric for a single topic in the run and return its result.
     *
     * @param topicRun the run for a specific topic.
     * @param collection the test collection used in the evaluation process.
     * @return the metric result like a Result (or subclass) instance.
     */
    protected abstract Result computeTopicResult(TopicRun topicRun, Collection collection);

    /**
     * The implementation of this method must compute the over all result of the metric across the run.
     * It can be computed iterating the result set given that contains all the results for each topic in the run.
     *
     * @param results the results of the metric for each topic in the run.
     * @param collection the test collection used in the evaluation process.
     * @return the metric result like a Result (or subclass) instance.
     */
    public abstract Result computeOverallResult(ResultSet results, Collection collection);

    /**
     * Returns the metric acronym.
     * @return metric acronym.
     */
    public String getMetricAcronym() {
        return acronym;
    }

    /**
     * Returns the complete metric name.
     * @return complete metric name.
     */
    public String getMetricCompleteName() {
        return completeName;
    }

    /**
     * Returns a short description of the metric.
     * @return description.
     */
    public String getMetricDescription() {
        return description;
    }

    /**
     * Informs if metric is computed just for over all results.
     * @return true if is a overall metric, false otherwise. Default is false.
     */
    public boolean isOverallMetric() {
        return overallMetric;
    }

    /**
     * This method is disabled for single metric.
     * @param component
     */
    @Override
    public void add(MetricComponent component) {
        throw new TrecEvalOOMetricException("Impossible to add in metric");
    }

    /**
     * This method is disabled for single metric.
     * @param component
     */
    @Override
    public void remove(MetricComponent component) {
        throw new TrecEvalOOMetricException("Impossible to add in metric");
    }

    @Override
    public ArrayList<Metric> getMetricList() {
        ArrayList<Metric> metricList = new ArrayList<>();
        metricList.add(this);
        return metricList;
    }
}
