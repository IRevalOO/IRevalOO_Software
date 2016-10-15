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
 * This metric computes Success at cutoffs.
 * Success (a relevant doc has been retrieved) measured at various doc level cutoffs in the ranking.
 * If the cutoff is larger than the number of docs retrieved, then it is assumed nonrelevant docs fill in the rest.
 * History: Developed by Stephen Tomlinson.
 * N value must be positive.
 */
public class Success extends Metric {

    private int N = -1;

    public Success(int N){
        acronym="Success_at_" + N;
        completeName = "Success at cutoff " + N;

        if(N<0){
            throw new TrecEvalOOMetricException("N must be positive in P@N");
        }

        this.N = N;
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double success = 0.0;
        for (int i = 0 ; i < N; i++) {

            if (i >= topicRun.size()) {
                break;
            }

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                success = 1.0;
            }
        }

        return new NumericResult(topicRun.getIdTopic(),this, success);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
