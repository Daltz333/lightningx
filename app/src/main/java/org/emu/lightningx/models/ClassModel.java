package org.emu.lightningx.models;

import org.emu.lightningx.services.GlobalStateService;
import org.emu.lightningx.util.GlobalUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClassModel {
    // Represents whether or not the current instance of this class is selected in the UI
    private boolean isSelected = false;

    // We should always "lazy" load objects that are potentially heavy
    // as this can impact application startup times
    private final ArrayList<StudentModel> students;

    // Name of the class
    private final String name;

    // Date that the class was created
    private String classCreationDate;

    private int uuid;

    private ArrayList<AttendanceDateModel> attendanceDates;

    public ClassModel(String name) {
        students = new ArrayList<>();
        classCreationDate = GlobalUtil.getCurrentDateFormatted();

        this.name = name;
    }

    /**
     * Get the number of students in this class
     * @return
     */
    public int getNumStudents() {
        return students.size();
    }

    /**
     * To be used by GlobalStateService to update this object
     *
     * whenever a class is selected
     * @param attendanceDates
     */
    public void updateAttendanceDates(ArrayList<AttendanceDateModel> attendanceDates) {
        this.attendanceDates = attendanceDates;
    }

    /**
     * Iterates over the list of students, and returns whether or not a student is present
     *
     * A student is considered NOT present if it's not in this list.
     * @param student
     * @param date
     * @return
     */
    public boolean isStudentPresent(StudentModel student, String date) {
        return getAttendanceDate(date).isStudentPresent(student.getStudentId());
    }

    public void markStudentPresent(StudentModel student, String date, boolean present) {
        AttendanceDateModel attendance = getAttendanceDate(date);

        if (attendance.isStudentPresent(student.getStudentId())) {
            attendance.removeStudent(student);
        } else {
            attendance.addPresentStudent(student);
        }
    }

    public AttendanceDateModel getAttendanceDate(String date) {
        if (attendanceDates != null) {
            if (!attendanceDates.isEmpty()) {
                for (AttendanceDateModel attendance : attendanceDates) {
                    if (attendance.getAttendanceDate().equalsIgnoreCase(date)) {
                        return attendance;
                    }
                }
            }

            AttendanceDateModel newDate = new AttendanceDateModel(GlobalStateService.getInstance().getSelectedDate(), this);
            attendanceDates.add(newDate);

            return newDate;
        }

        return null;
    }

    /**
     * Get the name of the class
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the uuid of the current class
     * @return
     */
    public int getUuid() {
        return this.uuid;
    }

    /**
     * Sets the uuid of the current class
     * @param uuid
     */
    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the class creation date
     * Formatted in form DD/MM/YYYY
     * @return
     */
    public String getClassCreationDate() {
        return this.classCreationDate;
    }

    /**
     * Set the class creation date
     * Should be formatted in form DD/MM/YYYY
     * @param classCreationDate
     */
    public void setClassCreationDate(String classCreationDate) {
        this.classCreationDate = classCreationDate;
    }

    /**
     * Add a new student to this class
     * @param student
     */
    public void addStudent(StudentModel student) {
        students.add(student);
    }

    /**
     * Remove a student from this class
     * @param student
     */
    public void removeStudent(StudentModel student) {
        students.remove(student);
    }

    public ArrayList<StudentModel> getStudents() {
        return students;
    }

    /**
     * Retrieve whether or not this class is currently selected in the UI
     * @return
     */
    public boolean getIsSelected() {
        return isSelected;
    }

    /**
     * Set that this class is currently selected in the UI
     * May be useful in the future for something
     * @param state
     */
    public void setIsSelected(boolean state) {
        isSelected = state;
    }
}
