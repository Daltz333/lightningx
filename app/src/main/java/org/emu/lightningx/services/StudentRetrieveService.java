package org.emu.lightningx.services;

import org.emu.lightningx.R;
import org.emu.lightningx.models.StudentModel;

import java.util.ArrayList;

/**
 * Singleton class that represents the current list of students loaded.
 * We should probably replace this with a database interface
 */
public class StudentRetrieveService {
    public static StudentRetrieveService instance = new StudentRetrieveService();

    private ArrayList<StudentModel> students = new ArrayList<>();

    private StudentRetrieveService() {
        StudentModel student1 = new StudentModel("Jacob", "E0000001", R.drawable.theboy);
        StudentModel student2 = new StudentModel("Samantha", "E0000002", R.drawable.theboy);
        StudentModel student3 = new StudentModel("Henry", "E0000003", R.drawable.theboy);

        students.add(student1);
        students.add(student2);
        students.add(student3);
    }

    public ArrayList<StudentModel> getStudents() {
        return students;
    }
}
