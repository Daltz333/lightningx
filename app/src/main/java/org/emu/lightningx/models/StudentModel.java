package org.emu.lightningx.models;

public class
StudentModel {
    public String studentName;
    public String studentId;
    // TODO, replace to internet
    public int studentProfileUriPath;

    public StudentModel(String name, String id, int resourceId) {
        studentName = name;
        studentId = id;
        studentProfileUriPath = resourceId;
    }

    /**
     * Get the students name.
     * We may convert this into two separate fields: first and last name
     * @return
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Get the student UUID
     * @return
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Get the student profile picture UUID
     *
     * We may not implement this
     * @return
     */
    public int getStudentProfileUriPath() {
        return studentProfileUriPath;
    }
}
