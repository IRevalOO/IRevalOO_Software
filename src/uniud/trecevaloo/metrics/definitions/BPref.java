package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This metric computes the Bpref measure. It is the fraction of top R non relevant docs that are retrieved after each relevant doc.
 * Documents not judged are ignored.  Put another way: when looking at the R relevant docs, and the top R nonrelevant docs,
 * if all relevant docs are to be preferred to nonrelevant docs, Bpref is the fraction of the preferences that the ranking preserves.
 * Cite: 'Retrieval Evaluation with Incomplete Information', Chris Buckley and Ellen Voorhees. In Proceedings of 27th SIGIR, 2004.
 */
public class BPref extends Metric {

    public BPref(){
        acronym = "BPref";
        completeName = "Main binary preference metric";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        int numNotRelDocs = 0;
        double bPrefValue = 0;

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();
        NumNotRel numNotRelMetric = new NumNotRel();
        double numNotRel =  numNotRelMetric.compute(topicRun,collection).toDouble();
        
        for (int i = 0 ; i < topicRun.size() ; i++){

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                if(numNotRelDocs > 0) {
                    bPrefValue += 1.0 - (Math.min(numNotRelDocs, numRel) / Math.min(numRel, numNotRel));
                } else {
                    bPrefValue += 1.0;
                }
            } else {
                numNotRelDocs++;
            }
        }

        if(numRel>0){
            bPrefValue = bPrefValue/numRel;
        } else {
            bPrefValue = 0;
        }

        return new NumericResult(topicRun.getIdTopic(),this,bPrefValue);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
