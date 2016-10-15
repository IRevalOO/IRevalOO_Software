# IRevalOO

IRevalOO is an object-oriented framework to support the evaluation of information retrieval systems inspired to trec_eval tool. 
The goal of IRevalOO is to make possible the implementation of custom applications for the evaluation of information retrieval systems.

With this framework the user can easily personalize the framework:

- adding new evaluation metrics;
- defining new metric sets;
- defining new relevance types;
- defining a new test collection importation formats (documents, topics and qrels); 
- defining a new run importation formats; 
- import runs and test collections independently from the input source (such as: databases, Json, XML,...);
- manage the metric results in a custom way, defining new procedure (such ad custom visualization, custom exportation, ...)


In IRevalOO there are also a series of default classes that can be used directly to instantiate the framework:

- *AdHocTRECCollection*: is able to import qrels from file in a ad hoc TREC-format;
- *AdHocTRECRunSet*:  is able to import all the runs from file in a ad hoc TREC-format in a given folder;
- Default metrics (inspired to trec_eval tool): 
    - *P*: precision.
    - *R*: recall.
    - *F*: F-measure.
    - *MAP*: Mean Average Precision (also at a given level of cutoff).
    - *GMAP*: Geometric Mean Average Precision.
    - *MRR*: Mean reciprocal rank.
    - *Bpref*: Bpref.
    - *GBpref*: Bpref with geometric mean.
    - *NDCG*: Normalized Discount Cumulative Gain (also at a given level of cutoff).
    - *RelativeP*: relative precision.
    - *PatN*:  precision at a given level of cutoff.
    - *RatN*: recall at a given level of cutoff.
    - *RelativePatN*: relative precision  at a given level of cutoff.
    - *Success*: see trec_eval documentation.
    - *InfAP*: Inferred AP metric see trec_eval documentation.
    - *Utility*: see trec_eval documentation.
    - *PRCurve*: Precision & Recall curve.
    
### Building

IRevalOO can be build (and run) using Apache Ant, the targets implemented in build.xml file are:

 - **framework_jar**:  compile the framework obtaining IRevalOO.jar. Just call: "ant framework_jar". Jar file is in ./out/jar folder. You can import the .jar file in your own project and use it to create your own IRevalOO personalized framework instance.
 
 - **run**: compile and run according to the Main class in *user* package. Just call: "ant run". NB: In this case: Main.java class must be into *user* package, and user instances also should be in user package.

 - **doc**: (if you need more documentation) generate javadoc in HTML for current project into ./doc folder. Just call: "ant doc".
 
### How to use IRevalOO

To use IRevalOO you need to do some things in your main method:

- instantiate the right *Collection* to use;
- instantiate the right *RunSet* to use; 
- instantiate the *Metric* or *MetricSet* to evaluate;
- instantiate the *EvaluatorManager* passing the collection, run set and metrics;
- set some secondary options;
- start the evaluation calling the *evaluatorManager.evaluate()* method;
- manage the results, it can be used the method *evaluatorManager.showResults(viewer)* passing the right *ResultViewer*, or *evaluatorManager.exportResults(exporter)* passing the right *ResultExporter* (some Viewer and Exporter are already available by default in IRevalOO).

### How to extend IRevalOO

IRevalOO can be used in two ways: 
- the first way is to use only the default classes already implemented in this framework (black-box approach);
- the second way is to extend and customize the framework implementing and using your own classes (white-box approach).

To follow the second approach is recommended to import the *IRevalOO.jar* in your project in order to extend the right abstract classes (or interfaces) and implement the right methods.
Some extended examples are reported in the *user.instances* package.

#### Create a new metric

To create a new metric follow this steps:

- extend the *Metric* class;
- implement the public constructor and instantiate all the attributes you need (such as: completeName, description, others...). Remember to instantiate also the acronym attribute giving the right metric acronym.
- implement the inherited abstract methods:
    - *computeTopicResult()*: calculate the metric result for a topic in the run;
    - *computeOverallResult()*: calculate the metric result over all topics in the run (such as average, sum,...).
    
#### Create a new metric result

Each metric has his result type, (most of them calculate a numeric result so they use the default result type *NumericResult*), to create a custom metric result type follow this steps:

- extend the *Result* class;
- implement the public constructor calling the *super(idTopic, metric)* method;
- implement the inherited abstract methods:
    - *toString()*: build a printable string of the result;
    - *toDouble()*: if possible return the result like a double, throw an exception otherwise;
    - implement order specific methods.
    
#### Create a metric set builder

To create a new metric set builder follow this steps:

- extend the *MetricSetBuilder* class;
- implement the public constructor calling the *super()* method;
- implement the inherited abstract methods:
    - *create()*: instantiate,fill and return the *MetricSet* whit the right metrics;
    
#### Create a new test collection format

To create a new test collection follow this steps:

- extend the *Collection* class;
- implement the public constructor calling the *super(relevanceType)* method (and instantiate all the other attributes you need for importation);
- implement the inherited abstract methods:
    - *importDocumentCollection()*: manage the importation and instantiate the right *Document* class. Use the method *addDocument(doc)* to add a document.
    - *importTopicCollection()*: manage the importation and instantiate the right *Topic* class. Use the method *addTopic(topic)* to add a topic.
    - *importQrelCollection()*: manage the importation and instantiate the right *Qrel* class. Use the method *addQrel(qrel)* to add a qrel.

It is possible also extend and customize *Document* and *Topic* classes adding new attributes:

- extend the *Topic* or *Document* class;
- implement the public constructor calling the *super()* method and implement the right get() methods.



#### Create a new run format

To create a new run follow this steps:

- extend the *RunSet* class;
- implement the public constructor calling the *super(relevanceType)* method (and instantiate  all the other attributes you need for importation);
- implement the inherited abstract methods:
    - *importRunSet()*: manage the importation and instantiate the right *Run* class. Use the method *add(run)* to add a run to the run set.
    
- extend the *RunSet* class;
- implement the public constructor calling the *super(relevanceType, name)* method (and instantiate all the other attributes you need for importation);
- implement the inherited abstract methods:
    - *importRun()*: manage the importation and instantiate the right *RunLine* class. Use the method *add(runLine)* to add a single outcome to the run.

It is possible also extend and customize *RunLine* class adding new attributes:

- extend the *RunLine* class;
- implement the public constructor calling the *super()* method and implement the right get() methods.

#### Create a new relevance type

To create a new relevance type follow this steps:

- extend the *RelevanceType* class;
- implement the public constructor;
- implement the inherited abstract methods:
    - *readValue()*: reads the input value and translates it in the appropriate numerical value used within the framework (example: "very relevant" = 2, "relevant" = 1, "not relevant" = 0, "unjudged" = -1 ); 
    - *isRelevant()*: define if a relevance value is relevant;
    - *isUnjudged()*: define if a relevance value is unjudged.


#### Create a new result viewer 

To create a new result viewer follow this steps:

- extend the *ResultViewer* class;
- implement the public constructor;
- implement the inherited abstract methods:
    - *show(results)*: print on terminal the results.

#### Create a new result exporter 

To create a new result exporter follow this steps:

- extend the *ResultExporter* class;
- implement the public constructor;
- implement the inherited abstract methods:
    - *export(results)*: export the results.
    
For more information refer to the javadoc.
