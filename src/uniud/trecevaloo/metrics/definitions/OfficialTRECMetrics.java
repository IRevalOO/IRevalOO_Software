package uniud.trecevaloo.metrics.definitions;

/**
 * This class create a metric set containing all the metrics of the 'official' set in Trec_eval 9.0.
 */
public class OfficialTRECMetrics extends MetricSetBuilder {

    public OfficialTRECMetrics(){}

    @Override
    protected MetricSet create() {
        MetricSet metrics = new MetricSet();

        metrics.add(new NumRet());
        metrics.add(new NumRel());
        metrics.add(new NumRelRet());

        metrics.add(new MAP());
        metrics.add(new GMAP());
        metrics.add(new RPrec());
        metrics.add(new BPref());
        metrics.add(new MRR());
        metrics.add(new PRCurve());

        metrics.add(new PatN(5));
        metrics.add(new PatN(10));
        metrics.add(new PatN(15));
        metrics.add(new PatN(20));
        metrics.add(new PatN(30));
        metrics.add(new PatN(100));
        metrics.add(new PatN(200));
        metrics.add(new PatN(500));
        metrics.add(new PatN(1000));

        metrics.add(new NDCG());

        return metrics;

    }
}
