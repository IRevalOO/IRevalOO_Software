package uniud.trecevaloo.metrics.definitions;

/**
 * This class create a metric set containing some statistics,
 * such as: number of qrels in the collection, number of topics cosidered in the computation.
 */
public class StatisticsMetrics extends MetricSetBuilder {

    @Override
    protected MetricSet create() {
        MetricSet metricSet = new MetricSet();
        metricSet.add(new NumQrels());
        metricSet.add(new NumTopic());
        return  metricSet;
    }
}
