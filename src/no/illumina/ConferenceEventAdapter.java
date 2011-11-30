package no.illumina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import no.illumina.dom.ConferenceEvent;

import java.text.DateFormat;
import java.util.List;

/**
 * User: Erik Axel Nielsen
 * Date: 20.11.11
 */
public class ConferenceEventAdapter extends ArrayAdapter<ConferenceEvent> {
    private LayoutInflater mInflater;

    private List<ConferenceEvent> conferenceEventList;
    private int mViewResourceId;

    public ConferenceEventAdapter(Context ctx, int viewResourceId, List<ConferenceEvent> eventList) {
        super(ctx, viewResourceId, eventList);

        mInflater = (LayoutInflater) ctx.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        conferenceEventList = eventList;

        mViewResourceId = viewResourceId;
    }

    @Override
    public int getCount() {
        return conferenceEventList.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public ConferenceEvent getItem(int position) {
        return conferenceEventList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT);

        /*
        TextView t = new TextView(parent.getContext());
        t.setEnabled(false);
        //t.setBackgroundColor(Color.WHITE);
        //t.setTextColor(Color.BLACK);
        */


        convertView = mInflater.inflate(mViewResourceId, null);
        ///      convertView.setEnabled(false);

        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        TextView dateText = (TextView) convertView.findViewById(R.id.date_text);

        ConferenceEvent ce = conferenceEventList.get(position);
        if (ce != null) {
            if (ce.getTitle() != null) {
                titleText.setText(ce.getTitle().trim());
            }
            convertView.setEnabled(ce.getDialIn() != null);
            titleText.setEnabled(ce.getDialIn() != null);

            String dateString = format.format(ce.getStart()) + "-" + format.format(ce.getEnd());
            dateText.setText(dateString);
            dateText.setEnabled(ce.getDialIn() != null);
        }

        return convertView;
    }

}
