package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric calculates the number of relevance judgments (qrels) in the collection.
 */
public class NumQrels extends Metric {

    public NumQrels(){
        acronym = "Qrel_size";
        completeName = "Number of qrels";
        overallMetric = true;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        return null;
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return new NumericResult(Result.ALL, this, collection.getQrels().size());
    }
}
