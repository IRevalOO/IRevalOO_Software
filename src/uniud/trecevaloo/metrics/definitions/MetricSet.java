package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultComponent;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a set of metrics.
 */
public class MetricSet implements MetricComponent{

    List<MetricComponent> metrics = new ArrayList<>();

    /**
     * Add a metric (or a metric set) to this metric set.
     * @param component metric to add.
     */
    @Override
    public void add(MetricComponent component) {
        metrics.add(component);
    }

    /**
     * Remove a metric (or a metric set) to this metric set.
     * @param component metric to remove.
     */
    @Override
    public void remove(MetricComponent component) {
        metrics.remove(component);
    }

    /**
     * Iterate the metrics in the metric set computing each metric.
     *
     * @param collection the test collection used in the evaluation process.
     * @param run the run evaluated in the evaluation process.
     * @param results the result set where the metric can save its result.
     */
    @Override
    public void compute(Collection collection, List<TopicRun> run, ResultComponent results) {
        for (MetricComponent m: metrics ) {
            m.compute(collection, run, results);
        }
    }

    public ArrayList<Metric> getMetricList() {
        ArrayList<Metric> metricList = new ArrayList<>();

        for (MetricComponent metric: metrics) {
            if(metric!=null) {
                List<Metric> metricResults = metric.getMetricList();
                metricList.addAll(metricResults);
            }
        }

        return metricList;
    }
}
