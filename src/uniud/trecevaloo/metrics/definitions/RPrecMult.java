package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * Precision measured at multiples of R (num_rel).
 * This is an attempt to measure topics at the same multiple milestones in a retrieval (see explanation of R-prec),
 * in order to determine  whether methods are precision oriented or recall oriented.
 * If method A dominates method B at the low multiples but performs less well at the
 * high multiples then it is precision oriented (compared to B).
 */
public class RPrecMult extends Metric {

    private double multiple;

    public RPrecMult(double multiple){
        this.multiple = multiple;
        acronym = "RPrecMult_"+multiple;
        completeName = "R Precision at multiple " + multiple;
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        numRel = numRel * multiple + 0.9;

        PatN pAtRMetric = new PatN((int)numRel);
        double rPrec = pAtRMetric.compute(topicRun,collection).toDouble();

        return new NumericResult(topicRun.getIdTopic(),this,rPrec);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
