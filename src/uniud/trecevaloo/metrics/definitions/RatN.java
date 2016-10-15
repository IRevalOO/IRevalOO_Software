package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.exceptions.TrecEvalOOMetricException;
import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

/**
 * This metric computes the recall at cutoffs metric. It is the recall at a specific level cutoff in the ranking. If cutoff is larger
 * than the number of docs retrieved, then it is assumed non relevant docs fill in the rest. Recall is a fine single topic measure, but does not average well.
 * Not judged docs are ignored.
 * N value must be positive.
 */
public class RatN extends Metric {

    private int N;

    public RatN(int N){
        acronym = "R_at_"+ N;
        completeName = "Recall at cutoff " + N;

        if(N<=0){
            throw new TrecEvalOOMetricException("N must be positive in R@N");
        }

        this.N = N;
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        double count = 0;

        NumRel relMetric = new NumRel();
        double numRel= relMetric.compute(topicRun, collection).toDouble();

        for (int i = 0 ; i < N; i++) {

            if(i >= topicRun.size()){
                break;
            }

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                count++;
            }
        }

        double rAtN = 0;

        if(numRel > 0){
            rAtN = count/numRel;
        }

        return new NumericResult(topicRun.getIdTopic(),this, rAtN);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
