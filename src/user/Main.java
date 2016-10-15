package user;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.metrics.definitions.*;
import uniud.trecevaloo.metrics.results.*;
import uniud.trecevaloo.relevance.*;
import uniud.trecevaloo.run.AdHocTRECRunSet;
import uniud.trecevaloo.run.RunSet;
import uniud.trecevaloo.testcollection.AdHocTRECCollection;
import uniud.trecevaloo.testcollection.Collection;

import java.io.*;
import java.util.ArrayList;
/**
 * If you want you can use this Main class to initialize your evaluation manger. Instance the collection, run set and
 * metrics in the main function, give it to the EvaluatorManager, start evaluation. Then manage your results.
 */
public class Main {




    private static String qrelPath = "";
    private static String runPath = "";
    private static String outFilePath = "";
    private static int numOfDocsFlag = -1;
    private static boolean onlyJudgedFlag = false;
    private static boolean allTopicsFlag = false;
    private static boolean helper = false;

    private static MetricSet metrics = new OfficialTRECMetrics().getMetricSet();
    private static ResultViewer resultViewer = null;
    private static ResultExporter resultExporter = null;
    private static RelevanceType relevanceType = new NumericalCategoryRelevanceType(3,1);
    private static RelevanceType runRelevanceType = new NumericRelevanceType(1.0);


    public static void main(String[] args) throws IOException {

        // getFlags and input parameters for a trec_eval execution
        checkInputFlags(args);

        if(helper){
            printHelper();
            return;
        }

        if( runPath.isEmpty()){
            System.out.println("Missing run path (or runs folder path)");
            return;
        } else if(qrelPath.isEmpty()){
            System.out.println("Missing qrels path");
            return;
        }

        System.out.println();
        System.out.println("qrelPath: " + qrelPath);
        System.out.println("runPath: " + runPath);
        System.out.println();

        // collection
        Collection collection = new AdHocTRECCollection(relevanceType,"","",qrelPath);
        // run
        RunSet runSet = new AdHocTRECRunSet(runRelevanceType,runPath);

        EvaluatorManager evaluatorManager = new EvaluatorManager(collection,runSet,metrics);

        // options
        if(numOfDocsFlag > -1){
            System.out.println("Num of docs per topic: " + numOfDocsFlag);
            evaluatorManager.setNumberOfDocsPerTopic(numOfDocsFlag);
        }

        if(onlyJudgedFlag){
            System.out.println("Consider only judged docs: active");
            evaluatorManager.considerOnlyJudgedDocs();
        }

        if(allTopicsFlag){
            System.out.println("Average over all topics in collection: active");
            evaluatorManager.averageOverAllTopicsInCollection();
        }

        // start computing...
        evaluatorManager.evaluate();

        // show summury results
        evaluatorManager.showResults(resultViewer);

        // Export results in a file
        if(!outFilePath.isEmpty()){
            resultExporter = new FileResultExporter(outFilePath);
            evaluatorManager.exportResults(resultExporter);
        }

    }


    static void printHelper(){
        System.out.println(
                " \n----------------------------------------------------\n" +
                " ----------------- TRECEVALOO HELPER ----------------\n" +
                " ----------------------------------------------------\n\n" +
                "This command line interface is meant only for the default TREC ad Hoc classes already implemented by default in the framework.\n" +
                "The options are limited respect the potentiality of this framework and his user custom instances. \n\n" +
                "There are a fair number of options, of wich only the lower case options are normally used:\n\n" +
                "   -h: print full help message of how to use the software and exit.\n" +
                "   -Q <qrelspath>: set the path to the qrels trec ad hoc file. \n" +
                "   -R <runpath>: set the path to the run trec ad hoc file, or a folder path which contains all the runs. \n" +
                "   -m <metrics>: set the measure list to compute. Default metric lists: \n " +
                "    'all' computes all the metrics in implemented by default in the framework. \n " +
                "    'official' computes the official trec metrics of 'trec_eval'.\n" +
                "   -J:  calculate all values only over the judged (either relevant or  \n" +
                "     nonrelevant) documents.  All unjudged documents are removed from the \n" +
                "     retrieved set before any calculations (possibly leaving an empty set). \n" +
                "     DO NOT USE, unless you really know what you're doing - very easy to get \n" +
                "     reasonable looking numbers in a file that you will later forget were \n" +
                "     calculated  with the -J flag.\n" +
                "   -c: Average over the complete set of queries in the relevance judgements  \n" +
                "     instead of the queries in the intersection of relevance judgements \n" +
                "     and results.  Missing queries will contribute a value of 0 to all \n" +
                "     evaluation measures (which may or may not be reasonable for a  \n" +
                "     particular evaluation measure, but is reasonable for standard TREC \n" +
                "     measures.) Default is off.\n" +
                "   -M <num>: Max number of docs per topic to use in evaluation (discard rest). Default is MAX_LONG. \n" +
                "   -v <mode:params>: this flag set the result visualization modality. \n" +
                "      You can use 'all', 'overall'(default), 'run_all:<runname>', 'run_overall:<runname>', 'topic:<topicname>', 'metric:<metricname>'.\n" +
                "   -e <path>: use this flag to export the results to file. You must specify the file path where export the results.\n" +
                "   -r <modality:params>: use this flag to set the right relevance type used in the collection. You can use tags like: \n" +
                "      'binary' (default) where 0 is not relevant and 1 is relevant. \n" +
                "      'category:<num_of_categoies>,<relevance_limit>' for numeric category relevance for example: -r category:4,2 is a category relevance\n" +
                "      whit 4 categories and all the categories grater than 2 are relevant in binary metrics. \n" +
                "      'numeric:<relevance_limit>' which represents a numeric relevance like a Double.\n"
        );
    }


