package uhmanoa.droid_sch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/* This is a helper class for interacting with the database such as retrieving, inserting
* or deleting data. Functionality for this class will have to be implemented as more functionality
* is added to the core application */

public class SQL_DataSource {
    /* Class Member Variables */
    private SQL_Helper dbhelper;
    private SQLiteDatabase database;

    private String[] STAR_COLUMN = {
            SQL_Helper.COLUMN_ID,
            SQL_Helper.COLUMN_CRN,
            SQL_Helper.COLUMN_CRS,
            SQL_Helper.COLUMN_TITL,
            SQL_Helper.COLUMN_SEM,
            SQL_Helper.COLUMN_YEAR
    };

    enum STAR_ENUM {
        COLUMN_ID,
        COLUMN_CRN,
        COLUMN_CRS,
        COLUMN_TITL,
        COLUMN_SEM,
        COLUMN_YEAR
    }

    private String[] SCH_COLUMN = {
            SQL_Helper.COLUMN_ID,
            SQL_Helper.COLUMN_CRN,
            SQL_Helper.COLUMN_YEAR,
            SQL_Helper.COLUMN_SEM
    };

    enum SCH_ENUM {
        COLUMN_ID,
        COLUMN_CRN,
        COLUMN_YEAR,
        COLUMN_SEM
    }

    private String[] TSCH_COLUMN = {
            SQL_Helper.COLUMN_ID,
            SQL_Helper.COLUMN_CRN,
            SQL_Helper.COLUMN_YEAR,
            SQL_Helper.COLUMN_SEM
    };

    private String[] COURSE_COLUMN = {
            SQL_Helper.COLUMN_CRN,
            SQL_Helper.COLUMN_CRS,
            SQL_Helper.COLUMN_SECT,
            SQL_Helper.COLUMN_TITL,
            SQL_Helper.COLUMN_SEM,
            SQL_Helper.COLUMN_YEAR,
            SQL_Helper.COLUMN_START,
            SQL_Helper.COLUMN_START2,
            SQL_Helper.COLUMN_END,
            SQL_Helper.COLUMN_END2,
            SQL_Helper.COLUMN_ROOM,
            SQL_Helper.COLUMN_ROOM2,
            SQL_Helper.COLUMN_PROF,
            SQL_Helper.COLUMN_CREDIT,
            SQL_Helper.COLUMN_SEAT,
            SQL_Helper.COLUMN_WAITL,
            SQL_Helper.COLUMN_WAITLA,
            SQL_Helper.COLUMN_DATES,
            SQL_Helper.COLUMN_MJR
    };

    enum COURSE_ENUM {
        COLUMN_CRN,
        COLUMN_CRS,
        COLUMN_SECT,
        COLUMN_TITL,
        COLUMN_SEM,
        COLUMN_YEAR,
        COLUMN_START,
        COLUMN_START2,
        COLUMN_END,
        COLUMN_END2,
        COLUMN_ROOM,
        COLUMN_ROOM2,
        COLUMN_PROF,
        COLUMN_CREDIT,
        COLUMN_SEAT,
        COLUMN_WAITL,
        COLUMN_WAITLA,
        COLUMN_DATES,
        COLUMN_MJR
    }

    private String[] CFOCUS_COLUMN = {
            SQL_Helper.COLUMN_CRS,
            SQL_Helper.COLUMN_SEM,
            SQL_Helper.COLUMN_YEAR,
            SQL_Helper.COLUMN_FGA,
            SQL_Helper.COLUMN_FGB,
            SQL_Helper.COLUMN_FGC,
            SQL_Helper.COLUMN_FS,
            SQL_Helper.COLUMN_FW,
            SQL_Helper.COLUMN_DA,
            SQL_Helper.COLUMN_DB,
            SQL_Helper.COLUMN_DH,
            SQL_Helper.COLUMN_DL,
            SQL_Helper.COLUMN_DP,
            SQL_Helper.COLUMN_DY,
            SQL_Helper.COLUMN_HSL,
            SQL_Helper.COLUMN_NI,
            SQL_Helper.COLUMN_ETH,
            SQL_Helper.COLUMN_HAP,
            SQL_Helper.COLUMN_OC,
            SQL_Helper.COLUMN_WI
    };

