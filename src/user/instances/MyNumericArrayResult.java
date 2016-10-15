package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOOResultException;
import uniud.trecevaloo.metrics.definitions.Metric;
import uniud.trecevaloo.metrics.results.Result;

/**
 * This class is an example of how extend the framework with your own result type.
 */
public class MyNumericArrayResult extends Result {

        private double[] values;

        public MyNumericArrayResult(String idTopic, Metric metric, double[] values){
            super(idTopic, metric);
            this.values = values;
        }

        @Override
        public double toDouble() {
            throw new TrecEvalOOResultException("toDouble is not accepted in this type of result");
        }

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

        public double getValue(int index){

            if(index < values.length){
                return values[index];
            } else {
                throw new TrecEvalOOResultException("index out of range in NumericArrayResult index: " + index + " array length: " + values.length );
            }
        }
}

