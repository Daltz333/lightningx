package org.emu.lightningx.services;

import org.emu.lightningx.models.ProfessorModel;

public class GlobalStateService {
    private GlobalStateService instance = null;

    /**
     * This class represents the application global state.
     *
     * It contains the root data such as the current professor selected,
     * settings modifications, etc
     */
    private GlobalStateService(){}

    public GlobalStateService getInstance() {
        if (instance == null) {
            instance = new GlobalStateService();
        }

        return instance;
    }

    private ProfessorModel selectedProfessor;

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
        return this.selectedProfessor;
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