    static void checkInputFlags(String[] args){
        /*
        for (int i = 0; i<args.length ; i++){
                System.out.println("" + i + " " + args[i]);
        }*/

        if(args.length > 1) {

            numOfDocsFlag = Integer.parseInt(args[0]);

            onlyJudgedFlag = args[1].equals("yes");

            allTopicsFlag = args[2].equals("yes");

            if(args[3].contains("official")){
                metrics = new OfficialTRECMetrics().getMetricSet();
            } else if(args[3].contains("all")){
                metrics = new AllTRECMetrics().getMetricSet();
            } else {
                metrics = new MetricSet();
            }

            String[] metricsFlags = args[3].split(",");

            ArrayList<Metric> mList = new AllTRECMetrics().getMetricSet().getMetricList();

            for (String metric: metricsFlags) {
                if(!metric.equals("official") && !metric.equals("all")){

                    for (Metric m: mList) {
                        if(m.getMetricAcronym().equals(metric)){
                            metrics.add(m);
                        }
                    }
                }
            }

            helper = args[4].equals("yes");

            if (!args[5].contains("none")) {
                qrelPath = args[5];
            }

            if (!args[6].contains("none")) {
                runPath = args[6];
            }

            String[] viewFlags;
            viewFlags = args[7].split(":");

            switch (viewFlags[0]) {
                case "all":
                    resultViewer = new CompleteResultViewer();
                    break;
                case "overall":
                    resultViewer = new OverallResultViewer();
                    break;
                case "run_all":
                    resultViewer = new CompleteRunResultViewer(viewFlags[1]);
                    break;
                case "run_overall":
                    resultViewer = new OverallRunResultViewer(viewFlags[1]);
                    break;
                case "topic":
                    resultViewer = new TopicResultViewer(viewFlags[1]);
                    break;
                case "metric":
                    resultViewer = new MetricResultViewer(viewFlags[1]);
            }

            if (!args[8].contains("none")) {
                outFilePath = args[8];
            }

            String[] relevanceFlags;
            relevanceFlags = args[9].split(":");

            int relLimit = 1, numCat = 2;
            double relLimit2 = 1.0;


            switch (relevanceFlags[0]) {
                case "binary":
                    relevanceType = new BinaryRelevanceType("1", "0");
                    System.out.println("\nBinary relevance type.");
                    break;
                case "category":
                    String[] cateRelFlags;
                    cateRelFlags = relevanceFlags[1].split(",");
                    if(cateRelFlags.length==2){
                        numCat = Integer.parseInt(cateRelFlags[0]);
                        relLimit = Integer.parseInt(cateRelFlags[1]);
                    }
                    relevanceType = new NumericalCategoryRelevanceType(numCat, relLimit);
                    System.out.println("\nCategory relevance type. Relevance limit: "+ relLimit + ", number of categories: " + numCat);
                    break;
                case "numeric":
                    if(viewFlags.length==2){
                        relLimit2 = Double.parseDouble(viewFlags[1]);
                    }
                    relevanceType = new NumericRelevanceType(relLimit2);
                    System.out.println("\nNumeric relevance type. Relevance limit: "+ relLimit2);
                    break;
            }
        }
    }
}
