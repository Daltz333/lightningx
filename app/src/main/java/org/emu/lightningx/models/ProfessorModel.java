package org.emu.lightningx.models;

import java.util.ArrayList;

public class ProfessorModel {
    private String name = "";

    private int uuid = -1;

    private ArrayList<ClassModel> classes;

    /**
     * Get the professor UUID
     * @return
     */
    public int getUuid() {
        return uuid;
    }

    /**
     * Set the professor UUID
     *
     * This function should not be called outside of professor/account creation
     * @param uuid
     */
    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the professor name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the professor name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public ProfessorModel() {
        classes = new ArrayList<ClassModel>();
    }

    public void addClass(ClassModel newClass) {
        classes.add(newClass);
    }

    public void removeClass(ClassModel classToRemove) {
        classes.remove(classToRemove);
    }

}
