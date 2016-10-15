package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.metrics.results.NumericArrayResult;
import uniud.trecevaloo.metrics.results.PRCurveArrayResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.RunLine;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

import java.util.List;

/**
 * This metric computes the interpolated precision at recall cutoffs metric. This is the data shown in the standard Recall-Precision graph.
 * It is the interpolated precision at recall cutoffs. The precision interpolation
 * used here is P(rankX) = max(P(rankY)) for all y greater than or equal to x.
 *
 * The default recall level cutoffs are : 0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0 .
 * (That it means 0, 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%).
 *
 * The result is an array containing all the precision values computed at the cutoffs.
 */
public class PRCurve extends Metric {

    private static final int LEVELS_NUM = 11;
    private static final double[] recallLevels = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};


    public PRCurve(){
        acronym="P&RCurve";
        completeName = "Precision & Recall curve";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {

        double[] pValues = new double[LEVELS_NUM];
        long[] cutoffs = new long[LEVELS_NUM];
        int currentCut;
        long relSoFar;
        double precision, intPrecision;

        NumRel numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        NumRelRet numRelRetMetric = new NumRelRet();
        double numRelRet = numRelRetMetric.compute(topicRun,collection).toDouble();

        int numRet = topicRun.size();

        // translate the percentage levels in terms of relevant documents.
        // The + 0.9 is derived from the implementation of trec_eval (file: m_imprec_at_recall.c line: 65).
        for (int i=0 ; i< LEVELS_NUM; i++){
            cutoffs[i] = (long)(recallLevels[i] * numRel + 0.9);
        }

        currentCut = LEVELS_NUM - 1;
        while (currentCut >= 0 && cutoffs[currentCut] > numRelRet){
            currentCut--;
        }

        precision =  numRelRet / numRet;
        intPrecision = precision;
        relSoFar = (long) numRelRet;

        // scroll the documents retrieved in reverse order. Eit is necessary to do it in reverse order as the interpolation
        // function for the precision is : intPrecision(X) = MAX(precision(Y)) for every Y >= X.
        for (int i = numRet ; i > 0 && relSoFar > 0 ; i--){
            precision = (double) relSoFar / (double) i;
            if(intPrecision < precision){
                intPrecision = precision;
            }

            RunLine runLine = topicRun.getRun().get(i-1);
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());

            if(q==null){
                continue;
            }

            if(q.getRelevance().isRelevant()){
                while (currentCut >= 0 && relSoFar == cutoffs[currentCut]) {
                    pValues[currentCut] = intPrecision;
                    currentCut--;
                }
                relSoFar--;
            }
        }

        while (currentCut >= 0){
            pValues[currentCut] = intPrecision;
            currentCut--;
        }

        return new PRCurveArrayResult(topicRun.getIdTopic(),this,pValues);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        double[] pValuesAvg = new double[LEVELS_NUM];

        List<Result> resultsList = results.getResultsByMetric(getMetricAcronym());

        for (int i = 0; i < LEVELS_NUM; i++) {
            for (Result result : resultsList) {
                if (result instanceof NumericArrayResult) {
                    pValuesAvg[i] += ((NumericArrayResult) result).getValue(i);
                }
            }
        }

        for (int i = 0; i < LEVELS_NUM; i++) {

            double topicNum;
            if(EvaluatorManager.avgOverAllTopicsInCollection){
                topicNum = collection.getTopicQrels().size();
            } else {
                topicNum = resultsList.size();
            }

            pValuesAvg[i] = pValuesAvg[i] / topicNum;
        }

        return new PRCurveArrayResult(Result.ALL, this, pValuesAvg);
    }
}
