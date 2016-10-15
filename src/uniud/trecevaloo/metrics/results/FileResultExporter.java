package uniud.trecevaloo.metrics.results;

import uniud.trecevaloo.exceptions.TrecEvalOOResultException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * This class  manages the results exportation to file sources. It needs the file path where create the file.
 */
public class FileResultExporter implements ResultExporter {

    private String filePath;

    /**
     * FileResultExporter constructor.
     * @param filePath the file path.
     */
    public FileResultExporter(String filePath){
        this.filePath = filePath;
    }

    /**
     * This method export the results.
     * @param results the results to export.
     */
    @Override
    public void export(ResultComponent results) {
        String resultsStr = results.toString();

        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath, "UTF-8");
            writer.println(resultsStr);
            writer.close();
            System.out.println("\nResults exported to file: " + filePath);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new TrecEvalOOResultException(e.toString());
        }
    }
}
