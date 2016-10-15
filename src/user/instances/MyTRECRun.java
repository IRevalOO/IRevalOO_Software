package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOORunException;
import uniud.trecevaloo.relevance.Relevance;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.run.AdHocTRECRunLine;
import uniud.trecevaloo.run.Run;
import uniud.trecevaloo.run.RunLine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is an example of how extend the framework with your own run.
 */
public class MyTRECRun extends Run {


    String runPath;
    private static final int IDTOP_RUN = 0;
    private static final int IDDOC_RUN = 2;
    private static final int RANK_RUN = 3;
    private static final int REL_RUN = 4;


    public MyTRECRun(String name, RelevanceType relevanceType, String runPath) {
        super(name, relevanceType);
        this.runPath = runPath;
    }

    @Override
    protected void importRun() {

        BufferedReader buffer = null;
        String sCurrentLine;

        try {

            buffer = new BufferedReader(new FileReader(runPath));
            // read lines
            while ((sCurrentLine = buffer.readLine()) != null) {

                sCurrentLine = sCurrentLine.trim();
                if(sCurrentLine.isEmpty()){
                    continue;
                }
                // slipt lines fields
                String[] strings = sCurrentLine.split("\\s+");
                // add RunLine to the Run
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
