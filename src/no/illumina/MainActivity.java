package no.illumina;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import no.illumina.dom.ConferenceEvent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    // Cannot use pound directly in URLs, use this instead
    public static final String POUND = "%23";

    private static final int ACTIVITY_SETTINGS = 0;
    private CountryPhoneNumberMap phoneNumberMap;

    private final List<ConferenceEvent> eventList = new ArrayList<ConferenceEvent>();
    private ConferenceEventAdapter adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ConferenceEventAdapter(this, R.layout.dial_in_list_item, eventList);
        setListAdapter(adapter);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialConference(eventList.get(position).getDialIn());
            }
        });


        fillData();
        phoneNumberMap = new CountryPhoneNumberMap(this);
    }

    private void fillData() {
        CalendarAdapter calendarAdapter = new CalendarAdapter(this);
        eventList.clear();
        eventList.addAll(calendarAdapter.parseCalendar());
        adapter.notifyDataSetChanged();
    }

    public void dialConference(String dialIn) {
        try {
            TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            System.out.println("COUNTRY " + mTM.getNetworkCountryIso());

            Intent callIntent = new Intent(Intent.ACTION_CALL);

            String phoneNumber = phoneNumberMap.getPhoneNumber(mTM.getNetworkCountryIso());

            callIntent.setData(Uri.parse("tel:" + phoneNumber + "," + dialIn + POUND));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("ConfConnect", "Call failed", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(this, SettingActivity.class);
                startActivityForResult(i, ACTIVITY_SETTINGS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Bundle extras = intent.getExtras();
        switch (requestCode) {
            case ACTIVITY_SETTINGS:
                fillData();
                break;
        }
    }


}
