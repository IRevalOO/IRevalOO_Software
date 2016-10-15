package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.exceptions.TrecEvalOORelevanceException;
import uniud.trecevaloo.relevance.RelevanceType;

/**
 * This class is an example of how extend the framework with your own relevance type.
 */
public class MyCategoryRelevanceType implements RelevanceType {

    public MyCategoryRelevanceType(){}

    @Override
    public double readValue(Object value) throws TrecEvalOOException {
        if(value.equals("-")){
            return -1;
        } else if(value.equals("NOT RELEVANT")){
            return 0;
        } else if(value.equals("RELEVANT")){
            return 1;
        } else if (value.equals("VERY RELEVANT")){
            return 2;
        } else {
            throw new TrecEvalOORelevanceException("unreadable category relevance " + value);
        }
    }

    @Override
    public boolean isRelevant(double value) {
        return value >= 1;
    }

    @Override
    public boolean isUnjudged(double value) {
        return value < 0;
    }
}
