package org.emu.lightningx.models;

import java.util.ArrayList;

public class ClassModel {
    // Represents whether or not the current instance of this class is selected in the UI
    private boolean isSelected = false;

    // We should always "lazy" load objects that are potentially heavy
    // as this can impact application startup times
    private ArrayList<StudentModel> students;

    public ClassModel() {
        students = new ArrayList<StudentModel>();
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
