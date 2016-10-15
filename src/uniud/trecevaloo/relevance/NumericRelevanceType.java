package uniud.trecevaloo.relevance;

import uniud.trecevaloo.exceptions.TrecEvalOORelevanceException;

/**
 * This class  manage any numerical relevance type.
 */
public class NumericRelevanceType  implements RelevanceType{

    private double relevantLimit;


    public NumericRelevanceType(double relevantLimit){
        if(relevantLimit >= 0){
            this.relevantLimit = relevantLimit;
        } else {
            throw new TrecEvalOORelevanceException("numberOfCategories cant be negative");
        }
    }

    /**
     * It reads correctly the symbol and gives the value used inside the system.
     * Is used by the system to read correctly a relevance when you are importing run and qrels.
     *
     * @param value the value to read (Can be anything).
     * @return the value used in the system.
     */
    @Override
    public double readValue(Object value) {
        return Double.parseDouble((String) value);
    }

    /**
     * It defines if a relevance judgment is relevant or not. (Used by the system).
     * @param value the value.
     * @return true if it is relevant, otherwise false.
     */
    @Override
    public boolean isRelevant(double value) {
        return (value >= relevantLimit);
    }

    /**
     * It defines if a relevance is unjudged.
     * @param value the value.
     * @return true if it is unjudged, otherwise false.
     */
    @Override
    public boolean isUnjudged(double value) {
        return false;
    }
}
