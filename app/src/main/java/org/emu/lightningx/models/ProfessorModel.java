package org.emu.lightningx.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProfessorModel {
    private String name = "";

    private int uuid = -1;

    private final ArrayList<ClassModel> classes;

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

    public ArrayList<ClassModel> getClasses() {
        return classes;
    }

    /**
     * Gets the list of classes associated with this professor as a comma separated list of uuids
     * This is used for database lookups
     * @return
     */
    public String getClassesAsCommaUuidList() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < classes.size(); i++) {
            if (i != classes.size() - 1) {
                builder.append(classes.get(i)).append(",");
            } else {
                builder.append(classes.get(i));
            }
        }

        return builder.toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ProfessorModel) {
            ProfessorModel realObj = (ProfessorModel) obj;

            return realObj.getName().equals(name)
                    && realObj.getUuid() == uuid;
        } else {
            return false;
        }
    }
}
