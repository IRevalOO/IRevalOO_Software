package test;

import uniud.trecevaloo.control.EvaluatorManager;
import uniud.trecevaloo.exceptions.TrecEvalOORunException;
import uniud.trecevaloo.metrics.definitions.MetricSet;
import uniud.trecevaloo.metrics.definitions.OfficialTRECMetrics;
import uniud.trecevaloo.metrics.results.FileResultExporter;
import uniud.trecevaloo.metrics.results.ResultExporter;
import uniud.trecevaloo.metrics.results.ResultViewer;
import uniud.trecevaloo.relevance.NumericRelevanceType;
import uniud.trecevaloo.relevance.NumericalCategoryRelevanceType;
import uniud.trecevaloo.relevance.RelevanceType;
import uniud.trecevaloo.run.*;
import uniud.trecevaloo.testcollection.AdHocTRECCollection;
import uniud.trecevaloo.testcollection.Collection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by yannick on 12/10/16.
 */
public class Test {

    static final int NUM_TOPIC = 100000;

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String path = "";

    private static String qrelPath = "";
    private static String runPath = "";
    private static String outFilePath = "";
    private static int numOfDocsFlag = -1;
    private static boolean onlyJudgedFlag = false;
    private static boolean allTopicsFlag = false;

    private static MetricSet metrics = new OfficialTRECMetrics().getMetricSet();
    private static ResultViewer resultViewer = null;
    private static ResultExporter resultExporter = null;
    private static RelevanceType relevanceType = new NumericalCategoryRelevanceType(3,1);
    private static RelevanceType runRelevanceType = new NumericRelevanceType(1.0);
    private static double totalTime = 0;


    public static void main(String[] args) throws IOException {
        System.out.println("--------------- TESTING TRECEVALOO --------------------");

        // set paths from code (just for testing)
        setPathsFromCode();

        // create a random qrels and run file (just for testing)
        /*millionQueryTrackCreator();
        millionQueryTrackRunCreator();
        System.out.println("DONE");*/

        String test = "";
        int num = 10;
        for (int i = 0 ; i < num ; i++){
            System.out.println("ITER " + i);
            double time = compute();
            test += "Test " + i + " time: " + time + "\n";
            totalTime += time;
        }

        test += "Average " + totalTime/num;

        System.out.print(test);

        //createMillionTestFiles();
    }



