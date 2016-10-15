package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes the precision over all documents retrieved for a topic. P = numRelRet/numRet.
 */
public class P extends Metric{

    public P(){
        acronym = "P";
        completeName = "Precision";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        NumRelRet numRelRetMetric = new NumRelRet();
        double numRelRet = numRelRetMetric.compute(topicRun,collection).toDouble();

        NumRet numRetMetric = new NumRet();
        double numRet = numRetMetric.compute(topicRun,collection).toDouble();

        double p = 0;
        if(numRet > 0){
            p = numRelRet/numRet;
        }

        return new NumericResult(topicRun.getIdTopic(), this, p);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
