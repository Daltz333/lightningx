package org.emu.lightningx.models;

import java.util.ArrayList;

public class AttendanceDateModel {
    private final String attendanceDate;
    private final ArrayList<StudentModel> studentsPresent;
    private ClassModel attendanceClass;

    private int datebaseUuid;

    public AttendanceDateModel(String attendanceDate, ClassModel attendanceClass) {
        this.attendanceDate = attendanceDate;

        studentsPresent = new ArrayList<>();
    }

    /**
     * Add a student as present. A student that is not present
     * in the list can be assumed as not present.
     * @param student
     */
    public void addPresentStudent(StudentModel student) {
        studentsPresent.add(student);
    }

    /**
     * Remove a student from the present list.
     * This function should be called when the user deselects a student as present
     * @param student
     * @return
     */
    public boolean removeStudent(StudentModel student) {
        return studentsPresent.remove(student);
    }

    /**
     * Get the attendance date. This can be useful for UI
     * @return
     */
    public String getAttendanceDate() {
        return attendanceDate;
    }
}
