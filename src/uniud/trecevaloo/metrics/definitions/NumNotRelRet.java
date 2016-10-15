package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 *  This metric calculates the number of not relevant JUDGED documents retrieved for each topic in the test collection.
 *  The over all result is the sum of individual topics, not averaged.
 */
public class NumNotRelRet extends Metric {

    public NumNotRelRet(){
        acronym = "NumNotRelRet";
        completeName = "Number of not relevant docs retrieved";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        int notRelRet = 0;
        for (RunLine runLine: topicRun.getRun()) {

            Qrel q = collection.getQrel(runLine.getIdDocument(),topicRun.getIdTopic());
            if(q == null){continue;}

            if(q.getRelevance().isUnjudged()){
                continue;
            }

            if(!q.getRelevance().isRelevant()){
                notRelRet++;
            }
        }

        return new NumericResult(topicRun.getIdTopic(), this,notRelRet);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.sumResults(results, this);
    }
}
