package no.illumina.adapter;

import android.content.Context;

/**
 * User: Erik Axel Nielsen
 * Date: 01.12.11
 */
public class CalendarAdapterFactory {
    public static ICalendarAdapter getCalendarAdapter(Context context) {
        //return new DummyCalendarAdapter();
        return new  CalendarAdapter(context);
    }
}
