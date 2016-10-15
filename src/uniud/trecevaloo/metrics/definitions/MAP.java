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
 * This is the MAP (Mean average precision) metric.
 * This is the precision measured after each relevant doc is retrieved then averaged over topics.
 * This is the main single-valued number used to compare the entire rankings of two or more retrieval methods.
 * It has proven in practice to be useful and robust.
 * History: Developed by Chris Buckley after TREC 1.
 * Cite: 'Retrieval System Evaluation', Chris Buckley and Ellen Voorhees.
 * Chapter 3 in TREC: Experiment and Evaluation in Information Retrieval edited by Ellen Voorhees and Donna Harman. MIT Press 2005
 * This MAP computes its value over all retrieved documents if N is not specified.
 * Otherwise is possible to compute MAP at cutoffs specifying N.
 * Attention: N must be positive.
 * Documents not judged doesn't influence the result.
 */
public class MAP extends Metric {

    private int N = -1;

    public MAP(){
        acronym = "MAP";
        completeName = "Mean average precision";
    }

    public MAP(int N){
        acronym ="MAP_at_" + N;
        completeName = "Mean average precision at cutoff " + N;

        if(N >= 0){
            this.N = N;
        } else {
            throw new TrecEvalOOMetricException("MAP : N must be positive");
        }
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double ap = 0, map = 0, relSoFar = 0;
        double retrived = topicRun.size();

        for (int i = 0 ; i < (N > -1 ?  N : retrived); i++) {

            if(i >= retrived){
                break;
            }

            RunLine runLine = topicRun.getRun().get(i);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            // se trovo un esito della run non giudicato nei qrels lo salto
            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                relSoFar++;
                ap += relSoFar / (i + 1);
            }
        }

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun, collection).toDouble();

        if(numRel > 0){
            map = ap/numRel;
        }

        return new NumericResult(topicRun.getIdTopic(),this,map);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
