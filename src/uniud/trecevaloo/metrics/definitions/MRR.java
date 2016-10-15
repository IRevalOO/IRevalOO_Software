package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This metric computes the MRR (Mean Reciprocal Rank) metric.
 * It computes the reciprocal rank of the first relevant retrieved doc.
 * This metric is most useful for tasks in which there is only one relevant doc, or the user only wants one relevant doc.
 */
public class MRR extends Metric {

    public MRR(){
        acronym = "MRR";
        completeName = "Mean reciprocal rank";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double recpRank = 0;

        for (int i = 0 ; i < topicRun.size() ; i++){
            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                recpRank = 1.0/(i+1.0);
                break;
            }
        }

        return new NumericResult(topicRun.getIdTopic(),this,recpRank);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
