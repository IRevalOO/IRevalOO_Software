package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.relevance.Relevance;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.testcollection.Qrel;
import uniud.trecevaloo.testcollection.Topic;

/**
 *  This class is an example of how extend the framework with your own collection.
 */
public class MyCollection extends Collection {

    private String dbPath;

    public MyCollection(RelevanceType relevanceType, String dbPath) {
        super(relevanceType);
        this.dbPath = dbPath;
    }

    @Override
    protected void importDocumentCollection() throws TrecEvalOOException {
        // no documents to import
    }

    @Override
    protected void importTopicCollection() throws TrecEvalOOException {
        // some code to access and read the db topic table
        // for each topic in db{
                // read id
                // read description
                // read narrative
                // read category
                Topic topic = new MyTopic("id","description","narrative","category");
                addTopic(topic);
        //}
    }

    @Override
    protected void importQrelCollection() throws TrecEvalOOException {
        // some code to access and read the db qrels table
        // for each qrel in db{
                // read idDocument
                // read idTopic
                // read relevance
                Relevance relevance = new Relevance(getRelevanceType(),"relevance value in db");
                Qrel qrel = new Qrel("idDocument","idTopic",relevance);
                addQrel(qrel);
        //}
    }
}
