package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes the utility considering relevant, not relevant, retrieved and not retrieved documents.
 *
 * This metric set evaluation based on contingency
 * table:               relevant  nonrelevant
 *      retrieved            a          b
 *      nonretrieved         c          d
 * where  utility = p1 * a + p2 * b + p3 * c + p4 * d and p1-4 are parameters (given on command line in that order).
 * Conceptually, each retrieved relevant doc is worth something positive to a user, each retrieved nonrelevant doc
 * has a negative worth, each relevant doc not retrieved may have a negative worth, and each nonrelevant doc not retrieved
 * may have a (small) positive worth.
 * The overall measure is simply a weighted sum of these values.
 * If p4 is non-zero, then '-N num_docs_in_coll' may also be needed - the standard results and rel_info files do not contain that information.
 * Default coefficients are: p1 = 1.0, p2 = -1.0, p3 = 0.0, p4 = 0.0.
 * Warning: Current version summary evaluation averages over all topics;
 * it could be argued that simply summing is more useful (but not backward compatible).
 */
public class Utility extends Metric {

    private double  relRetCoeff = 1.0,  relNotRetCoeff = 0.0,  notRelRetCoeff = -1.0,  notRelNotRetCoeff = 0.0, docsInCollection = 0.0;

    public Utility(){
        acronym = "Utility";
        completeName = "Utility";
    }

    public Utility(double docsInCollection){
        acronym = "Utility";
        this.docsInCollection = docsInCollection;
    }

    public Utility(double relRetCoeff, double relNotRetCoeff, double notRelRetCoeff, double notRelNotRetCoeff){
        acronym = "Utility";
        this.relRetCoeff = relRetCoeff;
        this.relNotRetCoeff = relNotRetCoeff;
        this.notRelRetCoeff = notRelRetCoeff;
        this.notRelNotRetCoeff = notRelNotRetCoeff;
    }

    public Utility(double relRetCoeff, double relNotRetCoeff, double notRelRetCoeff, double notRelNotRetCoeff, double docsInCollection){
        acronym = "Utility";
        this.relRetCoeff = relRetCoeff;
        this.relNotRetCoeff = relNotRetCoeff;
        this.notRelRetCoeff = notRelRetCoeff;
        this.notRelNotRetCoeff = notRelNotRetCoeff;
        this.docsInCollection = docsInCollection;
    }

    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        Metric numRelRetMetric = new NumRelRet();
        double numRelRet = numRelRetMetric.compute(topicRun,collection).toDouble();

        Metric numRetMetric = new NumRet();
        double numRet = numRetMetric.compute(topicRun,collection).toDouble();

        Metric numRelMetric = new NumRel();
        double numRel = numRelMetric.compute(topicRun,collection).toDouble();

        double numNotRelRet = numRet - numRelRet;
        double numRelNotRet = numRel - numRelRet;
        double numNotRelNotRet = docsInCollection + numRelRet - numRet - numRel;

        double utility = numRelRet * relRetCoeff + numNotRelRet * notRelRetCoeff + numRelNotRet * relNotRetCoeff + numNotRelNotRet * notRelNotRetCoeff;

        return new NumericResult(topicRun.getIdTopic(),this,utility);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
        return NumericResult.arithmeticMeanResults(results,this, collection);
    }
}
