package no.illumina;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import no.illumina.dom.Calendar;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class SettingActivity extends Activity {
    public static final String SETTING_CALENDAR = "SETTING_CALENDAR";
    private List<CheckBox> checkBoxList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setTitle("Select calendar(s)");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkbox_container);

        // TODO: This can also be sent by the Main Activity
        CalendarAdapter adt = new CalendarAdapter(this);
        List<Calendar> calendarList = adt.getCalendarList();
        if (calendarList.size() > 0) {
            TextView labelNoCalendars = (TextView) findViewById(R.id.label_no_calendars);
            labelNoCalendars.setVisibility(View.GONE);
        }

        checkBoxList = new ArrayList<CheckBox>(calendarList.size());
        for (Calendar calendar : adt.getCalendarList()) {
            CheckBox box = new CheckBox(this);
            checkBoxList.add(box);
            String calendarName = calendar.getName();
            calendarName = calendarName == null || calendarName.trim().equals("") ? "(no name)" : calendarName;

            box.setText(calendarName);
            box.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            box.setChecked(calendar.isVisible());
            box.setTag(calendar);
            linearLayout.addView(box);
        }


        Button saveButton = (Button) findViewById(R.id.ok_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                save();
                finish();
//                Intent mIntent = new Intent();
                //mIntent.putExtras(bundle);
//                setResult(RESULT_OK, mIntent);
            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
//                Intent mIntent = new Intent();
                //mIntent.putExtras(bundle);
//                setResult(RESULT_OK, mIntent);
            }
        });
    }

    private void save() {
        SharedPreferences settings = getSharedPreferences(SETTING_CALENDAR, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        for (CheckBox cb : checkBoxList) {
            Calendar calendar = (Calendar) cb.getTag();
            calendar.setVisible(cb.isChecked());
            editor.putBoolean(SETTING_CALENDAR + calendar.getId(), calendar.isVisible());
        }
        editor.commit();
    }
}

