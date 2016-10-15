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
 * This metric computes relative Precision at cutoffs.
 * Precision at cutoff relative to the maximum possible precision at that cutoff.
 * Equivalent to Precision up until R, and then recall after R.
 * N value must be positive.
 */
public class RelativePAtN extends Metric {

    private int N = -1;

    public RelativePAtN(int N){
        acronym = "RelativeP_at_" + N;
        completeName = "Relative precision at cutoff " + N;


        if(N<0){
            throw new TrecEvalOOMetricException("N must be positive in P@N");
        }

        this.N = N;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double count = 0, relativePAtN = 0;

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        if(numRel <= 0){
            return new NumericResult(topicRun.getIdTopic(),this, relativePAtN);
        }

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

        if(N > 0){
            relativePAtN = count/(N > numRel ? numRel : N);
        }

        return new NumericResult(topicRun.getIdTopic(),this, relativePAtN);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
