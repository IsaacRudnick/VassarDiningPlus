package edu.vassar.cmpu203.app.persistence;

import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.User;

public interface IPersistenceFacade {

    /**
     * Saves the user to storage
     * @param user
     */
    void saveUser(User user);

    /**
     * Loads the user from storage
     * @return the user
     */
    User loadUser();

    /**
     * Saves the day library to storage
     * @param days
     */
    void saveDayLibrary(DayLibrary days);

    /**
     * Loads the day library from storage
     * @return the day library
     */
    DayLibrary loadDayLibrary();

}
