package uniud.trecevaloo.testcollection;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.relevance.RelevanceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a generic test collection. If you want to create a new test collection format extend this class.
 * It manages the importation of documents, topics and qrels.
 * Every collection needs a RelevanceType that define the kind of relevance used in its qrels.
 *
 * No multiple qrel are admitted!
 */
public abstract class Collection {

    private  List<Document> documents = new ArrayList<>();
    private  List<Topic> topics = new ArrayList<>();
    private  List<Qrel> qrelsList = new ArrayList<>();
    private  List<TopicQrel> topicQrelList;
    private  RelevanceType relevanceType;
    private  HashMap<String, TopicQrel> qrels = new HashMap<>();

    /**
     * The collection constructor.
     * @param relevanceType the relevance used by qrels.
     */
    public Collection(RelevanceType relevanceType){
        this.relevanceType = relevanceType;
    }

    /**
     * This method manages the importation of  documents, topics and qrels. I also create the qrels has-map.
     */
     public void createCollection() throws TrecEvalOOException{
         System.out.println("Start importing collection:");
         System.out.println("Importing documents...");
         this.importDocumentCollection();
         System.out.println("Importing topics...");
         this.importTopicCollection();
         System.out.println("Importing qrels...");
         this.importQrelCollection();
         this.createQrelsHashMap();
         System.out.println("Collection imported.\n");
    }

    /**
     * This method must be implemented to manage the document importation.
     * @throws TrecEvalOOException If some exception must be throw do it like this: throw new TrecEvalOOCollectionException(e.toString()); where e is the exception catched.
     */
    protected abstract void importDocumentCollection() throws TrecEvalOOException;

    /**
     * This method must be implemented to manage the topic importation.
     * @throws TrecEvalOOException If some exception must be throw do it like this: throw new TrecEvalOOCollectionException(e.toString()); where e is the exception catched.
     */
    protected abstract void importTopicCollection() throws TrecEvalOOException;

    /**
     * This method must be implemented to manage the qrels importation.
     * @throws TrecEvalOOException If some exception must be throw do it like this: throw new TrecEvalOOCollectionException(e.toString()); where e is the exception catched.
     */
    protected abstract void importQrelCollection() throws TrecEvalOOException;

    /**
     * Get the document list.
     * @return document list.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    public double docforTopicRateo(){
        int sum = 0;
        List<TopicQrel> topicQrelList = new ArrayList<>(qrels.values());

        for (TopicQrel t: topicQrelList) {
            sum += t.getQrels().size();
        }
        return sum/topicQrelList.size();
    }

    /**
     * Get the topic list.
     * @return topic list.
     */
    public List<Topic> getTopics() {
        return topics;
    }

    /**
     * Get the qrel list.
     * @return qrel list.
     */
    public List<Qrel> getQrels() {
        return qrelsList;
    }

    /**
     * Get the qrels hash-map.
     * @return  qrels hash-map.
     */
    public HashMap<String,TopicQrel> getTopicQrels() {return  qrels; }

    /**
     * Get the qrels of a given topic.
     * @param idTopic topic id.
     * @return an instance of TopicQrel.
     */
    public TopicQrel getQrelByTopic(String idTopic){
        TopicQrel topicQrel = qrels.get(idTopic);

        if(topicQrel != null){
            return topicQrel;
        } else {
            return null;
        }
    }

    /***
     * Return the qrel concerning the id document e the id topic. If no qrel is found return null.
     * If is null means that this qrel is not present in the collection, therefore probably the document
     * for this topic is not judged.
     *
     * @param idDocument id document.
     * @param idTopic id topic.
     * @return the qrel or null if not found.
     */
    public Qrel getQrel(String idDocument, String idTopic){

        /*for (Qrel q: qrelsList) {
            if(q.getIdDocument().equals(idDocument) && q.getIdTopic().equals(idTopic)){
                return q;
            }
        }

        return null;*/

        TopicQrel topicQrel = qrels.get(idTopic);

        if(topicQrel != null){
            return topicQrel.getQrel(idDocument);
        } else {
            return null;
        }
    }

    /**
     * The relevance type of the collection.
     * @return relevance type.
     */
    public RelevanceType getRelevanceType() {
        return relevanceType;
    }

    /**
     * Add a document to the collection.
     * @param document the document.
     */
    public void addDocument(Document document){
        documents.add(document);
    }

    /**
     * Add a topic to the collection.
     * @param topic the topic.
     */
    public void addTopic(Topic topic){
        topics.add(topic);
    }

    /**
     * Add a qrel to the collection.
     * @param qrel the qrel.
     */
    public void addQrel(Qrel qrel){
        qrelsList.add(qrel);
    }

    /**
     * Create the hash map of qrels. (Only for internal usage, to increase computation performance).
     */
    private void createQrelsHashMap(){

        for (Qrel qrel: qrelsList) {
            TopicQrel t = qrels.get(qrel.getIdTopic());

            if(t==null){
                t =  new TopicQrel(qrel.getIdTopic());
                t.add(qrel);
                qrels.put(qrel.getIdTopic(),t);
            } else {
                t.add(qrel);
            }
        }
    }
}
