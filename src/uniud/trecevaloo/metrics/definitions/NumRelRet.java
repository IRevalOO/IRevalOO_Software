package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This metric calculates the number of relevant docs in the test collection retrieved.
 * The over all result is the sum of individual topics, not averaged.
 */
public class NumRelRet extends Metric{

    public NumRelRet(){
        acronym = "NumRelRet";
        completeName = "Number of relevant docs retrieved";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        int relRet = 0;
        for (RunLine runLine: topicRun.getRun()) {

            Qrel q = collection.getQrel(runLine.getIdDocument(),topicRun.getIdTopic());
            if(q == null){continue;}

            if(q.getRelevance().isRelevant()){
                relRet++;
            }
        }

        return new NumericResult(topicRun.getIdTopic(), this,relRet);
}

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.sumResults(results, this);
    }

}
