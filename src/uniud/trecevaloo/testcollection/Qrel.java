package uniud.trecevaloo.testcollection;

import uniud.trecevaloo.relevance.Relevance;

/**
 * This class represents a qrel. And it is composed only by idDocument, idTopic, relevance.
 */
public class Qrel {


    private String idDocument;
    private String idTopic;
    private Relevance relevance;

    /**
     * The constructor of a qrel.
     * @param idDocument document id.
     * @param idTopic topic id.
     * @param relevance relevance instance.
     */
    public Qrel(String idDocument, String idTopic, Relevance relevance){
        this.idDocument = idDocument;
        this.idTopic = idTopic;
        this.relevance = relevance;
    }

    /**
     * Get document id.
     * @return document id.
     */
    public String getIdDocument(){
        return idDocument;
    }

    /**
     * Get topic id.
     * @return topic id.
     */
    public String getIdTopic(){
        return idTopic;
    }

    /**
     * Get relevance.
     * @return relevance.
     */
    public Relevance getRelevance(){
        return relevance;
    }
}