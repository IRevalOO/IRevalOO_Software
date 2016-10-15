package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes the RPrecison metric. Is the precision after R documents have been retrieved (Is like PatR).
 * R is the total number of relevant docs for the topic. This is a good single point measure for an entire retrieval ranking
 * that averages well since each topic is being averaged at an equivalent point in its result ranking. Note that this is
 * the point that Precision = Recall.
 * Cite: 'Retrieval System Evaluation', Chris Buckley and Ellen Voorhees.
 * Chapter 3 in TREC: Experiment and Evaluation in Information Retrieval
 * edited by Ellen Voorhees and Donna Harman.  MIT Press 2005.
 */
public class RPrec extends Metric {

    public RPrec(){
        acronym = "RPrec";
        completeName = "R precision";
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        PatN pAtRMetric = new PatN((int)numRel);
        double rPrec = pAtRMetric.compute(topicRun,collection).toDouble();

        return new NumericResult(topicRun.getIdTopic(),this,rPrec);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
