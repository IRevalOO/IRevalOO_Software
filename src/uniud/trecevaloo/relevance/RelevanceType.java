package uniud.trecevaloo.relevance;

import uniud.trecevaloo.exceptions.TrecEvalOOException;

/**
 * Implement this interface to manage new relevance types.
 */
public interface RelevanceType {

    /**
     * This method has the purpose to read correctly the relevance translating it in a double value used inside the system.
     * For example if the relevance found in the source of qrels are "NOT RELEVAT" and "RELEVANT" it can be translated in 0 and 1.
     *
     * @param value the value to read (Can be anything).
     * @return the value used in the system.
     * @throws TrecEvalOOException
     */
    double readValue(Object value) throws TrecEvalOOException;

    /**
     * This method has the purpose to define when such type of relevance judgment is relevant or not in a binary meaning. It's used
     * during the computation of binary metrics such as P, MAP, ...
     *
     * @param value the value.
     * @return true if it is relevant, otherwise false.
     */
    boolean isRelevant(double value);

    /**
     * This method has the purpose to define when such type of relevance judgment is unjudged. It is allowed to have
     * unjudged documents in the pool.
     * @param value the value.
     * @return true if it is unjudged, otherwise false.
     */
    boolean isUnjudged(double value);

}
