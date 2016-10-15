package uniud.trecevaloo.testcollection;

/**
 * This class is an  instance example of the framework for a document.
 * It implements a topic in ad Hoc TREC format.
 */
public class AdHocTRECTopic extends Topic{

    private String title;
    private String narrative;

    /**
     * The constructor of an AdHoc TREC topic.
     * @param id topic id.
     * @param description topic description.
     * @param title topic title.
     * @param narrative topic narrative.
     */
    AdHocTRECTopic(String id, String description, String title, String narrative) {
        super(id, description);
        this.narrative = narrative;
        this.title = title;
    }

    /**
     * Get topic title.
     * @return title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get topic narrative.
     * @return narrative.
     */
    public String getNarrative() {
        return narrative;
    }
}
