package uniud.trecevaloo.testcollection;

/**
 * This class is an  instance example of the framework for a document.
 * It implements a document in ad Hoc TREC format.
 */
public class AdHocTrecDocument extends Document{

    private String title;
    private String author;
    private String source;
    private String data;

    /**
     * The constructor of an AdHoc TREC document.
     * @param id document id.
     * @param text document text.
     * @param title document title.
     * @param author document author.
     * @param source document source.
     * @param data document data.
     */
    AdHocTrecDocument(String id, String text, String title, String author, String source, String data) {
        super(id, text);
        this.title = title;
        this.author = author;
        this.data = data;
        this.source = source;
    }

    /**
     * Get document title.
     * @return title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get document author.
     * @return author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get document data.
     * @return data.
     */
    public String getData() {
        return data;
    }

    /**
     * Get document source.
     * @return source.
     */
    public String getSource() {
        return source;
    }
}
