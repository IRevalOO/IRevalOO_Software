package uniud.trecevaloo.relevance;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.exceptions.TrecEvalOORelevanceException;

/**
 * This class represents a relevance value.
 */
public class Relevance {

    private RelevanceType type;
    private double value;

    /**
     * The constructor it needs the relevance type and the relevance value.
     * @param relevanceType the relevance type.
     * @param value relevance value.
     */
    public Relevance(RelevanceType relevanceType, Object value){
        this.type = relevanceType;
        try {
            this.value = type.readValue(value);
        }catch (TrecEvalOORelevanceException e) {
            this.value = Double.NaN;
            throw new TrecEvalOOException(e.toString());
        }
    }

    /**
     * Return the relevance value.
     * @return value in double.
     */
    public double getValue() {
        return value;
    }

    /**
     * Return the relevance type.
     * @return the relevance type.
     */
    public RelevanceType getType() {
        return type;
    }

    /**
     * This method defines if the relevance judgment is relevant or not in binary metrics.
     * @return true if is relevant, false otherwise.
     */
    public boolean isRelevant(){
        return type.isRelevant(value);
    }

    /**
     * This method defines if the relevance judgment is unjudged.
     * @return true if is unjudged, false otherwise.
     */
    public boolean isUnjudged(){ return  type.isUnjudged(value); }
}


