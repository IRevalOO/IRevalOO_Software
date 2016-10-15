package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric calculates the number of topics considered in the metrics calculation.
 * Default si the number of topics in the intersection between topics in qrels and the run.
 * If the avgOverAllTopicsInCollection option is selected computes the number of topics in qrels.
 */
public class NumTopic extends Metric {


    public NumTopic(){
        acronym = "Topic_size";
        completeName = "Number of topics";
        overallMetric = true;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        if(!EvaluatorManager.avgOverAllTopicsInCollection){
            return new NumericResult(topicRun.getIdTopic(), this, 1);
        } else {
            return null;
        }
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        double numOfTopics;

        if(!EvaluatorManager.avgOverAllTopicsInCollection){
            return NumericResult.sumResults(results,this);
        } else {
            numOfTopics = collection.getTopicQrels().size();
            return new NumericResult(Result.ALL, this ,numOfTopics);
        }

    }
}
