package uhmanoa.droid_sch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by supah_000 on 4/15/2015.
 */
public class BuilderOptions{

    private Context c;
    private final String selectOption = "SELECTED_OPTION";

    private final String booleanDaysOff = "PREF_DAYS_OFF";
    private final String stringDaysOff = "PREF_DAYS_OFF_S";

    private final String booleanStart = "PREF_START";
    private final String booleanEnd = "PREF_END";
    private final String intStart = "PREF_START_INT";
    private final String intEnd = "PREF_END_INT";

    private final String booleanTimeOff = "PREF_TIME_OFF";
    private final String stringDayTimeOff1 = "PREF_TIMOFF_DAY1";
    private final String stringDayTimeOff2 = "PREF_TIMOFF_DAY2";
    private final String intTOStart1 = "PREF_START_INT_TO1";
    private final String intTOStart2 = "PREF_START_INT_TO2";
    private final String intTOEnd1 = "PREF_END_INT_END1";
    private final String intTOEnd2 = "PREF_END_INT_END2";


    public BuilderOptions (Context ctx) {
        c = ctx;
    }

    public int getSelectedOption() {
        int x = 0; //default = 0;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        x = settings.getInt(selectOption, 0);
        return x;
    }

    public void setTimeOffStart1(int x) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intTOStart1, x);
        editor.commit();
    }

    public void setTimeOffStart2(int x) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intTOStart2,x);
        editor.commit();
    }

    public void setTimeOffEnd1(int x) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intTOEnd1, x);
        editor.commit();
    }

    public void setTimeOffEnd2(int x) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intTOEnd2, x);
        editor.commit();
    }

    public int getTimeOffStart1() {
        int x = 0; //default = 0;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        x = settings.getInt(intTOStart1, -1);
        return x;
    }

    public int getTimeOffStart2() {
        int x = 0; //default = 0;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        x = settings.getInt(intTOStart2, -1);
        return x;
    }

    public int getTimeOffEnd1() {
        int x = 0; //default = 0;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        x = settings.getInt(intTOEnd1, -1);
        return x;
    }

    public int getTimeOffEnd2() {
        int x = 0; //default = 0;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        x = settings.getInt(intTOEnd2, -1);
        return x;
    }

    public void setSelectedOption(int x) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(selectOption, x);
        editor.commit();
    }

    public void setTimeOffBoolean(boolean b) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(booleanTimeOff, b);
        editor.commit();
    }
    public boolean getTimeOffBoolean() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getBoolean(booleanTimeOff, false);
    }

    public void setEarliestStartBoolean(boolean b) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(booleanStart, b);
        editor.commit();
    }

    public void setLatestEndBoolean(boolean b) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(booleanEnd, b);
        editor.commit();
    }

    public void setStartTime(int time) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intStart, time);
        editor.commit();
    }

    public void setEndTime(int time) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(intEnd, time);
        editor.commit();
    }

    public void setDaysOffBoolean(Boolean b) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(booleanDaysOff, b);
        editor.commit();
    }

    public void setDaysOffString(String s) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(stringDaysOff, s);
        editor.commit();
    }

    public void setDayTimeOffString1(String s) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(stringDayTimeOff1, s);
        editor.commit();
    }

    public void setDayTimeOffString2(String s) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(stringDayTimeOff2, s);
        editor.commit();
    }

    public boolean getDaysOffBoolean() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getBoolean(booleanDaysOff, false);
    }

    public ArrayList<Character> getDaysOffArray() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        String days = settings.getString(stringDaysOff ,"");
        ArrayList<Character> day_char = new ArrayList<>();
        for(int x = 0; x< days.length(); x++) {
            day_char.add(days.charAt(x));
        }
        return day_char;
    }

    public ArrayList<Character> getDaysTOArray1() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        String days = settings.getString(stringDayTimeOff1 ,"");
        ArrayList<Character> day_char = new ArrayList<>();
        for(int x = 0; x< days.length(); x++) {
            day_char.add(days.charAt(x));
        }
        return day_char;
    }


    public ArrayList<Character> getDaysTOArray2() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        String days = settings.getString(stringDayTimeOff2 ,"");
        ArrayList<Character> day_char = new ArrayList<>();
        for(int x = 0; x< days.length(); x++) {
            day_char.add(days.charAt(x));
        }
        return day_char;
    }


    public boolean getBooleanLatestEnd() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getBoolean(booleanEnd, false);
    }

    public boolean getBooleanEarliestStart() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getBoolean(booleanStart, false);
    }

    public int getLatestEnd() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getInt(intEnd, -1);
    }
    public int getEarliestStart() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getInt(intStart, -1);
    }

}