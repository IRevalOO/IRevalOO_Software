package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric calculates the number of docs retrieved in the run.
 * The over all result is the sum of individual topics, not averaged.
 */
public class NumRet extends  Metric{

    public NumRet(){
        acronym = "NumRet";
        completeName = "Number of retrieved docs";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        return new NumericResult(topicRun.getIdTopic(), this, topicRun.size());
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.sumResults(results, this);
    }
}
