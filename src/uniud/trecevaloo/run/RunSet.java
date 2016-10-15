package uniud.trecevaloo.run;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.testcollection.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a set of run. Extend this class to create a specific set of run.
 */
public abstract class RunSet {

    private RelevanceType relevanceType;
    private List<Run> runSet  = new ArrayList<>();

    /**
     * The run set constructor.
     * @param relevanceType the relevance type used by the runs.
     */
    public RunSet(RelevanceType relevanceType){
        this.relevanceType = relevanceType;
    }

    /**
     * This method manages the importation of  multiple runs.
     */
    public void createRunSet(Collection collection)  throws TrecEvalOOException {
        this.importRunSet();
       /* for (Run run: runSet) {
            System.out.println("Importing run " + run.getName() + "...");
            run.createRun(collection);
        }*/
    }

    /**
     * This method must be implemented to manage the runs importation.
     * @throws TrecEvalOOException If some exception must be throw do it like this: throw new TrecEvalOORunException(e.toString()); where e is the exception catched.
     */
    protected abstract  void importRunSet() throws TrecEvalOOException;

    /**
     * Add a run to run set.
     * @param run the run.
     */
    public void add(Run run){
        runSet.add(run);
    }

    /**
     * The relevance type used in the run set.
     * @return relevance type.
     */
    public RelevanceType getRelevanceType() {
        return relevanceType;
    }

    /**
     * Return the list of run in the run set.
     * @return run list.
     */
    public List<Run> getRuns() {
        return runSet;
    }
}
