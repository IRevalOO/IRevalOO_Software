package uniud.trecevaloo.testcollection;

/**
 * This class represents a generic topic. If you want to create a new topic format extend this class.
 */
public abstract class Topic {

    private String id;
    private String description;

    /**
     * Topic constructor.
     * @param id topic id.
     * @param description topic description.
     */
    public Topic(String id, String description){
        this.id = id;
        this.description = description;
    }

    /**
     * Get topic id.
     * @return topic id.
     */
    public String getId(){
        return id;
    }

    /**
     * Get topic description.
     * @return description.
     */
    public String getDescription(){
        return description;
    }
}

