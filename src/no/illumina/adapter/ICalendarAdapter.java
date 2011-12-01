package no.illumina.adapter;

import no.illumina.dom.Calendar;
import no.illumina.dom.ConferenceEvent;

import java.util.List;

/**
 * User: Erik Axel Nielsen
 * Date: 01.12.11
 */
public interface ICalendarAdapter {
    List<Calendar> getCalendarList();
    public List<ConferenceEvent> parseCalendar();
}