    enum CFOC_ENUM {
        COLUMN_CRS,
        COLUMN_SEM,
        COLUMN_YEAR,
        COLUMN_FGA,
        COLUMN_FGB,
        COLUMN_FGC,
        COLUMN_FS,
        COLUMN_FW,
        COLUMN_DA,
        COLUMN_DB,
        COLUMN_DH,
        COLUMN_DL,
        COLUMN_DP,
        COLUMN_DY,
        COLUMN_HSL,
        COLUMN_NI,
        COLUMN_ETH,
        COLUMN_HAP,
        COLUMN_OC,
        COLUMN_WI
    }

    private String[] CDAYS_COLUMN = {
            SQL_Helper.COLUMN_CRN,
            SQL_Helper.COLUMN_YEAR,
            SQL_Helper.COLUMN_SEM,
            SQL_Helper.COLUMN_SECDAY,
            SQL_Helper.COLUMN_SUN,
            SQL_Helper.COLUMN_MON,
            SQL_Helper.COLUMN_TUE,
            SQL_Helper.COLUMN_WED,
            SQL_Helper.COLUMN_THR,
            SQL_Helper.COLUMN_FRI,
            SQL_Helper.COLUMN_SAT
    };

    enum CDAY_ENUM {
        COLUMN_CRN,
        COLUMN_YEAR,
        COLUMN_SEM,
        COLUMN_SECDAY,
        COLUMN_SUN,
        COLUMN_MON,
        COLUMN_TUE,
        COLUMN_WED,
        COLUMN_THR,
        COLUMN_FRI,
        COLUMN_SAT
    }

    private String[] PREF_COLUMN = {
            SQL_Helper.COLUMN_PNAME,
            SQL_Helper.COLUMN_CRSMIN,
            SQL_Helper.COLUMN_SUN,
            SQL_Helper.COLUMN_MON,
            SQL_Helper.COLUMN_TUE,
            SQL_Helper.COLUMN_WED,
            SQL_Helper.COLUMN_THR,
            SQL_Helper.COLUMN_FRI,
            SQL_Helper.COLUMN_SAT
    };

    enum PREF_ENUM {
        COLUMN_PNAME,
        COLUMN_CRSMIN,
        COLUMN_SUN,
        COLUMN_MON,
        COLUMN_TUE,
        COLUMN_WED,
        COLUMN_THR,
        COLUMN_FRI,
        COLUMN_SAT
    }

    private String[] PBLOCK_COLUMN = {
            SQL_Helper.COLUMN_PNAME,
            SQL_Helper.COLUMN_START,
            SQL_Helper.COLUMN_END,
            SQL_Helper.COLUMN_SUN,
            SQL_Helper.COLUMN_MON,
            SQL_Helper.COLUMN_TUE,
            SQL_Helper.COLUMN_WED,
            SQL_Helper.COLUMN_THR,
            SQL_Helper.COLUMN_FRI,
            SQL_Helper.COLUMN_SAT
    };

    enum PBLOCK_ENUM {
        COLUMN_PNAME,
        COLUMN_START,
        COLUMN_END,
        COLUMN_SUN,
        COLUMN_MON,
        COLUMN_TUE,
        COLUMN_WED,
        COLUMN_THR,
        COLUMN_FRI,
        COLUMN_SAT
    }

    public void open() throws SQLException {
        database = dbhelper.getWritableDatabase();
    }

    public SQL_DataSource(Context context) {
        dbhelper = new SQL_Helper(context);
    }

    /* Closes Database Helper */
    public void close() {
        dbhelper.close();
    }

    //--------------------------- STARRED OBJECT DB HELPER FUNCTIONS----------------------------//
    //save starred
    /* Stores Favorite Object into corresponding Table
    * It then runs a function for converting the row into a
     * fav_obj (object/class representation of the Favorite Object */

