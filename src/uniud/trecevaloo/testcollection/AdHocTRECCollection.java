package uniud.trecevaloo.testcollection;

import uniud.trecevaloo.exceptions.TrecEvalOOCollectionException;
import uniud.trecevaloo.relevance.Relevance;
import uniud.trecevaloo.relevance.RelevanceType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is an example instance of the framework for the collection.
 * It implements the methods to import an Ad Hoc TREC format test collection from file sources.
 */
public class AdHocTRECCollection extends Collection{

    private String documentPath;
    private String topicPath;
    private String qrelPath;

    private static final int  ID_TOP_QREL = 0;
    private static final int  ID_DOC_QREL = 2;
    private static final int  REL = 3;

    /**
     * The constructor of an AdHoc TREC collection. Remember to call the super() constructor.
     *
     * @param relevanceType is the instance of the right relevance type used in qrels.
     * @param documentPath the file path for the documents.
     * @param topicPath the file path for the topics.
     * @param qrelPath the file path for qrels.
     */
    public AdHocTRECCollection(RelevanceType relevanceType, String documentPath, String topicPath, String qrelPath){
        super(relevanceType);
        this.documentPath = documentPath;
        this.topicPath = topicPath;
        this.qrelPath = qrelPath;
    }

    /**
     * Import Ad hoc TREC documents in the collection from file.
     */
    @Override
    protected void importDocumentCollection() {
    }

    /**
     * Import Ad hoc TREC topics in the collection from file.
     */
    @Override
    protected void importTopicCollection() {
    }


    /**
     * Import Ad hoc TREC qrels in the collection from file.
     */
    @Override
    protected void importQrelCollection() {
        BufferedReader buffer = null;
        String sCurrentLine;

        try {
            buffer = new BufferedReader(new FileReader(qrelPath));

            while ((sCurrentLine = buffer.readLine()) != null) {
                sCurrentLine = sCurrentLine.trim();
                if(sCurrentLine.isEmpty()){
                    continue;
                }

                String[] strings = sCurrentLine.split("\\s+");

                Relevance relevance = new Relevance(this.getRelevanceType(), strings[REL]);
                Qrel qrel = new Qrel(strings[ID_DOC_QREL], strings[ID_TOP_QREL],relevance);
                addQrel(qrel);
            }

        } catch (IOException | RuntimeException  e) {
            throw new TrecEvalOOCollectionException(e.toString());
        } finally {
            try {
                if (buffer != null)buffer.close();
            } catch (IOException ex) {
                throw new TrecEvalOOCollectionException(ex.toString());
            }
        }
    }

}
