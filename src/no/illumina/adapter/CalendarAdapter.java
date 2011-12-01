package no.illumina.adapter;

/**
 * User: Erik Axel Nielsen
 * Date: 20.11.11
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;
import no.illumina.ConferenceDialer;
import no.illumina.SettingActivity;
import no.illumina.dom.Calendar;
import no.illumina.dom.ConferenceEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarAdapter implements ICalendarAdapter {
    private static String CALENDAR_CALENDARS_URI = "content://calendar/calendars";
    private static String CALENDAR_CALENDARS_URI_8_PLUS = "content://com.android.calendar/calendars";

    private final Context context;

    private static String getCalendarUri() {
        if (Integer.parseInt(Build.VERSION.SDK) <= 7) {
            return CALENDAR_CALENDARS_URI;
        } else {
            return CALENDAR_CALENDARS_URI_8_PLUS;
        }
    }

    protected CalendarAdapter(Context ctx) {
        this.context = ctx;
    }

    public List<Calendar> getCalendarList() {
        List<Calendar> returnList = new ArrayList<Calendar>();
        ContentResolver contentResolver = context.getContentResolver();

        final Cursor cursor = contentResolver.query(Uri.parse(getCalendarUri()),
                (new String[]{"_id", "name"}), null, null, null);
        // For a full list of available columns see http://tinyurl.com/yfbg76w

        while (cursor.moveToNext()) {
            Calendar cal = new Calendar(cursor.getString(0), cursor.getString(1));
            cal.setVisible(isCalendarVisible(cal));
            returnList.add(cal);
        }
        return returnList;
    }

    public List<ConferenceEvent> parseCalendar() {
        List<ConferenceEvent> returnList = new ArrayList<ConferenceEvent>();
        ConferenceDialer dialer = new ConferenceDialer();

        ContentResolver contentResolver = context.getContentResolver();

        // For each calendar, display all the events from the previous week to the end of next week.
        for (Calendar calendar : getCalendarList()) {
            if (!calendar.isVisible()) continue;

            Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
            long now = new Date().getTime();
            //ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);

            ContentUris.appendId(builder, now - DateUtils.HOUR_IN_MILLIS * 2);
            ContentUris.appendId(builder, now + DateUtils.DAY_IN_MILLIS);


            /*
            Cursor cur = contentResolver.query(builder.build(), null, "Calendars._id=" + id,
                    null, "startDay ASC, startMinute ASC");

            for(int i=0;i<cur.getColumnCount();i++) {
                System.out.println("NAME " + cur.getColumnName(i));
            }
            */


            Cursor eventCursor = contentResolver.query(builder.build(),
                    new String[]{"title", "begin", "end", "allDay", "eventLocation"}, "Calendars._id=" + calendar.getId(),
                    null, "startDay ASC, startMinute ASC");
            // For a full list of available columns see http://tinyurl.com/yfbg76w

            while (eventCursor.moveToNext()) {
                final String title = eventCursor.getString(0);
                final Date begin = new Date(eventCursor.getLong(1));
                final Date end = new Date(eventCursor.getLong(2));
                final Boolean allDay = !eventCursor.getString(3).equals("0");
                final String location = eventCursor.getString(4);

//				System.out.println("Title: " + title + " Begin: " + begin + " End: " + end +
//						" All Day: " + allDay);
//                System.out.println("Title " + title + " Location:"  + location);
//                System.out.println("Title " + title);

                String dialIn = dialer.findDialIn(location);
                if (dialIn == null) {
                    dialIn = dialer.findDialIn(title);
                }
                returnList.add(new ConferenceEvent(title, location, dialIn, begin, end));
            }
        }
        return returnList;
    }

    private boolean isCalendarVisible(Calendar calendar) {
        SharedPreferences settings = context.getSharedPreferences(SettingActivity.SETTING_CALENDAR, Context.MODE_PRIVATE);
        return settings.getBoolean(SettingActivity.SETTING_CALENDAR + calendar.getId(), true);
    }
}