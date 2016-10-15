package uniud.trecevaloo.run;

import uniud.trecevaloo.relevance.Relevance;

/**
 * This class represents a single run line (outcome). Extend this class to create a specific type of run line.
 */
public abstract class RunLine {

    private String idDocument;
    private String idTopic;
    private Relevance relevance;
    private int rank;

    /**
     * The RunLine constructor.
     * @param idDocument document id.
     * @param idTopic topic id.
     * @param relevance the relevance according to the retrieval system.
     * @param rank the rank position.
     */
    public RunLine(String idDocument, String idTopic, Relevance relevance, int rank){
        this.idDocument = idDocument;
        this.idTopic = idTopic;
        this.relevance = relevance;
        this.rank = rank;
    }

    /**
     * Get the document id.
     * @return document id.
     */
    public String getIdDocument() {
        return idDocument;
    }

    /**
     * Get the topic id.
     * @return topic id.
     */
    public String getIdTopic() {
        return idTopic;
    }

    /**
     * Get rhe rank.
     * @return rank position.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Get the relevance.
     * @return the relevance.
     */
    public Relevance getRelevance() {
        return relevance;
    }
}
