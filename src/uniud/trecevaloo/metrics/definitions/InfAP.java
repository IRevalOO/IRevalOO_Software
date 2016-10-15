package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This metric allow to sampling the judgement pool: Qrels/results divided
 * into unpooled, pooled_but_unjudged, pooled_judged_rel,pooled_judged_nonrel.
 * My intuition of infAP:
 * Assume a judgment pool with a random subset that has been judged.
 * Calculate P at rel doc using only the judged higher retrieved docs,
 * then average in 0's from higher docs that were not in the judgment pool.
 *  (Those in the pool but not judged are ignored, since they are assumed
 *  to be relevant in the same proportion as those judged.)
 * Cite:  'Estimating Average Precision with Incomplete and Imperfect
 * Judgments', Emine Yilmaz and Javed A. Aslam. CIKM.
 */

public class InfAP extends Metric {

    private static final double INFAP_EPSILON = 0.00001;

    public InfAP(){
        acronym="InfAP";
        completeName = "Inferred AP";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        int relSoFar = 0, nonRelSoFar = 0, unjudgedSoFar = 0;
        double infAP = 0.0;

        for (int i = 0 ; i < topicRun.size() ; i++){

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            } else if(q.getRelevance().isUnjudged()){
                unjudgedSoFar++;
                continue;
            }


            if(!q.getRelevance().isRelevant()){
                nonRelSoFar++;
            } else {
                relSoFar++;
                if(i == 0){
                    infAP += 1.0;
                } else {
                    double j = i;
                    infAP += 1.0 / (j + 1.0) +  (j / (j+1.0)) *
                            ((relSoFar - 1 + nonRelSoFar + unjudgedSoFar) / j) *
                            ((relSoFar - 1 + INFAP_EPSILON) / (relSoFar - 1 + nonRelSoFar + 2 * INFAP_EPSILON));

                }
            }
        }

        Metric numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        if(numRel > 0){
            infAP /= numRel;
        }

        return  new NumericResult(topicRun.getIdTopic(),this,infAP);

    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
