package uniud.trecevaloo.run;

import uniud.trecevaloo.relevance.Relevance;

/**
 * This class is an  instance example of the framework for a single run outcome.
 * It implements a run line in ad Hoc TREC format.
 */
public class AdHocTRECRunLine extends  RunLine{

    public AdHocTRECRunLine(String idDocument, String idTopic, Relevance relevance, int rank){
        super(idDocument,idTopic,relevance,rank);
    }

}
