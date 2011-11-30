package no.illumina.dom;

/**
 */
public class Calendar {
    private String id;
    private String name;
    private boolean visible;

    public Calendar(String id, String name) {
        this.id = id;
        this.name = name;
        this.visible = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
