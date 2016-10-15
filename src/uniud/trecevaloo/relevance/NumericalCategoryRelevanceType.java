package uniud.trecevaloo.relevance;

import uniud.trecevaloo.exceptions.TrecEvalOORelevanceException;

/**
 * This class manages any numerical category relevance type. All it needs is to define the number of categories and
 * the  limit category  under which a judgment is consider not relevant in binary metrics.
 */
public class NumericalCategoryRelevanceType implements RelevanceType {

    private int numOfCategories;
    private int relevantLimit;

    /**
     * The NumericalCategoryRelevanceType constructor.
     *
     * @param numOfCategories number of categories.
     * @param relevantLimit relevant limit.
     */
    public NumericalCategoryRelevanceType(int numOfCategories, int relevantLimit){

        if(numOfCategories > 0){
            this.numOfCategories = numOfCategories;
        } else {
            throw new TrecEvalOORelevanceException("numberOfCategories must be positive");
        }
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
     * In this case check if is a correct category number.
     *
     * @param value the value to read (Can be anything).
     * @return the value used in the system.
     */
    @Override
    public double readValue(Object value) {
        int numericValue = Integer.parseInt((String) value);
        if(numericValue < numOfCategories){
            return numericValue;
        } else {
            throw new TrecEvalOORelevanceException("unreadable category relevance " + value);
        }

    }

    /**
     * It defines if a relevance judgment is relevant or not. (All is greater than relevantLimit is relevant).
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
        return (value < 0);
    }


}
