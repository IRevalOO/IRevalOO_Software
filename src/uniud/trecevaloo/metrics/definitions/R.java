package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes the R (recall) over all the document retrieved for a topic. R = numRelRet/numRel.
 */
public class R extends Metric{


    public R(){
        acronym = "R";
        completeName = "Recall";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        NumRelRet numRelRetMetric = new NumRelRet();
        double numRelRet = numRelRetMetric.compute(topicRun, collection).toDouble();

        NumRel numRelMetric  = new NumRel();
        double numRel = numRelMetric .compute(topicRun, collection).toDouble();

        double r = 0;

        if(numRel > 0){
            r = numRelRet/numRel;
        }

        return new NumericResult(topicRun.getIdTopic(),this,r);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
