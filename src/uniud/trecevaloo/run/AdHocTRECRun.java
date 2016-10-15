package uniud.trecevaloo.run;

import uniud.trecevaloo.exceptions.TrecEvalOORunException;
import uniud.trecevaloo.relevance.Relevance;
import uniud.trecevaloo.relevance.RelevanceType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class manages the TREC ad hoc run importation. Given a path of a file it imports the run.
 */
public class AdHocTRECRun extends Run {

    String runPath;
    private static final int IDTOP_RUN = 0;
    private static final int IDDOC_RUN = 2;
    private static final int RANK_RUN = 3;
    private static final int REL_RUN = 4;

    /**
     * The constructor of an AdHoc TREC run. Remember to call the super() constructor.
     * @param name run name.
     * @param relevanceType the relevance type.
     * @param runPath the run file path.
     */
    public AdHocTRECRun(String name, RelevanceType relevanceType, String runPath){
        super(name,relevanceType);
        this.runPath = runPath;
    }

    /**
     * Import Ad hoc TREC run in the run set from file.
     */
    @Override
    protected void importRun() {

        BufferedReader buffer = null;
        String sCurrentLine;

        try {

            buffer = new BufferedReader(new FileReader(runPath));

            while ((sCurrentLine = buffer.readLine()) != null) {

                sCurrentLine = sCurrentLine.trim();
                if(sCurrentLine.isEmpty()){
                    continue;
                }
                String[] strings = sCurrentLine.split("\\s+");

                Relevance relevance = new Relevance(this.getRelevanceType(), strings[REL_RUN]);
                RunLine runLine = new AdHocTRECRunLine(strings[IDDOC_RUN], strings[IDTOP_RUN], relevance, Integer.parseInt(strings[RANK_RUN]));
                add(runLine);
            }

        } catch (IOException e) {
            throw new TrecEvalOORunException(e.toString());
        } finally {
            try {
                if (buffer != null)buffer.close();
            } catch (IOException ex) {
                throw new TrecEvalOORunException(ex.toString());
            }
        }
    }
}
