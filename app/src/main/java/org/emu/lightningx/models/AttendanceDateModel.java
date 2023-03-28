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
     * Checks to see if a student is in the attendance list for this date
     *
     * It can be assumed that a student is not present if
     * it's not contained in this objects ArrayList
     * @param id
     * @return
     */
    public boolean isStudentPresent(String id) {
        // Iterate over the students list
        // and compare based on ID
        if (!studentsPresent.isEmpty()) {
            for (StudentModel student : studentsPresent) {
                if (student.getStudentId().equalsIgnoreCase(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the attendance date. This can be useful for UI
     * @return
     */
    public String getAttendanceDate() {
        return attendanceDate;
    }
}
