package uniud.trecevaloo.control;

import uniud.trecevaloo.exceptions.TrecEvalOOException;
import uniud.trecevaloo.metrics.definitions.MetricComponent;
import uniud.trecevaloo.metrics.definitions.MetricSet;
import uniud.trecevaloo.metrics.definitions.MetricSetBuilder;
import uniud.trecevaloo.metrics.results.*;
import uniud.trecevaloo.run.Run;
import uniud.trecevaloo.run.RunSet;
import uniud.trecevaloo.run.TopicRun;
import uniud.trecevaloo.testcollection.Collection;
import uniud.trecevaloo.metrics.definitions.StatisticsMetrics;

import java.util.List;

/**
 * This class manages the evaluation process using the right test collection and runs to compute the metric set.
 * It also provides the result visualization and exportation methods.
 */
public class EvaluatorManager {

    private Collection collection;
    private RunSet runSet;
    private MetricComponent metrics;
    private ResultComponent results;
    private    boolean computationDone;
    static public   boolean onlyJudgedDocs;
    static public   boolean avgOverAllTopicsInCollection;
    static public   int numOfDocsPerTopic;
    public double time = 0;

    /**
     * Constructor of the EvaluatorManager.
     * @param collection the collection.
     * @param runSet the run set.
     * @param metrics the metric set (or single metric).
     */
   public EvaluatorManager(Collection collection, RunSet runSet, MetricComponent metrics){
       this.collection = collection;
       this.runSet = runSet;
       this.metrics = metrics;
       avgOverAllTopicsInCollection = false;
       onlyJudgedDocs = false;
       computationDone = false;
       numOfDocsPerTopic = Integer.MAX_VALUE;
       results = new ResultSet("",null, ResultComponent.Type.GENERAL);
   }


    /**
     * Compute the evaluation process.
     */
    public void evaluate(){
        // start time
        long start_time = System.nanoTime();

        try {
            importation();
        } catch (TrecEvalOOException e){
            e.printStackTrace();
            computationDone = false;
            return;
        }

        if(metrics == null){
            System.out.println("\nNo metrics to compute.\n");
            computationDone = false;
            return;
        }


        int totalRunLines = 0;

        System.out.println("START COMPUTING METRICS:");

        MetricSetBuilder statisticsBuilder = new StatisticsMetrics();
        MetricSet statistics = statisticsBuilder.getMetricSet();

        // compute all runs
        for (Run run: runSet.getRuns()) {
            // run is imported just before the calculation
            run.createRun(collection);

            ResultMemoizetor.getInstance().init();
            totalRunLines += run.size();

            List<TopicRun> runEachTopic = run.getRun();

            // create run results set
            ResultSet runResults = new ResultSet(run.getName(),null, ResultComponent.Type.RUN);

            // compute some run statistics
            statistics.compute(collection, runEachTopic,runResults);

            // start metric computation
            metrics.compute(collection,runEachTopic,runResults);

            // add the run results
            this.results.add(runResults);

            System.out.println("Computation for run " + run.getName() + " done.");

            // manually clean has-map and array-list that contains the run
            // (is necessary otherwise you may run into a java.lang.OutOfMemoryError:GC overhead limit exceeded)
            // if you compute a lot of big run files
            run.dismiss();
        }

            // end time
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;

        computationDone = true;
        System.out.println("\nCOMPUTATION SUCCESSFULLY COMPLETED.");
        System.out.println("Time elapsed: " + difference/1000 + " seconds\n");
        time = difference/1000;
        System.out.println("Statistics: ");
        System.out.println("Number of runs: " + runSet.getRuns().size());
        System.out.println("Total of runLines: " + totalRunLines);
        System.out.println("Total of qrels: " + collection.getQrels().size() + "\n\n");
        //System.out.println("Doc for topic rateo (in qrels): " + collection.docforTopicRateo() + "\n\n");
    }

    /**
     * Set EvaluatorManager to calculate all values only over the judged documents (either relevant or not relevant).
     * All unjudged documents are removed from the retrieved set before any calculation.
     * Possibly leaving an empty set, DO NOT USE, unless you really know what you're doing.
     */
    public void considerOnlyJudgedDocs() {
        onlyJudgedDocs = true;
    }

    /**
     * Set the number of documents per topic to consider in the evaluation of the run.
     * @param numberOfDocsPerTopic number of docs per topic.
     */
    public void setNumberOfDocsPerTopic(int numberOfDocsPerTopic) {
        numOfDocsPerTopic = numberOfDocsPerTopic;
    }

    /**
     * Set EvaluatorManager to average over the complete set of topics in the relevance judgements
     * instead of the topics in the intersection of relevance judgements (qrels)
     * and the run.  Missing topics will contribute a value of 0 to all
     * evaluation measures (which may or may not be reasonable for a
     * particular evaluation measure, but is reasonable for standard TREC
     * measures.) Default is off.
     */
    public void averageOverAllTopicsInCollection() {avgOverAllTopicsInCollection = true; }

    /**
     * Manage the importation of runs and collection.
     * @throws TrecEvalOOException if the importation failed it throws an exception.
     */
    private void importation() throws TrecEvalOOException{

        System.out.println("\nSTART IMPORTATION.\n");

        // import collection
        if(collection != null){
            try {
                collection.createCollection();
            } catch (TrecEvalOOException e){
                System.out.println("\nTEST COLLECTION IMPORT FAILED. Computation not executed.\n");
                    throw e;
            }
        }

        // import run set
        if(runSet!=null){
            try {
                runSet.createRunSet(collection);
            } catch (TrecEvalOOException e){
                System.out.println("\nRUN IMPORT FAILED. Computation not executed.\n");
                throw e;
            }
        }

        System.out.println("\nIMPORTATION DONE.\n");
    }

    /**
     * Export the results according to the ResultExporter passed.
     * @param resultExporter instance of ResultExporter that define the type of exportation.
     */
    public void exportResults(ResultExporter resultExporter){
        try {
            if(computationDone){
                resultExporter.export(results);
            }
        } catch (TrecEvalOOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the results according to the ResultViewer passed.
     * @param resultViewer instance of ResultViewer that define the type of visualization.
     */
    public void showResults(ResultViewer resultViewer){
        try {
            if(computationDone) {
                resultViewer.show(results);
            }
        } catch (TrecEvalOOException e) {
            e.printStackTrace();
        }
    }
}
