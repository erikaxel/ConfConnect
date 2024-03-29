package no.illumina;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Erik Axel Nielsen
 * Date: 20.11.11
 */
public class ConferenceDialer {
    private char endChar = '#';
    private char startChar = 'x';
    private int numberOfChars = 8;

    public ConferenceDialer() {

    }

    // Find numbers that are
    // 1) Between
    public String findDialIn(String number) {
        if (number == null) return null;
        Pattern p = Pattern.compile(startChar + "[0-9]{" + numberOfChars + "}" + endChar);
        Matcher matcher = p.matcher(number);
        if (matcher.find()) {
            String g = matcher.group();
            return g.substring(1, g.length() - 1);
        }

        p = Pattern.compile("[0-9]{" + numberOfChars + "}" + endChar);
        matcher = p.matcher(number);
        if (matcher.find()) {
            String g = matcher.group();
            return g.substring(0, g.length() - 1);
        }
        // Try to match any group of exact digits
        p = Pattern.compile("[0-9]{" + numberOfChars + "}");
        matcher = p.matcher(number);
        if (matcher.find()) {
            String g = matcher.group();
            return g.substring(0, g.length() - 1);
        }
        // Try to find any
        // Things we maybe should catch:
        // 123 123 123#


        return null;
    }

    public static void main(String args[]) {
        ConferenceDialer dial = new ConferenceDialer();
        assertEquals(dial.findDialIn("+4402071497878x71052695#"), "71052695");
        assertEquals(dial.findDialIn("50091575#"), "50091575");
        assertIsNull(dial.findDialIn("50091575"));
        assertEquals(dial.findDialIn("Sourceledermøte - rom 405 - call-in: 400 0808 - 26127062# (Sushi blir servert)"), "26127062");
    }

    public static void assertEquals(String one, String two) {
        if (!one.equals(two)) {
            System.out.println(one + " does not equal " + two);
        }

        //assert(one.equals(two));
    }

    public static void assertIsNull(String one) {
        if (one != null) {
            System.out.println(one + " is not null");
        }
    }

}
