package uniud.trecevaloo.metrics.definitions;

import uniud.trecevaloo.metrics.results.NumericResult;
import uniud.trecevaloo.metrics.results.Result;
import uniud.trecevaloo.metrics.results.ResultSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;

/**
 * This metric computes the GMAP (Geometric Mean Average Precision) metric.
 * For each topic the result is the same of MAP, but the geometric mean is calculated when averaging over topics.
 * This rewards methods that are more consistent over topics as opposed to methods which do very well for some topics
 * but very poorly for others. GMAP is reported only in the summary over all topics, not for individual topics.
 */
public class GMAP extends Metric {


    public GMAP(){
        acronym = "GMAP";
        overallMetric = true;
        completeName = "Geometric mean average precision";
    }


    @Override
    public Result computeTopicResult(TopicRun topicRun, Collection collection) {
        MAP mapMetric = new MAP();
        double map =  mapMetric.compute(topicRun,collection).toDouble();
        return new NumericResult(topicRun.getIdTopic(), this, map);
    }

    @Override
    public Result computeOverallResult(ResultSet results, Collection collection) {
       return NumericResult.geometricMeanResults(results,this, collection);
    }
}
