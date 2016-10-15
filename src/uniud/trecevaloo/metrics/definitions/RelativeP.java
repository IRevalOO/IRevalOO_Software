package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes Relative Precision:  P / (Max possible P for this size set).
 * Relative precision over all docs retrieved for a topic.
 */
public class RelativeP extends Metric{

    public RelativeP(){
        acronym = "RelativeP";
        completeName = "Relative precision";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        NumRelRet numRelRetMetric = new NumRelRet();
        double numRelRet = numRelRetMetric.compute(topicRun,collection).toDouble();

        NumRet numRetMetric = new NumRet();
        double numRet = numRetMetric.compute(topicRun,collection).toDouble();

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        double relativeP = 0;
        if(numRet > 0 && numRel > 0){
            relativeP = numRelRet/Math.min(numRel,numRet);
        }

        return new NumericResult(topicRun.getIdTopic(), this, relativeP);

    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this,collection);
    }
}
