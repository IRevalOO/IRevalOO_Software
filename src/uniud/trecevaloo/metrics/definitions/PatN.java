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
 * This metric computes the precision at cutoffs metric. It is the precision at a specific level cutoff in the ranking.
 * If cutoff is larger than the number of docs retrieved, then it is assumed non relevant docs fill in the rest.
 * Not judged docs are ignored. Precision is a very nice user oriented measure, and a good comparison
 * number for a single topic, but it does not average well.
 * N value must be positive.
 */
public class PatN extends Metric {

    private int N;

    public PatN(int N){
        acronym = "P_at_" + N;
        completeName = "Precision at cutoff " + N;

        if(N<0){
            throw new TrecEvalOOMetricException("N must be positive in P@N");
        }

        this.N = N;
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double count = 0;

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

        double pAtN = 0;

        if(N>0){
            pAtN = count/N;
        }

        return new NumericResult(topicRun.getIdTopic(),this, pAtN);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
