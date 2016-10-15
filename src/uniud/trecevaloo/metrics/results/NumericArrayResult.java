package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOResultException;
import uniud.trecevaloo.metrics.definitions.Metric;

/**
 * This class creates a numeric array result. Result is a double array.
 */
public class NumericArrayResult extends Result {

    double[] values;

    /**
     * NumericArrayResult constructor.
     * @param idTopic the topic id.
     * @param metric the metric that use this result.
     * @param values the double array.
     */
    public NumericArrayResult(String idTopic, Metric metric, double[] values){
        super(idTopic, metric);
        this.values = values;
    }

    /**
     * This method is disabled in this class.
     * @return
     */
    @Override
    public double toDouble() {
        throw new TrecEvalOOResultException("toDouble() method is not accepted in this type of result");
    }

    /**
     * This method return a printable string of the result values.
     * @return the string of the result.
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

            resultStr += "Topic: " + getIdTopic() + "\t-\t" + getMetricAcronym() + "\tat\t" + i + ":\t" + valueStr + "\n";
        }

        return resultStr;
    }


    /**
     * Return the value in a specific array position.
     * @param index position.
     * @return the value.
     */
    public double getValue(int index){

        if(index < values.length){
            return values[index];
        } else {
            throw new TrecEvalOOResultException("index out of range in NumericArrayResult index: " + index + " array length: " + values.length );
        }
    }
}
