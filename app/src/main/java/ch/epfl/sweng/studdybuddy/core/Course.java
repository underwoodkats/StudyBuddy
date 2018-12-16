package ch.epfl.sweng.studdybuddy.core;

/**
 * Class representing courses
 */

public class Course
{
    private String courseName;
    private ID<Course> courseID;

    /**
     * Empty constructor
     */
    public Course() {}

    /**
     * Create a course from a Course name
     * @param courseName the course name
     */
    public Course(String courseName)
    {
        this();
        this.courseName = courseName;
        courseID = new ID<>(courseName);
    }

    /**
     * Create a new course from an existing one
     * @param sourceCourse the course to be copied
     */
    public Course(Course sourceCourse)
    {
        this.courseName = sourceCourse.getCourseName();
        this.courseID = sourceCourse.getCourseID();
    }

    /**
     *
     * @return the name of the course
     */
    public String getCourseName()
    {
        return  courseName;
    }

    /**
     * Set a new name to the course
     * @param name the name to be set
     */
    public void setCourseName(String name) { courseName = name; }

    /**
     *
     * @return the id of the course
     */
    public ID<Course> getCourseID()
    {
        return courseID;
    }

    /**
     * Set a new id to the course
     * @param courseID the id to be set
     */
    public void setCourseID(ID<Course> courseID)
    {
        this.courseID = courseID;
    }


}
