package uhmanoa.droid_sch;

/**
 * Created by supah_000 on 1/27/2015.
 */
public class Star_obj {
    private String course_name;
    private int CRN;
    private int ID; // Incase user for some reason stars both a course and class
    private int Semester;

    //Constructor
    public Star_obj(String pCrsname, int pCRN, int pID, int pSem) {
        pID= ID;
        CRN = pCRN;
        Semester = pSem;
        course_name = pCrsname;
    }

    public int getCRN() {
        return CRN;
    }
    public int getID() { return ID; }
    public int getSemester() { return Semester; }

    public String getCourse() {
        return course_name;
    }

    /* Class refers to a course with a specific CRN,
    *  Course refers to a course such as CHEM 161, where there maybe multiple lectures
    *  with different CRN's and Times */
    public boolean isClass() {
        return (CRN != -1);
    }

}
