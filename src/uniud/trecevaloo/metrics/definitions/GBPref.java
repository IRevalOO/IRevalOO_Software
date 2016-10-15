package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * GBPref computes Bpref, but using geometric mean over topics.
 * See the explanation for 'Bpref' for the base measure for a single topic.
 * It uses the geometric mean to combine the single topic scores.
 * This rewards methods that are more consistent across topics as opposed to
 * high scores for some topics and low scores for others.
 * GBPref is printed only as a summary measure across topics, not for the individual topics.
 */
public class GBPref extends Metric {

    public GBPref(){
        acronym="GBPref";
        overallMetric = true;
        completeName = "Geometric binary preference metric";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        BPref bPrefMetric = new BPref();
        double bpref = bPrefMetric.compute(topicRun,collection).toDouble();
        return new NumericResult(topicRun.getIdTopic(),this,bpref);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.geometricMeanResults(results,this, collection);
    }
}
