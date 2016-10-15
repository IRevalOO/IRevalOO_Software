package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.ResultComponent;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

import java.util.ArrayList;
import java.util.List;
/**
 * This interface implements the methods needed for the composition of metric items (Metric Set and Metric).
 * It is necessary to realize the Composite design pattern to compose metric sets.
 */
public interface MetricComponent {
    /**
     * See MetricSet.
     *
     * @param component
     */
    void add(MetricComponent component);

    /**
     * See MetricSet.
     *
     * @param component
     */
    void remove(MetricComponent component);

    /**
     * The implementation of this method compute metrics in the composition.
     *
     * @param collection the test collection used in the evaluation process.
     * @param run        the run evaluated in the process.
     * @param results    the result set where the metric can save its result.
     */
    void compute(Collection collection, List<TopicRun> run, ResultComponent results);

    public abstract ArrayList<Metric> getMetricList();
}