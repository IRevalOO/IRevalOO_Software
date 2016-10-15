package user.instances;

import uniud.trecevaloo.metrics.definitions.Metric;
import uniud.trecevaloo.metrics.definitions.NumRel;
import uniud.trecevaloo.metrics.definitions.PatN;
import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This class is an example of how extend the framework with your own metric. In this case is MAP.
 */
public class MAP extends Metric {


    public MAP(){
        acronym = "MAP";
        completeName="Mean Average Precision";
        description="This metric calculate....";
        overallMetric = false;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double ap = 0, map = 0;
        double retrived = topicRun.size();

        for (int i = 0 ; i < retrived; i++) {

            if(i >= retrived){
                break;
            }

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            // not consider outcomes not in the pool
            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                Metric PatIMetric = new PatN(i+1);
                double pAtI = PatIMetric.compute(topicRun,collection).toDouble();
                ap += pAtI;
            }
        }

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun, collection).toDouble();

        if(numRel > 0){
            map = ap/numRel;
        }

        return new NumericResult(topicRun.getIdTopic(),this,map);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}

