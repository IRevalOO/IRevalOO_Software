package uniud.trecevaloo.testcollection;

import uniud.trecevaloo.exceptions.TrecEvalOOCollectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the qrels concerning a specific topic. (It's used internally by the system).
 */
public class TopicQrel {

    private String idTopic;
    private HashMap<String,Qrel> qrels = new HashMap<>();

    /**
     * The constructor.
     * @param idTopic the id topic.
     */
    TopicQrel(String idTopic){
        this.idTopic = idTopic;
    }

    /**
     * Get the topic id.
     * @return topic id.
     */
    public String getIdTopic() {
        return idTopic;
    }

    /**
     * Give the lists of qrel concerning this specific topic.
     * @return qrel list.
     */
    public List<Qrel> getQrels() {
        List<Qrel> qrelsList = new ArrayList<>(qrels.values());
        // order the qrels by relevance descending,  useful for some metrics like IDCG
        qrelsList.sort((Qrel o1, Qrel o2)->Double.compare(o2.getRelevance().getValue(), o1.getRelevance().getValue()));

        return qrelsList;
    }

    /**
     * Get the qrel given its idDocument.
     * @param idDocument id document.
     * @return the qrel.
     */
    public Qrel getQrel(String idDocument){
        return qrels.get(idDocument);
    }

    /**
     * Add a qrel to the set of qrels for this topic. Check if already exists.
     * @param qrel the qrel.
     */
    public void add(Qrel qrel){
        Qrel q = qrels.get(qrel.getIdDocument());

        if(q!= null){
            throw new TrecEvalOOCollectionException("Multiple occurence of qrel for topic: " + q.getIdTopic() + ", document: " + q.getIdDocument());
        } else {
            qrels.put(qrel.getIdDocument(), qrel);
        }
    }

}
