package user.instances;

import uniud.trecevaloo.testcollection.Topic;

/**
 * This class is an example of how extend the framework with your own topic format.
 */
public class MyTopic extends Topic {

    private String narrative;
    private String category;


    public MyTopic(String id, String description, String narrative, String category) {
        super(id, description);
        this.narrative = narrative;
        this.category = category;
     }

    public String getNarrative() {
        return narrative;
    }

    public String getCategory() {
        return category;
    }
}
