package uniud.trecevaloo.metrics.definitions;

/**
 * This class is a builder for metric sets. If you want create automatically a specific metric set extend this class
 * and implement the method create(). Remember to create also the public constructor that call super().
 */
public abstract class MetricSetBuilder {

    private MetricSet metricSet;

    /**
     * The metric set builder constructor.
     */
    public MetricSetBuilder(){
        this.metricSet = create();
    }

    /**
     * Implement this method  to create the metric set.
     * @return the metric set.
     */
    protected abstract MetricSet create();

    /**
     * Return the metric set created.
     * @return metric set.
     */
    public MetricSet getMetricSet() {
        return metricSet;
    }
}
