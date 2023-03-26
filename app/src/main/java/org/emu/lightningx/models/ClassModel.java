package org.emu.lightningx.models;

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

    public ClassModel(String name) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        students = new ArrayList<StudentModel>();
        classCreationDate = dtf.format(LocalDateTime.now());
        this.name = name;
    }

    /**
     * Get the name of the class
     * @return
     */
    public String getName() {
        return this.name;
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
