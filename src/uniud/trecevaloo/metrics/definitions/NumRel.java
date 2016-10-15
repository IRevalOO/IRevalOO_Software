package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;
import uniud.trecevaloo.testcollection.TopicQrel;

/**
 *  This metric calculates the number of relevant docs in the test collection.
 *  The over all result is the sum of individual topics, not averaged.
 */
public class NumRel extends Metric {

    public NumRel(){
        acronym = "NumRel";
        completeName = "Number of relevant docs";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        int numRel = 0;
        TopicQrel topicQrel = collection.getQrelByTopic(topicRun.getIdTopic());

        if(topicQrel==null){
            return new NumericResult(topicRun.getIdTopic(),this,0);
        }

        for (Qrel q: topicQrel.getQrels()) {
            if(q.getRelevance().isRelevant()){
                numRel++;
            }
        }

        return new NumericResult(topicRun.getIdTopic(),this,numRel);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.sumResults(results, this);
    }
}
