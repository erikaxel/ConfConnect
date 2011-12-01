package no.illumina.adapter;

import no.illumina.dom.Calendar;
import no.illumina.dom.ConferenceEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Erik Axel Nielsen
 * Date: 01.12.11
 */
public class DummyCalendarAdapter implements ICalendarAdapter{
    public List<Calendar> getCalendarList() {
        List<Calendar>returnList = new ArrayList<Calendar>();
        returnList.add(new Calendar("1", "Calendar 1"));
        returnList.add(new Calendar("2", "Calendar 2"));
        return returnList;
    }

    public List<ConferenceEvent> parseCalendar() {
        List<ConferenceEvent>confEvent = new ArrayList<ConferenceEvent>();
        confEvent.add(new ConferenceEvent("Entry 1", "12345678#", "1", new Date(), new Date()));
        confEvent.add(new ConferenceEvent("Entry 2", "A", "", new Date(), new Date()));
        confEvent.add(new ConferenceEvent("Entry 3", "A", "1244", new Date(), new Date()));
        return confEvent;
    }
}
