package user.instances;

import uniud.trecevaloo.metrics.definitions.*;

/**
 * This class is an example of how extend the framework with your own metric set builder.
 */
public class MyMetricSetBuilder extends MetricSetBuilder {


    public MyMetricSetBuilder() {
        super();
    }

    @Override
    protected MetricSet create() {

        MetricSet metrics = new MetricSet();
        metrics.add(new BPref());
        metrics.add(new PatN(10));
        metrics.add(new R());

        return metrics;

    }
}
