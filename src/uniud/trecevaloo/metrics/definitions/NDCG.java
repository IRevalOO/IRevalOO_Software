package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.exceptions.TrecEvalOOMetricException;
import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;
import uniud.trecevaloo.testcollection.TopicQrel;

import java.util.List;

/**
 * This metric computes the NDCG metric according to Jarvelin e Kekalainen (ACM ToIS v. 20, pp. 422-446, 2002).
 * Based on an implementation by Ian Soboroff.
 * This NDCG computes its value over all retrieved documents if N is not specified.
 * Otherwise is possible to compute NDCG at cutoffs specifying N.
 * Attention: N must be positive.
 * Documents not judged influence the value with a relevance of 0 (not relevant).
 */

public class NDCG extends Metric{

    private int N = -1;

    public NDCG(){
        acronym ="NDCG";
        completeName = "Normalized Discounted Cumulative Gain";
    }

    // NDCG at cutoff
    public NDCG(int N){
        acronym ="NDCG_at_"+N;
        completeName = "Normalized Discounted Cumulative Gain at cutoff " + N;

        if(N >= 0){
            this.N = N;
        } else {
            throw new TrecEvalOOMetricException("NDCG : N must be positive");
        }
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        TopicQrel topicQrel = collection.getQrelByTopic(topicRun.getIdTopic());
        // if there is no topic in qrels the metric return 0
        if(topicQrel == null){
            return new NumericResult(topicRun.getIdTopic(), this, 0);
        }

        double dcg = 0, relevance, ndcg = 0;
        double retrived = topicRun.size();
        //System.out.println(retrived);

        List<Qrel> qrels = topicQrel.getQrels(); // qrels are already ordered by relevance
        double idcg = 0, idealRelevance = 0;
        int i = 0;

        while(i < (N > -1 ?  N : retrived)) {

            if(i < retrived) {
                // compute DCG
                RunLine runLine = topicRun.getRun().get(i);
                Qrel q = topicQrel.getQrel(runLine.getIdDocument());

                if(q!=null){
                    relevance = q.getRelevance().getValue();
                    if( relevance < 0){
                        relevance = 0;
                    }
                } else {
                    relevance = 0;
                }

                dcg +=  relevance/(Math.log(i+2)/Math.log(2));
            }

            // compute IDCG
            if(i < qrels.size()){
                idealRelevance = qrels.get(i).getRelevance().getValue();
                if( idealRelevance < 0){
                    idealRelevance = 0;
                }
            } else {
                idealRelevance = 0;
            }

            idcg +=  idealRelevance/(Math.log(i+2)/Math.log(2));

            //System.out.println("topic " + topicRun.getIdTopic() + " i "  +  i + " idealRelevance: " + idealRelevance + " idcg: " + idcg);
            i++;
        }

        if(N == -1) {
            // do not compute this if is NDCG at cutoff
            while (i < qrels.size() && idealRelevance > 0.0) {
                // calculate change in ideal dcg
                idealRelevance = qrels.get(i).getRelevance().getValue();

                if (idealRelevance > 0) {
                    idcg += idealRelevance / (Math.log(i + 2) / Math.log(2));
                }

                //System.out.println(" i "  +  i + " idealRelevance " + idealRelevance + " idcg " + idcg);
                i++;
            }
        }

        //System.out.println("topic " + topicRun.getIdTopic() + " dcg: " + dcg + " idcg: " + idcg);
        if(idcg > 0){
            ndcg = dcg/idcg;
        }

        return new NumericResult(topicRun.getIdTopic(),this,ndcg);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
