package com.example;

import android.app.Activity;
import android.app.ListActivity;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.List;

public class MyActivity extends ListActivity {
    public static final String DIAL_NORWAY = "+4722862508";
    // Cannot use pound directly in URLs, use this instead
    public static final String POUND = "%23";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<ConferenceEvent> eventList = CalendarExample.parseCalendar(this);
        setListAdapter(new ConferenceEventAdapter(this, R.layout.dial_in_list_item, eventList));

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                dialConference(eventList.get(position).getDialIn());
            }
        });

    }

    public void dialConference(String dialIn) {
        try {
            TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            System.out.println("COUNTRY " + mTM.getNetworkCountryIso());

            Intent callIntent = new Intent(Intent.ACTION_CALL);

            callIntent.setData(Uri.parse("tel:" + DIAL_NORWAY + "," + dialIn + POUND));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("helloandroid dialing example", "Call failed", e);
        }
    }
}
