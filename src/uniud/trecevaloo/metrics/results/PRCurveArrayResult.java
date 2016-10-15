package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.metrics.definitions.PRCurve;

/**
 * This class represents a PRCurve result. It overrides the toString() method printing the results properly.
 */
public class PRCurveArrayResult extends NumericArrayResult {

    /**
     * PRCurveArrayResult constructor.
     * @param idTopic id topic.
     * @param metric the PR curve metric.
     * @param values the PR curve values.
     */
    public PRCurveArrayResult(String idTopic, PRCurve metric, double[] values) {
        super(idTopic, metric, values);
    }

    /**
     *  This method return a printable string of the PR curve results.
     * @return he string of the result.
     */
    @Override
    public String toString() {
        String resultStr = "";

        for (int i=0; i < values.length; i++){

            String valueStr;
            double decimal = values[i] - Math.floor(values[i]);

            if(decimal!=0){
                valueStr = String.format("%.4f",values[i]);
            } else {
                valueStr = String.format("%.0f",values[i]);
            }

            resultStr += "Topic: " + getIdTopic() + "\t-\t" + getMetricAcronym() + "_at_recall_level_" + i + ":\t" + valueStr + "\n";
        }

        return resultStr;
    }
}