    public long saveStar(Star_obj pStar) {
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(SQL_Helper.COLUMN_CRS, pStar.getCourse());
        values.put(SQL_Helper.COLUMN_CRN, pStar.getCRN());
        values.put(SQL_Helper.COLUMN_TITL, pStar.getCourseTitle());
        values.put(SQL_Helper.COLUMN_SEM, pStar.getSemester());
        values.put(SQL_Helper.COLUMN_YEAR, pStar.getYear());

        long id = database.insert(SQL_Helper.TABLE_STAR, null, values);
        database.setTransactionSuccessful();
        database.endTransaction();
        return id;
    }

    //delete starred
    public void deleteStar(long id) {
        database.beginTransaction();
        System.out.println("Deleting Starred Object with id: " + id);
        database.delete(SQL_Helper.TABLE_STAR, SQL_Helper.COLUMN_ID + " = " + id, null);
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    //get all starred
    /* Returns corresponding Starred Row Data */
    private Star_obj cursorToStarObj(Cursor curs) {
        Star_obj so = new Star_obj(
                curs.getString(STAR_ENUM.COLUMN_CRS.ordinal()),
                curs.getString(STAR_ENUM.COLUMN_TITL.ordinal()),
                curs.getInt(STAR_ENUM.COLUMN_CRN.ordinal()),
                curs.getInt(STAR_ENUM.COLUMN_ID.ordinal()),
                curs.getInt(STAR_ENUM.COLUMN_SEM.ordinal()),
                curs.getInt(STAR_ENUM.COLUMN_YEAR.ordinal()));
        return so;
    }

    public ArrayList<Star_obj> getAllStar(int sem, int yr) {
        database.beginTransaction();
        String whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = ?";
        String whereArgs[] = {
                String.valueOf(sem),
                String.valueOf(yr)
        };

        ArrayList<Star_obj> all_starobj = new ArrayList<>();
        Cursor curse = database.query(SQL_Helper.TABLE_STAR, STAR_COLUMN, whereClause, whereArgs,
                null, null, SQL_Helper.COLUMN_CRN + " ASC");
        curse.moveToFirst();
        while (!curse.isAfterLast()) {
            Star_obj so = cursorToStarObj(curse);
            all_starobj.add(so);
            curse.moveToNext();
        }
        curse.close();
        database.setTransactionSuccessful();
        database.endTransaction();
        return all_starobj;
    }

    //delete all starred
    public void deleteAllStar() {
        database.beginTransaction();
        database.delete(SQL_Helper.TABLE_STAR, null, null);
        database.setTransactionSuccessful();
        database.endTransaction();
        System.out.println("Removed all Starred/Favorite Courses");
    }

    //--------------------------- STARRED OBJECT DB HELPER FUNCTIONS----------------------------//

    //--------------------------- SCHEDULE DB HELPER FUNCTIONS----------------------------//

    //Finds unique ID for Schedule
    private long getUniqueSchedID() {
        int id = 0;
        while(true) {
            String whereClause = SQL_Helper.COLUMN_ID + " = ?";
            String whereArgs[] = {
                    String.valueOf(id)
            };

            Cursor curse = database.query(SQL_Helper.TABLE_SCH, SCH_COLUMN, whereClause, whereArgs,
                    null, null, SQL_Helper.COLUMN_CRN + " ASC");

            if(!(curse.moveToFirst()) || curse.getCount() == 0 ) {
                //cursor is empty so this id is unique
                return (long) id;
            } else {
                id++;
            }
        }
    }

    // Save Schedule
    public void saveSched(Schedule sch) {
        database.beginTransaction();

        ArrayList<Course> crs = sch.getCourses();

        long id = getUniqueSchedID();
        int yr = sch.getYear();
        int sem = sch.getSemester();

        for(int x = 0; x < crs.size(); x++) {
            ContentValues values = new ContentValues();
            Course c = crs.get(x);
            values.put(SQL_Helper.COLUMN_ID, id);
            values.put(SQL_Helper.COLUMN_CRN, c.getCrn());
            values.put(SQL_Helper.COLUMN_YEAR, yr);
            values.put(SQL_Helper.COLUMN_SEM, sem);
            database.insert(SQL_Helper.TABLE_SCH, null, values);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public ArrayList<Schedule> getAllSchedules() {
        ArrayList<Schedule> sch = new ArrayList<>();
        return sch;
    }

    public Schedule getSchedule(long id) {
        Schedule s = null;

        return s;
    }

    //--------------------------- SCHEDULE DB HELPER FUNCTIONS----------------------------//

    //--------------------------- TEMP SCHEDULE DB HELPER FUNCTIONS----------------------------//

    public ArrayList<Schedule> getAllTempSchedules() {
        ArrayList<Schedule> sch = new ArrayList<>();
        return sch;
    }

    public void saveTSched(Schedule sch) {
        database.beginTransaction();

        ArrayList<Course> crs = sch.getCourses();

        long id = sch.getID();
        int yr = sch.getYear();
        int sem = sch.getSemester();

        for(int x = 0; x < crs.size(); x++) {
            ContentValues values = new ContentValues();
            Course c = crs.get(x);
            values.put(SQL_Helper.COLUMN_ID, id);
            values.put(SQL_Helper.COLUMN_CRN, c.getCrn());
            values.put(SQL_Helper.COLUMN_YEAR, yr);
            values.put(SQL_Helper.COLUMN_SEM, sem);
            database.insert(SQL_Helper.TABLE_TSCH, null, values);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    //--------------------------- TEMP SCHEDULE DB HELPER FUNCTIONS----------------------------//
    //--------------------------- COURSE SEARCH DB HELPER FUNCTIONS ------------------------------//

    public boolean courseDataExists(int sem, int year) {
        //simple check for if data exists
        String whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = ?";
        String whereArgs[] = {
                String.valueOf(sem),
                String.valueOf(year)
        };
        Cursor curse = database.query(SQL_Helper.TABLE_COURSE, COURSE_COLUMN, whereClause, whereArgs,
                null, null, null);
        if(!(curse.moveToFirst()) || curse.getCount() == 0) {
            return false;
        }

        return true;
    }

    public Star_obj findMatch(int sem, int year, String search_text) {
        Star_obj so = null;
        Boolean isCRN = Character.isDigit(search_text.charAt(0));

        String whereClause = "";
        if(isCRN) {
            whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = " +
                    "? AND " + SQL_Helper.COLUMN_CRN + " MATCH ?";
        } else {
            whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = " +
                    "? AND " + SQL_Helper.COLUMN_CRS + " MATCH ?";
        }

        String whereArgs[] = {
            String.valueOf(sem),
            String.valueOf(year),
            search_text
        };

        Cursor curse = database.query(SQL_Helper.TABLE_COURSE, COURSE_COLUMN, whereClause, whereArgs,
                null, null, SQL_Helper.COLUMN_CRS + " ASC");
        curse.moveToFirst();
        if(curse.getCount() != 0 ){
            Course c = cursorToCourse(curse);
            so = new Star_obj(c.getCourse(), c.getTitle(), c.getCrn(), -1, sem, year);
        }
        curse.close();

        return so;
    }

    public ArrayList<Course> getSearchResults(int sem, int year, String search_text) {
        database.beginTransaction();

        //Results Based on CRN
        String whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = ? AND " +
                "(" + SQL_Helper.COLUMN_CRN + " MATCH ? OR " + SQL_Helper.COLUMN_CRS + " MATCH ? " +
                " OR " + SQL_Helper.COLUMN_TITL + " MATCH ?)";
        String whereArgs[] = {
                String.valueOf(sem),
                String.valueOf(year),
                search_text,
                search_text,
                search_text
        };

        ArrayList<Course> results = new ArrayList<>();
        Cursor curse = database.query(SQL_Helper.TABLE_COURSE, COURSE_COLUMN, whereClause, whereArgs,
                null, null, SQL_Helper.COLUMN_CRS + " ASC");
        curse.moveToFirst();
        while (!curse.isAfterLast()) {
            Course c = cursorToCourse(curse);
            results.add(c);
            curse.moveToNext();
        }
        curse.close();

        database.setTransactionSuccessful();
        database.endTransaction();
        return results;
    }

    //--------------------------- COURSE SEARCH DB HELPER FUNCTIONS ------------------------------//

    //--------------------------- COURSE DB HELPER FUNCTIONS ----------------------------------//

    private Course cursorToCourse(Cursor curse) {
        Course c;
        int crn = curse.getInt(COURSE_ENUM.COLUMN_CRN.ordinal());
        int sem = curse.getInt(COURSE_ENUM.COLUMN_SEM.ordinal());
        int year = curse.getInt(COURSE_ENUM.COLUMN_YEAR.ordinal());
        String mjr = curse.getString(COURSE_ENUM.COLUMN_MJR.ordinal());

        String course = curse.getString(COURSE_ENUM.COLUMN_CRS.ordinal());
        ArrayList<String> focus = getFocusArray(course, sem, year);
        ArrayList<Character> day1 = getDayArray(sem, year, crn, 0);
        ArrayList<Character> day2 = getDayArray(sem, year, crn, 1);


        if(curse.getInt(COURSE_ENUM.COLUMN_START2.ordinal()) != 9999) {
            c = new Course(
                    curse.getString(COURSE_ENUM.COLUMN_CRS.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_TITL.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_CRN.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_CREDIT.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_PROF.ordinal()),
                    day1,
                    day2,
                    curse.getInt(COURSE_ENUM.COLUMN_START.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_START2.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_END.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_END2.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_ROOM.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_ROOM2.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_SECT.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_SEAT.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_WAITL.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_WAITLA.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_DATES.ordinal()),
                    "");
            c.setFocusReqs(focus);
            c.setYear(year);
            c.setSemester(sem);
            c.setMajor(mjr);
        } else {
            c = new Course(
                    curse.getString(COURSE_ENUM.COLUMN_CRS.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_TITL.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_CRN.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_CREDIT.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_PROF.ordinal()),
                    day1,
                    curse.getInt(COURSE_ENUM.COLUMN_START.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_END.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_ROOM.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_SECT.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_SEAT.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_WAITL.ordinal()),
                    curse.getInt(COURSE_ENUM.COLUMN_WAITLA.ordinal()),
                    curse.getString(COURSE_ENUM.COLUMN_DATES.ordinal()),
                    "");
            c.setFocusReqs(focus);
            c.setYear(year);
            c.setSemester(sem);
            c.setMajor(mjr);
        }
        return c;
    }

    public Course getCourse(int sem, int year, int crn) {
        database.beginTransaction();
        String whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = " +
                "? AND " + SQL_Helper.COLUMN_CRN + " = ?";
        String whereArgs[] = {
                String.valueOf(sem),
                String.valueOf(year),
                String.valueOf(crn)
        };

        ArrayList<Character> day_array = new ArrayList<>();
        Cursor curse = database.query(SQL_Helper.TABLE_CDAY, CDAYS_COLUMN, whereClause, whereArgs,
                null, null, null);
        curse.moveToFirst();

        Course c = null;
        if(curse.getCount() != 0) {
            c = cursorToCourse(curse);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        return c;
    }

    public void saveCourse(Course crs) {
        database.beginTransaction();

        int year = crs.getYear();
        int sem = crs.getSemester();

        ContentValues values = new ContentValues();
        values.put(SQL_Helper.COLUMN_CRN, crs.getCrn());
        values.put(SQL_Helper.COLUMN_CRS, crs.getCourse());
        values.put(SQL_Helper.COLUMN_SECT, crs.getSection());
        values.put(SQL_Helper.COLUMN_TITL, crs.getTitle());
        values.put(SQL_Helper.COLUMN_SEM, crs.getSemester());
        values.put(SQL_Helper.COLUMN_YEAR, crs.getYear());
        values.put(SQL_Helper.COLUMN_START, crs.getStart1());
        values.put(SQL_Helper.COLUMN_START2, crs.getStart2());
        values.put(SQL_Helper.COLUMN_END, crs.getEnd1());
        values.put(SQL_Helper.COLUMN_END2, crs.getEnd2());
        values.put(SQL_Helper.COLUMN_ROOM, crs.getRoom1());
        values.put(SQL_Helper.COLUMN_ROOM2, crs.getRoom2());
        values.put(SQL_Helper.COLUMN_PROF, crs.getProfessor());
        values.put(SQL_Helper.COLUMN_CREDIT, crs.getCredits());
        values.put(SQL_Helper.COLUMN_SEAT, crs.getSeats_avail());
        values.put(SQL_Helper.COLUMN_WAITL, crs.getWaitlisted());
        values.put(SQL_Helper.COLUMN_WAITLA, crs.getWait_avail());
        values.put(SQL_Helper.COLUMN_DATES, crs.getDates());
        values.put(SQL_Helper.COLUMN_MJR, crs.getMajor());
        database.insert(SQL_Helper.TABLE_COURSE, null, values);

        //Store Day Values
        ArrayList<Character> days = crs.getDays1();
        ArrayList<Integer> dayarr = getDayArray(days);
        saveCDays(sem, year, crs.getCrn(), false, dayarr);

        //Store Day2 Values
        if(crs.getStart2() != 9999) {
            days = crs.getDays2();
            dayarr = getDayArray(days);
            saveCDays(sem, year, crs.getCrn(), true, dayarr);
        }

        //Check for Existing Focus Reqs
        String whereClause = SQL_Helper.COLUMN_CRS + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = ?" +
                " AND " + SQL_Helper.COLUMN_SEM + " = ?";
        String whereArgs[] = {
                crs.getCourse(),
                String.valueOf(crs.getYear()),
                String.valueOf(crs.getSemester())
        };

        Cursor curse = database.query(SQL_Helper.TABLE_CFOCUS, CFOCUS_COLUMN, whereClause, whereArgs,
                null, null, null);
        if(!(curse.moveToFirst()) || curse.getCount() == 0 ) {
            //Save Focus Values
            saveCFocus(sem, year, crs.getCourse(), crs.getFocusReqs());
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private ArrayList<String> getAllFocusStrings() {
        ArrayList<String> foc = new ArrayList<>();
        foc.add("FGA");
        foc.add("FGB");
        foc.add("FGC");
        foc.add("FS");
        foc.add("FW");
        foc.add("DA");
        foc.add("DB");
        foc.add("DH");
        foc.add("DL");
        foc.add("DP");
        foc.add("DY");
        foc.add("HSL");
        foc.add("NI");
        foc.add("ETH");
        foc.add("HAP");
        foc.add("OC");
        foc.add("WI");
        return foc;
    }

    private void saveCFocus(int sem, int year, String crs, ArrayList<String> focus) {
        ContentValues cv = new ContentValues();
        cv.put(SQL_Helper.COLUMN_CRS, crs);
        cv.put(SQL_Helper.COLUMN_SEM, sem);
        cv.put(SQL_Helper.COLUMN_YEAR, year);

        ArrayList<String> allFocus = getAllFocusStrings();
        int col = 3; //start all column 3
        for(int x = 0; x < allFocus.size(); x++) {
            if(focus.contains(allFocus.get(x))) {
                cv.put(CFOCUS_COLUMN[col], 1); //1 = true
            } else {
                cv.put(CFOCUS_COLUMN[col], 0); //0 = false
            }
            col++;
        }
        database.insert(SQL_Helper.TABLE_CFOCUS, null, cv);
    }

    private void saveCDays(int sem, int year, int CRN, boolean secday, ArrayList<Integer> day) {
        ContentValues cv = new ContentValues();
        cv.put(SQL_Helper.COLUMN_CRN, CRN);
        cv.put(SQL_Helper.COLUMN_YEAR, year);
        cv.put(SQL_Helper.COLUMN_SEM, sem);
        if(secday) {
            cv.put(SQL_Helper.COLUMN_SECDAY, 1);
        } else {
            cv.put(SQL_Helper.COLUMN_SECDAY, 0);
        }
        cv.put(SQL_Helper.COLUMN_SUN, day.get(0));
        cv.put(SQL_Helper.COLUMN_MON, day.get(1));
        cv.put(SQL_Helper.COLUMN_TUE, day.get(2));
        cv.put(SQL_Helper.COLUMN_WED, day.get(3));
        cv.put(SQL_Helper.COLUMN_THR, day.get(4));
        cv.put(SQL_Helper.COLUMN_FRI, day.get(5));
        cv.put(SQL_Helper.COLUMN_SAT, day.get(6));
        database.insert(SQL_Helper.TABLE_CDAY, null, cv);
    }

    private ArrayList<String> getFocusArray(String crs, int sem, int year) {
        String whereClause = SQL_Helper.COLUMN_YEAR + " = ? AND " + SQL_Helper.COLUMN_CRS + " = ?" +
                " AND " + SQL_Helper. COLUMN_SEM+ " = ?";
        String whereArgs[] = {
                String.valueOf(year),
                crs,
                String.valueOf(sem)
        };

        ArrayList<String> focus = new ArrayList<>();
        Cursor curse = database.query(SQL_Helper.TABLE_CFOCUS, CFOCUS_COLUMN, whereClause, whereArgs,
                null, null, null);
        curse.moveToFirst();
        if(curse.getCount() != 0) {
            //start at col 3 (FGA)
            int col = 3;
            ArrayList<String> allfoc = getAllFocusStrings();
            for(int x = 0; x < allfoc.size(); x++) {
                if(curse.getInt(col) == 1) {
                    focus.add(allfoc.get(x));
                }
                col++;
            }
        }
        return focus;
    }

    private ArrayList<Character> getDayArray (int sem, int year, int CRN, int sec) {
        String whereClause = SQL_Helper.COLUMN_SEM + " = ? AND " + SQL_Helper.COLUMN_YEAR + " = " +
                "? AND " + SQL_Helper.COLUMN_CRN + " = ? AND " + SQL_Helper. COLUMN_SECDAY + " = ?";
        String whereArgs[] = {
                String.valueOf(sem),
                String.valueOf(year),
                String.valueOf(CRN),
                String.valueOf(sec)
        };

        ArrayList<Character> day_array = new ArrayList<>();
        Cursor curse = database.query(SQL_Helper.TABLE_CDAY, CDAYS_COLUMN, whereClause, whereArgs,
                null, null, null);
        curse.moveToFirst();
        if(curse.getCount() != 0) {
            if (curse.getInt(CDAY_ENUM.COLUMN_SUN.ordinal()) == 1) {
                day_array.add('U');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_MON.ordinal()) == 1) {
                day_array.add('M');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_TUE.ordinal()) == 1) {
                day_array.add('T');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_WED.ordinal()) == 1) {
                day_array.add('W');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_THR.ordinal()) == 1) {
                day_array.add('R');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_FRI.ordinal()) == 1) {
                day_array.add('F');
            }
            if (curse.getInt(CDAY_ENUM.COLUMN_SAT.ordinal()) == 1) {
                day_array.add('S');
            }
        }
        curse.close();
        return day_array;
    }

    private ArrayList<Integer> getDayArray(ArrayList<Character> days) {
        ArrayList<Integer> day_array = new ArrayList<>();
        if(days.contains('U')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('M')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('T')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('W')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('R')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('F')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        if(days.contains('S')) {
            day_array.add(1);
        } else {
            day_array.add(0);
        }

        return day_array;
    }

    //--------------------------- COURSE DB HELPER FUNCTIONS ----------------------------------//


    public void deleteSchedule(Schedule s) {
        long id = s.getID();
        System.out.println("Deleted Schedule with id: " + id);
        database.delete(SQL_Helper.TABLE_SCH, SQL_Helper.COLUMN_ID + " = " + id, null);
    }

    public void clearCourseData() {
        database.delete(SQL_Helper.TABLE_COURSE, null, null);
        database.delete(SQL_Helper.TABLE_CFOCUS, null, null);
        database.delete(SQL_Helper.TABLE_CDAY, null, null);
    }

    public void clearTempSch() {
        database.delete(SQL_Helper.TABLE_TSCH, null, null);
    }

}