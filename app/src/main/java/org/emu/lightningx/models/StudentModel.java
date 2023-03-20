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

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getStudentProfileUriPath() {
        return studentProfileUriPath;
    }
}
