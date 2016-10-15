package uniud.trecevaloo.run;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a portion of a run relative to a specific topic.
 */
public class TopicRun {

    private String runName;
    private String idTopic;
    private List<RunLine> run = new ArrayList<>();

    /**
     * The TopicRun constructor.
     * @param runName run name.
     * @param idTopic id topic.
     */
    TopicRun(String runName, String idTopic){
        this.runName = runName;
        this.idTopic = idTopic;
    }

    /**
     * set the run list.
     * @param run the outcomes list for this TopicRun.
     */
    public void setRun(List<RunLine> run) {
        this.run = run;
    }

    /**
     * Get topic id.
     * @return topic id.
     */
    public String getIdTopic() {
        return idTopic;
    }

    /**
     * Get the outcomes list.
     * @return outcomes list.
     */
    public List<RunLine> getRun() {
        return run;
    }

    /**
     * The number of outcomes in the TopicRun.
     * @return number of outcomes.
     */
    public int size(){
        return run.size();
    }

    /**
     * Add a single run line to this TopicRun.
     * @param runLine single run line.
     */
    public void add(RunLine runLine){
        run.add(runLine);
    }

    /**
     * Get rhe run name.
     * @return run name.
     */
    public String getRunName() {
        return runName;
    }
}
