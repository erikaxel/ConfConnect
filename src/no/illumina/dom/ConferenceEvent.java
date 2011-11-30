package no.illumina.dom;

import java.util.Date;

/**
 * User: Erik Axel Nielsen
 * Date: 20.11.11
 */
public class ConferenceEvent {
    private String title;
    private String location;
    private String dialIn;
    private Date start;
    private Date end;


    public ConferenceEvent(String title, String location, String dialIn, Date start, Date end) {
        this.title = title;
        this.location = location;
        this.dialIn = dialIn;
        this.start = start;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return title;
/*
        final StringBuilder sb = new StringBuilder();
        sb.append("ConferenceEvent");
        sb.append("{title='").append(title).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", dialIn='").append(dialIn).append('\'');
        sb.append('}');
        return sb.toString();
        */
    }
}
