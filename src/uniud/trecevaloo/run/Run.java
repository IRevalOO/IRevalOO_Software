package uniud.trecevaloo.run;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;

import java.util.*;

/**
 * This class represents a run. Extend this class to create a specific type of run.
 */
public abstract class Run {

    private String name;
    private List<TopicRun> runList = new ArrayList<>();
    private HashMap<String, TopicRun> topicRuns = new HashMap<>();
    private RelevanceType relevanceType;
    private Collection collection;
    private int size = 0;

    /**
     * The run constructor.
     * @param name          the name of the run.
     * @param relevanceType the relevance type used by the run.
     */
    public Run(String name, RelevanceType relevanceType) {
        this.name = name;
        this.relevanceType = relevanceType;
    }

    /**
     * This method manages the importation of  a single run.
     */
    public void createRun(Collection collection) throws TrecEvalOOException {
        this.collection = collection;
        System.out.println("Importing run " + name + "...");

        this.importRun();

        runList = new ArrayList<>(topicRuns.values());

        for (TopicRun topicRun : runList) {
            // order TopicRun by relvance value.
            topicRun.getRun().sort((RunLine rl1, RunLine rl2) -> Double.compare(rl2.getRelevance().getValue(), rl1.getRelevance().getValue()));

            // if numOfDocsPerTopic is active consider only the first N docs, discard the rest.
            if (EvaluatorManager.numOfDocsPerTopic < Integer.MAX_VALUE) {
                if (EvaluatorManager.numOfDocsPerTopic < topicRun.size()) {
                    List<RunLine> newRun = topicRun.getRun().subList(0, EvaluatorManager.numOfDocsPerTopic);
                    topicRun.setRun(newRun);
                }
            }

            size += topicRun.size();
        }

        System.out.println("Run " + name + " imported.");
    }

    public void dismiss(){
        runList.clear();
        topicRuns.clear();
        size = 0;
    }

    /**
     * This method must be implemented to manage the run importation.
     * @throws TrecEvalOOException If some exception must be throw do it like this: throw new TrecEvalOORunException(e.toString()); where e is the exception catched.
     */
    protected abstract void importRun() throws TrecEvalOOException;

    /**
     * Return run like a list of TopicRun.
     * @return a TopicRun list .
     */
    public List<TopicRun> getRun() {
        return runList;
    }

    /**
     * The number of outcomes in the Run.
     * @return number of outcomes.
     */
    public int size() {
        return size;
    }

    /**
     * Return the run name.
     * @return run name.
     */
    public String getName() {
        return name;
    }

    /**
     * The relevance type of the collection.
     * @return relevance type.
     */
    public RelevanceType getRelevanceType() {
        return relevanceType;
    }

    /**
     * Add a outcomes in the run.
     * @param runLine single run line.
     */
    public void add(RunLine runLine) {
        if (EvaluatorManager.onlyJudgedDocs) {
            Qrel q = collection.getQrel(runLine.getIdDocument(), runLine.getIdTopic());
            if (q == null) {
                return;
            } else if (q.getRelevance().isUnjudged()) {
                return;
            }
        }

        TopicRun topicRun = topicRuns.get(runLine.getIdTopic());

        if (topicRun == null) {
            topicRun = new TopicRun(name, runLine.getIdTopic());
            topicRun.add(runLine);
            topicRuns.put(runLine.getIdTopic(), topicRun);
        } else {
            topicRun.add(runLine);
        }
    }
}