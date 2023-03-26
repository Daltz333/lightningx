package org.emu.lightningx.services;

import org.emu.lightningx.models.ClassModel;
import org.emu.lightningx.models.ProfessorModel;

public class GlobalStateService {
    private static GlobalStateService instance = null;

    /**
     * This class represents the application global state.
     *
     * It contains the root data such as the current professor selected,
     * settings modifications, etc
     */
    private GlobalStateService(){}

    public static GlobalStateService getInstance() {
        if (instance == null) {
            instance = new GlobalStateService();
        }

        return instance;
    }

    private ProfessorModel selectedProfessor;
    private ClassModel selectedClass;

    /**
     * Set the currently logged in professor
     * @param prof
     */
    public void setSelectedProfessor(ProfessorModel prof) {
        this.selectedProfessor = prof;
    }

    /**
     * Get the currently logged in professor
     * @return
     */
    public ProfessorModel getSelectedProfessor() {
        return selectedProfessor;
    }

    /**
     * Gets the currently selected class
     * @param selectedClass
     */
    public void setSelectedClass(ClassModel selectedClass) {
        this.selectedClass = selectedClass;
    }

    /**
     * Sets the currently selected class
     * @return
     */
    public ClassModel getSelectedClass() {
        return this.selectedClass;
    }

    /**
     * TODO, this function will be called whenever the user changes
     * data that should be updated. We should rewrite to the database during this.
     *
     * To reduce development time, we will just completely overwrite the database
     * instead of only modifying changed fields.
     */
    public void updateDatabase() {

    }
}
