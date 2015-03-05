package uhmanoa.droid_sch;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Visualize extends Activity {

    final int white = -1;
    final int none = -1;
    final int max_height = 300;

    private TableLayout table_layout;

    private Schedule sch;
    private ArrayList<String> time_values;
    private ArrayList<String> day_values;
    private ArrayList<Vis_CellRow> height_values;
    private ArrayList<Integer> color_values;

    final int hours_day = 24;
    final static int first_hour = 0;
    final int days_week = 7;

    int pxWidth;

    private void initializeTimeValues() {
        time_values = new ArrayList<String>();
        int counter = 1;
        boolean start_PM = false;
        for (int x = 0; x < hours_day; x++) {
            if (x == first_hour) {
                time_values.add("12:00a");
            } else {
                if (counter == 12 && start_PM == false) {
                    //Change to pm
                    start_PM = true;
                }
                if (start_PM) {
                    time_values.add(String.valueOf(counter) + ":00p");
                } else {
                    time_values.add(String.valueOf(counter) + ":00a");
                }
                counter++;
                if (counter > 12) {
                    counter = 1;
                }
            }
        }
    }

    private int getStartHour(int input) {
        return input / 100;
    }

    //debug
    public ArrayList<String> DEBUG_getTimeValues() {
        return time_values;
    }

    public ArrayList<Vis_CellRow> DEBUG_getHeights() {
        return height_values;
    }

    public ArrayList<Course> DEBUG_getDayMatches(int day, Schedule s) {
        return getDayMatches(day, s);
    }

    private void initializeColorValues() {
        color_values = new ArrayList<Integer>();
        color_values.add(getResources().getColor(R.color.light_red));
        color_values.add(getResources().getColor(R.color.light_blue));
        color_values.add(getResources().getColor(R.color.light_green));
        color_values.add(getResources().getColor(R.color.light_orange));
        color_values.add(getResources().getColor(R.color.light_magenta));
        color_values.add(getResources().getColor(R.color.navy));
        color_values.add(getResources().getColor(R.color.mauve));

    }

    private void initializeDayValues() {
        day_values = new ArrayList<String>();
        day_values.add("U");
        day_values.add("M");
        day_values.add("T");
        day_values.add("W");
        day_values.add("R");
        day_values.add("F");
        day_values.add("S");
    }

    public void setSchedule(Schedule s) {
        sch = s;
    }

    private void configureDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        pxWidth = metrics.widthPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);
        configureDisplay();
        initializeTimeValues();
        initializeDayValues();
        height_values = new ArrayList<Vis_CellRow>();
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        Schedule test = new Schedule();
        BuildTable(test);
    }

    private void BuildTable(Schedule sch) {
        //Time is in military format e.g. 2400 for 12am
        int start_time = getStartHour(sch.earliestStart());
        int end_time = getEndHour(sch.latestEnd());

        // Row Loop
        for (int row = 0; row < hours_day + 1; row++) {

            TableRow table_row = new TableRow(this);
            table_row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if (!(row >= start_time && row < end_time || row == hours_day)) {
                table_row.setVisibility(TableRow.GONE);
            }

            Vis_CellRow vis_row = height_values.get(row);
            // Column Loop
            for (int col = 0; col <= days_week; col++) {

                //This is the first column so this is time Data
                if (col == 0) {
                    //If this is the last row then it should not have a time
                    if (row >= (hours_day)) {
                        View vw = timeTextView("   ");
                        table_row.addView(vw);
                    } else {
                        // else set a time
                        View vw = timeTextView(time_values.get(row));
                        table_row.addView(vw);
                    }
                } else {
                    // If this is the last row
                    if (row == (hours_day)) {
                        //Set a letter to represent the day
                        View vw = timeTextView(day_values.get(col - 1));
                        table_row.addView(vw);
                    } else {
                        //Configure what it should look like
                        View vis_box = getLayoutInflater().inflate(R.layout.vis_layout, null);

                        View start = vis_box.findViewById(R.id.vis_top);
                        View middle = vis_box.findViewById(R.id.vis_mid);
                        View end = vis_box.findViewById(R.id.vis_bot);

                        Vis_Cell vc = vis_row.getVisCell(0);

                        start.setLayoutParams(new TableRow.LayoutParams());
                        start.getLayoutParams().height = vc.getTop();
                        start.getLayoutParams().width = getColumnWidth();
                        start.setBackgroundColor(getResources().getColor(color_values.get(
                                (int)vc.getTopColor())));

                        middle.setLayoutParams(new TableRow.LayoutParams());
                        middle.getLayoutParams().height = vc.getMid();
                        middle.getLayoutParams().width = getColumnWidth();
                        middle.setBackgroundColor(getResources().getColor(color_values.get(
                                (int)vc.getMidColor())));

                        end.setLayoutParams(new TableRow.LayoutParams());
                        end.getLayoutParams().height = vc.getBot();
                        end.getLayoutParams().width = getColumnWidth();
                        end.setBackgroundColor(getResources().getColor(color_values.get(
                                (int)vc.getBotColor())));;

                        table_row.addView(vis_box);
                    }
                }
            }
            table_layout.addView(table_row);
        }
    }

    private void populateHeights() {
        //assign unique ids to courses
        assignUniqueID(sch.getCourses());

        int start = 0;
        int end = 59;
        for (int x = 0; x < 23; x++) {
            //row logic

            Vis_CellRow vcr = new Vis_CellRow();
            for (int y = 0; y < days_week; y++) {
                //column logic
                ArrayList<Course> matches = getDayMatches(y, sch);
                if (matches.size() == 0) {
                    //is empty so add empty vis_cell
                    vcr.addVisCell(getVisCell(null, null, false, false, 5));
                } else {
                    int vis_case = 5;
                    ArrayList<Boolean> course_selector = new ArrayList<Boolean>();
                    ArrayList<Course> actual_matches = getCoursesWithin(start, end, vis_case,
                            course_selector, matches);
                    matches = null; //dereference

                    if (actual_matches.size() == 0) {
                        //is empty so add empty vis_cell
                        vcr.addVisCell(getVisCell(null, null, false, false, 5));
                        continue;
                    }

                    switch (vis_case) {
                        case 1:
                            vcr.addVisCell(getVisCell(actual_matches.get(0), null,
                                    course_selector.get(0), false, 1));
                            continue;
                        case 2:
                            vcr.addVisCell(getVisCell(null, actual_matches.get(0),
                                    false, course_selector.get(0), 2));
                            continue;
                        case 3:
                            vcr.addVisCell(getVisCell(actual_matches.get(0), actual_matches.get(1),
                                    course_selector.get(0), course_selector.get(1), 3));
                            continue;
                        case 4:
                            vcr.addVisCell(getVisCell(actual_matches.get(0), null,
                                    course_selector.get(0), false, 4));
                            continue;
                    }

                }
            }
            start = end + 1;
            end = end + 60;
        }
    }

    private ArrayList<Course> getCoursesWithin(int start, int end, int vcase,
                                               ArrayList<Boolean> sel, ArrayList<Course> matches) {

        ArrayList<Course> actual_matches = new ArrayList<Course>();
        //boolean values of sel, FALSE = first course e.g. getStart1
        // TRUE = second course e.g. getStart2
        // Sel is for keeping track of which start/end time we are looking at

        //CASE 4 CHECKER
        for (Course c : matches) {
            if ((c.getStart1() <= start) && (c.getEnd1() >= end)) {
                //this course goes through this block of time
                actual_matches.add(c);
                sel.add(false);
                vcase = 4;
                return actual_matches;
            }

            if ((c.getStart2() <= start) && (c.getEnd2() >= end)) {
                actual_matches.add(c);
                vcase = 4;
                sel.add(true);
                return actual_matches;
            }
        }

        //CASE 1,2,3
        ArrayList<Course> special_matches = new ArrayList<Course>();
        ArrayList<Boolean> start_end = new ArrayList<Boolean>();
        ArrayList<Boolean> sec_sel = new ArrayList<Boolean>();
        // start_end, TRUE if time in question START, FALSE if the time in question is END
        // sec_sel is same as sel ArrayList, except since we may rearrange it, use temp for now

        for (Course c : matches) {
            //  CASE 1 CHECKS
            int top = courseTimeToMinutes(c.getEnd1());
            if (top >= start && top <= end) {
                special_matches.add(c);
                start_end.add(false); //Looking at END TIME
                sec_sel.add(false); //Looking at START1/END1
            }

            top = courseTimeToMinutes(c.getEnd2());
            if (top >= start && top <= end) {
                special_matches.add(c);
                start_end.add(false);//Looking at END TIME
                sec_sel.add(true);//Looking at START2/END2
            }
            // ----------------

            //  CASE 2 CHECKS
            int bot = courseTimeToMinutes(c.getStart1());
            if (bot >= start && bot <= end) {
                special_matches.add(c);
                start_end.add(true); //Looking at START TIME
                sec_sel.add(false); //Looking at START1/END1
            }

            bot = courseTimeToMinutes(c.getStart2());
            if (bot >= start && bot <= end) {
                special_matches.add(c);
                start_end.add(true); //Looking at START TIME
                sec_sel.add(true);//Looking at START2/END2
            }
            // ----------------

            // CASE 3 -> Multiple Courses  inside ACTUAL_MATCHES

        }

        switch (special_matches.size()) {
            case 1:
                //if theres size 1, theres only one course; either case 1 or 2
                sel = sec_sel;
                if (start_end.get(0)) {
                    //looking at starting time
                    vcase = 2; //CASE 2
                } else {
                    //looking at ending time
                    vcase = 1; //CASE 1
                }
                return special_matches;

            case 2:
                //first element need of actual matches and sel arraylist need to be TOP
                //second element needs to be BOT
                //LARGER TIME we are looking at is BOT

                vcase = 3;

                int first_course_time;
                int second_course_time;

                if (start_end.get(0)) {
                    if (sec_sel.get(0)) {
                        first_course_time = courseTimeToMinutes(special_matches.get(0).getStart2());
                    } else {
                        first_course_time = courseTimeToMinutes(special_matches.get(0).getStart1());
                    }
                } else {
                    if (sec_sel.get(0)) {
                        first_course_time = courseTimeToMinutes(special_matches.get(0).getEnd2());
                    } else {
                        first_course_time = courseTimeToMinutes(special_matches.get(0).getEnd1());
                    }
                }

                if (start_end.get(1)) {
                    if (sec_sel.get(1)) {
                        second_course_time = courseTimeToMinutes(
                                special_matches.get(1).getStart2());
                    } else {
                        second_course_time = courseTimeToMinutes(
                                special_matches.get(1).getStart1());
                    }
                } else {
                    if (sec_sel.get(1)) {
                        second_course_time = courseTimeToMinutes(
                                special_matches.get(1).getEnd2());
                    } else {
                        second_course_time = courseTimeToMinutes(
                                special_matches.get(1).getEnd1());
                    }
                }

                if (first_course_time > second_course_time) {
                    //this means first course is BOT so it needs to be SECOND element
                    ArrayList<Course> corrected_list = new ArrayList<Course>();
                    sel.clear();
                    corrected_list.add(special_matches.get(1));
                    corrected_list.add(special_matches.get(0));
                    sel.add(sec_sel.get(1));
                    sel.add(sec_sel.get(0));
                    return corrected_list;
                } else {
                    //original order is correct
                    sel = sec_sel;
                    return special_matches;
                }
        }

        //CASE 5
        return actual_matches;
    }

    public void assignUniqueID(ArrayList<Course> crs) {
        for (Course c : crs) {
            c.setID(uniqueCourseID(crs));
        }
    }


    private long uniqueCourseID(ArrayList<Course> crs) {
        long id = 0;
        boolean unique = false; // Initialize Unique to False
        while (!unique) {
            boolean match = false; // Reset Match Flag to False
            for (int x = 0; x < crs.size(); x++) {
                // Iterate al_strobj and check if there's an existing match to the ID
                Long cmp;
                cmp = crs.get(x).getID();

                // If Match Exist, set match to true
                if (cmp.equals(id)) {
                    //Match found
                    match = true;
                    break; // Break out of For Loop
                }
            }
            if (match) {
                id++; //Increment ID
            } else {
                unique = true;
            }
        }
        return id;
    }

    private ArrayList<Course> getDayMatches(int day, Schedule s) {
        ArrayList<Course> matches = new ArrayList<Course>();
        char firstLetter = day_values.get(day).charAt(0);
        for (Course c : s.getCourses()) {
            ArrayList<Character> days = c.getDays1();
            if (days.contains(firstLetter)) {
                matches.add(c);
                break; //already added to list so remove
            }
            if (c.getStart2() != 9999) {
                if (c.getDays2().contains(firstLetter)) {
                    matches.add(c);
                    break;
                }
            }
        }
        return matches;
    }


    private Vis_Cell getVisCell(Course top, Course bot, boolean top_sec, boolean bot_sec,
                                int vcase) {
    /*
        Course Top = Course that starts in the left side of the cell
        Course bot = Course that starts in the right side of the cell
        top_sec; if true the time data in question refers to the secondary end time
        bot_sec; if true the time data in question refers to the secondary start time
    */

	/*
        CASE 1, Top = Scaled, Middle = Remaining. Bot = 0; TOP = Course color, Middle = White
    	CASE 2, Top = 0, Middle = Remaining, Bot = Scaled; BOT = Course color, Middle = White
        CASE 3, Top = Scaled, Middle = Remaining, Bot = Scaled; Top/Bot = Course colors,
        Middle = White
        CASE 4, Top = 0, Middle = FULL Width, Bot = 0; Middle = Course color
        CASE 5, Top = 0, Middle = FULL Width, Bot = 0; Middle = White
    */

        int top_end;
        int bot_start;

        if (top != null) {
            if (top_sec) {
                top_end = top.getEnd2();
            } else
                top_end = top.getEnd1();
        } else {
            top_end = none;
        }

        if (bot != null) {
            if (bot_sec) {
                bot_start = bot.getStart2();
            } else {
                bot_start = bot.getStart1();
            }
        } else {
            bot_start = none;
        }

        int top_height = getTopHeight(top_end);
        int bot_height = getBotHeight(bot_start);

        //Vis_Cell (top, mid, bot, color, color, color)
        final int max_height = 300;
        switch (vcase) {
            case 1:
                return new Vis_Cell(top_height, (max_height - top_height), 0, top.getID(), white,
                        white);
            case 2:
                return new Vis_Cell(0, (max_height - bot_height), bot_height, white, white,
                        bot.getID());
            case 3:
                return new Vis_Cell(top_height, (max_height - top_height - bot_height), bot_height,
                        top.getID(), white, bot.getID());
            case 4:
                // Assumes that if a time entire hour is taken by a course then it the course is
                // passed through the TOP course parameter
                return new Vis_Cell(0, max_height, 0, white, top.getID(), white);
            case 5:
                return new Vis_Cell(0, max_height, 0, white, white, white);
            default:
                //just leave it empty
                return new Vis_Cell(0, max_height, 0, white, white, white);
        }
    }

    private int courseTimeToMinutes(int crsTimeFormat) {
        final int min_hr = 60;
        int hours = getHoursFromTime(crsTimeFormat);
        int minutes = getTimeMinutes(crsTimeFormat);
        return (hours * min_hr) + minutes;
    }

    private int getHoursFromTime(int crstime) {
        return crstime / 100;
    }

    private int getTimeMinutes(int crstime) {
        return crstime % 100;
    }

    private int getTopHeight(int EndTime) {
        if (EndTime == -1) {
            System.out.println("getTopHeight call: EndTime is empty");
            return 0;
        }
        final int max_height = 300;
        final int min_hour = 60;
        return (getTimeMinutes(EndTime) / 60) * max_height;
    }

    private int getBotHeight(int StartTime) {
        if (StartTime == -1) {
            System.out.println("getBotHeight call: StartTime is empty");
            return 0;
        }
        final int max_height = 300;
        final int min_hour = 60;
        return ((min_hour - getTimeMinutes(StartTime)) / 60) * max_height;
    }

    private int getEndHour(int input) {
        return (int) Math.ceil((double) input / 100);
    }

    private int getColumnWidth() {
        //return Math.round(dpWidth/(8));
        return pxWidth / (days_week + 1);
    }

    private View timeTextView(String input) {
        View table_view = getLayoutInflater().inflate(R.layout.vis_time, null);
        LinearLayout lv = (LinearLayout) table_view.findViewById(R.id.vis_ll);

        TextView tv = (TextView) table_view.findViewById(R.id.tv_time);
        tv.setText(input);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setLayoutParams(new TableRow.LayoutParams(getColumnWidth() - 10,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        return table_view;
    }

}