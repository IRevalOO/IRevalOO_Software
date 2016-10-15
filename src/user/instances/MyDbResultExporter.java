package user.instances;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.metrics.results.ResultComponent;
import uniud.trecevaloo.metrics.results.ResultExporter;


public class MyDbResultExporter implements ResultExporter {

    private String dbPath;

    public MyDbResultExporter(String dbPath){
        this.dbPath = dbPath;
    }

    @Override
    public void export(ResultComponent results) throws TrecEvalOOException {
        // some code to scan the results and load each result in db
    }
}
