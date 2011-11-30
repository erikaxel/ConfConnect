package no.illumina;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class CountryPhoneNumberMap {
    private Map<String, String> phoneMap;

    public CountryPhoneNumberMap(Context context) {
        phoneMap = new HashMap<String, String>();
        for (String str : context.getResources().getStringArray(R.array.phone_numbers)) {
            String[] split = str.split("=");
            // iso code in 0, actual phone number in 1
            String isoCode = split[0].toLowerCase();
            String phoneNumber = split[1].replaceAll(" ", "");
            phoneMap.put(isoCode, phoneNumber);
        }
    }

    public String getPhoneNumber(String iso) {
        return phoneMap.get(iso.toLowerCase());
    }
}
