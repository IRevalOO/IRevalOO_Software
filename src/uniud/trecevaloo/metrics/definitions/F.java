package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * The F-measure computes the weighted harmonic mean of recall and precision, like F = (x+1) * P / (R + x*P).
 * x is the relative importance of R to P (Deafult is 1.0).
 * Cite: Variant of van Rijsbergen's E measure ('Information Retrieval', Butterworths, 1979).
 */
public class F extends Metric {

    private int importanceOfRtoP = 1;

    public F(){
        acronym = "F";
        completeName = "F-measure";
    }

    public F(int importanceOfRtoP){
        acronym = "F";
        this.importanceOfRtoP = importanceOfRtoP;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        P pMetric = new P();
        double p = pMetric.compute(topicRun,collection).toDouble();

        R rMetric = new R();
        double r = rMetric.compute(topicRun,collection).toDouble();

        double x = (r + importanceOfRtoP*p);
        double f = 0;

        if(x != 0){
          f  = ((importanceOfRtoP + 1) *  p * r) / x;
        }

        return new NumericResult(topicRun.getIdTopic(), this,f);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
