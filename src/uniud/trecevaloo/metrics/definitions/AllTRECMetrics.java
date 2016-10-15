package uniud.trecevaloo.metrics.definitions;

/**
 * This class create a metric set containing all the metrics already implemented in this framework.
 */
public class AllTRECMetrics extends MetricSetBuilder {


    public AllTRECMetrics(){
        super();
    }

    @Override
    protected MetricSet create() {

        MetricSet metrics = new MetricSet();
        metrics.add(new NumRet());
        metrics.add(new NumRel());
        metrics.add(new NumRelRet());

        metrics.add(new NumNotRel());
        metrics.add(new NumNotRelRet());

        metrics.add(new MAP());
        metrics.add(new GMAP());
        metrics.add(new RPrec());
        metrics.add(new BPref());
        metrics.add(new MRR());
        metrics.add(new PRCurve());


        metrics.add(new P());
        metrics.add(new PatN(5));
        metrics.add(new PatN(10));
        metrics.add(new PatN(15));
        metrics.add(new PatN(20));
        metrics.add(new PatN(30));
        metrics.add(new PatN(100));
        metrics.add(new PatN(200));
        metrics.add(new PatN(500));
        metrics.add(new PatN(1000));

        metrics.add(new R());
        metrics.add(new RatN(5));
        metrics.add(new RatN(10));
        metrics.add(new RatN(15));
        metrics.add(new RatN(20));
        metrics.add(new RatN(30));
        metrics.add(new RatN(100));
        metrics.add(new RatN(200));
        metrics.add(new RatN(500));
        metrics.add(new RatN(1000));

        metrics.add(new InfAP());
        metrics.add(new GBPref());

        metrics.add(new F());

        metrics.add(new RPrecMult(0.2));
        metrics.add(new RPrecMult(0.4));
        metrics.add(new RPrecMult(0.6));
        metrics.add(new RPrecMult(0.8));
        metrics.add(new RPrecMult(1.0));
        metrics.add(new RPrecMult(1.2));
        metrics.add(new RPrecMult(1.4));
        metrics.add(new RPrecMult(1.6));
        metrics.add(new RPrecMult(1.8));
        metrics.add(new RPrecMult(2.0));



        metrics.add(new MAP(5));
        metrics.add(new MAP(10));
        metrics.add(new MAP(15));
        metrics.add(new MAP(20));
        metrics.add(new MAP(30));
        metrics.add(new MAP(100));
        metrics.add(new MAP(200));
        metrics.add(new MAP(500));
        metrics.add(new MAP(1000));




        metrics.add(new NDCG());
        metrics.add(new NDCG(5));
        metrics.add(new NDCG(10));
        metrics.add(new NDCG(15));
        metrics.add(new NDCG(20));
        metrics.add(new NDCG(30));
        metrics.add(new NDCG(100));
        metrics.add(new NDCG(200));
        metrics.add(new NDCG(500));
        metrics.add(new NDCG(1000));

        metrics.add(new Utility());

        metrics.add(new RelativeP());
        metrics.add(new RelativePAtN(5));
        metrics.add(new RelativePAtN(10));
        metrics.add(new RelativePAtN(15));
        metrics.add(new RelativePAtN(20));
        metrics.add(new RelativePAtN(30));
        metrics.add(new RelativePAtN(100));
        metrics.add(new RelativePAtN(200));
        metrics.add(new RelativePAtN(500));
        metrics.add(new RelativePAtN(1000));

        metrics.add(new Success(1));
        metrics.add(new Success(5));
        metrics.add(new Success(10));

        return metrics;
    }
}