    static double compute(){
        if( runPath.isEmpty()){
            System.out.println("Missing run path (or runs folder path)");
            return 0;
        } else if(qrelPath.isEmpty()){
            System.out.println("Missing qrels path");
            return 0;
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
        if(resultViewer!=null){
            evaluatorManager.showResults(resultViewer);
        }

        // Export results in a file
        if(!outFilePath.isEmpty()){
            resultExporter = new FileResultExporter(outFilePath);
            evaluatorManager.exportResults(resultExporter);
        }

        return evaluatorManager.time;

    }


    static void createMillionTestFiles(){
        int[] numtopics = {10, 100, 1000};

        for (int i = 0 ; i<numtopics.length; i++) {

            final int index = i;
            try {
                Files.walk(Paths.get(runPath)).forEach(filePath -> {

                    if (Files.isRegularFile(filePath)) {
                        System.out.println(filePath.getFileName().toString());

                        int currentNumTopics = 0;
                        String previusTopic = "1";
                        BufferedReader buffer = null;
                        String sCurrentLine;

                        String outPath = "./data/test_million_query/out/" + filePath.getFileName().toString()  + "_t" + numtopics[index];
                        PrintWriter writer = null;

                        try {
                            writer = new PrintWriter(outPath, "UTF-8");
                            buffer = new BufferedReader(new FileReader(runPath+"/"+filePath.getFileName()));

                            while ((sCurrentLine = buffer.readLine()) != null) {

                                sCurrentLine = sCurrentLine.trim();
                                if (sCurrentLine.isEmpty()) {
                                    continue;
                                }

                                String[] strings = sCurrentLine.split("\\s+");



                                    if(!previusTopic.equals(strings[0])){
                                        currentNumTopics++;
                                        if(currentNumTopics >= numtopics[index]){
                                            writer.close();
                                            return;
                                        }
                                        previusTopic = strings[0];

                                    } else {
                                        previusTopic = strings[0];
                                        //System.out.println(previusTopic);
                                    }

                                    // scrivi file
                                    writer.println(sCurrentLine);
                                }



                        } catch (IOException e) {
                            throw new TrecEvalOORunException(e.toString());
                        } finally {
                            try {
                                if (buffer != null) buffer.close();
                            } catch (IOException ex) {
                                throw new TrecEvalOORunException(ex.toString());
                            }
                        }
                    }
                });
            } catch (IOException e) {
                throw new TrecEvalOORunException(e.toString());
            }
        }

    }



    //-----------------------------------
    // set paths from code
    //-----------------------------------
    static void setPathsFromCode() {
        // INSERT THE RIGHT PATHS:
        if (OS.contains("win")) {
            // This is Windows

            path = "data\\test_random\\";
            outFilePath = "results_out\\example.out";

            //qrelPath = "data\\test_treceval\\qrels.test";
            //runPath = "data\\test_treceval\\results.test";

            // random tests
            //qrelPath =  "data\\test_random\\qrels.test";
            //runPath  = "data\\test_random\\run.test";

            //qrelPath =  "data\\test_TREC8\\qrelsTrec8";
            //runPath  = "data\\test_TREC8\\run";

            qrelPath = "data\\test_million_query\\qrelsMillion";
            runPath = "data\\test_million_query\\run";

        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            // This is Unix

            path = "data/test_random/";
            outFilePath = "results_out/example.out";

            // trec_eval tests
            //qrelPath = "./data/test_treceval/qrels.test";                   // bynary relevance qrels (3600)
            //qrelPath = "./data/test_treceval/qrels.123";                  // category qrels 0-4  -1 if unjudged (3600)
            //runPath = "./data/test_treceval/results.test";

            // trec8 tests
            //qrelPath = "./data/test_TREC8/qrelsTrec8";                  // bynary relevance qrels (87000)
            //runPath = "./data/test_TREC8/run";
            //runPath = "./data/test_TREC8/run/input.acsys8aln2";

            // test million query track
            qrelPath = "./data/test_million_query/qrelsMillion_2";                  // bynary relevance qrels (87000)
            runPath = "./data/test_million_query/run10000";


            // random tests
            //qrelPath = "./data/test_random/qrels.test";
            //runPath = "./data/test_random/run.test";

        } else if (OS.contains("mac")) {
            // This is Mac
        } else {
            // others...
        }
    }

    // ----------------------------------
    // Collection builder for testing
    // ----------------------------------
    static void millionQueryTrackCreator() {
        String filePath = path + "qrels.test";

        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath, "UTF-8");

            for (int i = 0; i < NUM_TOPIC; i++) {
                for (int j = 0; j < 10; j++) {
                    Random random = new Random();
                    String rel = (random.nextBoolean() ? "1" : "0");
                    writer.println(i + "\t" + "0" + "\t" + "DOC_" + j + "\t" + rel);
                }
            }

            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    static void millionQueryTrackRunCreator() {
        String filePath = path + "run.test";


        int[] docsID1 = {1, 3, 0, 4, 8};
        int[] docsID2 = {5, 3, 0, 2, 9};
        int[] docsID3 = {1, 0, 3, 6, 8};
        int[] docsID4 = {7, 2, 1, 4, 3};
        int[] docsID5 = {9, 5, 1, 4, 2};


        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath, "UTF-8");

            for (int i = 0; i < NUM_TOPIC; i++) {

                Random random = new Random();
                double x = random.nextDouble() * 10;

                if (x < 2) {
                    createRunLine(writer,i,docsID1);
                } else if (x < 4) {
                    createRunLine(writer,i,docsID2);
                } else if (x < 6) {
                    createRunLine(writer,i,docsID3);
                } else if (x < 8) {
                    createRunLine(writer,i,docsID4);
                } else {
                    createRunLine(writer,i,docsID5);
                }

            }

            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void createRunLine(PrintWriter w, int i, int[] idDocs) {
        for (int j = 0; j < idDocs.length; j++) {

            Random random = new Random();
            double rel = random.nextDouble();

            w.println(i + "\t" + "Q0" + "\t" + "DOC_" + idDocs[j] + "\t" + "000" + "\t" + rel + "\t" + "RUNTEST");
        }
    }
}
