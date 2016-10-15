package uniud.trecevaloo.run;

import uniud.trecevaloo.exceptions.TrecEvalOORunException;
import uniud.trecevaloo.relevance.RelevanceType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class manages the run set importation of TREC ad Hoc runs. Given a path of a folder containing alla the runs
 * in files it imports every single run in the system.
 */
public class AdHocTRECRunSet extends  RunSet{

    private String runPath;

    /**
     * The constructor of an AdHoc TREC run set. Remember to call the super() constructor.
     * @param relevanceType the relevance type.
     * @param runPath the folder path.
     */
    public AdHocTRECRunSet(RelevanceType  relevanceType, String runPath){
        super(relevanceType);
        this.runPath = runPath;
    }

    /**
     * Given a folder path it imports all the run files.
     */
    @Override
    protected void importRunSet() {

        try {
            Files.walk(Paths.get(runPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    add(new AdHocTRECRun(filePath.getFileName().toString(),getRelevanceType(), filePath.toString()));
                }
            });
        } catch (IOException e) {
            throw new TrecEvalOORunException(e.toString());
        }
    }
}
