package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOORunException;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.run.AdHocTRECRun;
import uniud.trecevaloo.run.RunSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is an example of how extend the framework with your own run set.
 */
public class MyTRECRunSet extends RunSet {

    private String runPath;

    public MyTRECRunSet(RelevanceType relevanceType, String runPath) {
        super(relevanceType);
        this.runPath = runPath;
    }

    @Override
    protected void importRunSet() {

        try {
            // read every file in the folder path and add the Run to the RunSet
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
