package uniud.trecevaloo.testcollection;

/**
 * This class represents a generic document. If you want to create a new document format extend this class.
 */
public abstract class Document {

    private String id;
    private String text;

    /**
     * Document constructor.
     * @param id document id.
     * @param text document text.
     */
    public Document(String id, String text){
        this.id = id;
        this.text = text;
    }

    /**
     * Get document id.
     * @return  document id.
     */
    public String getId(){
        return id;
    }

    /**
     * Get document text.
     * @return document text.
     */
    public String getText(){
        return text;
    }
}
