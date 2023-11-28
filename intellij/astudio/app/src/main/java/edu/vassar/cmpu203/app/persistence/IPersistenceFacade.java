package edu.vassar.cmpu203.app.persistence;

import edu.vassar.cmpu203.app.model.User;

public interface IPersistenceFacade {

    void saveUser(User user);

    User loadUser();

//    void saveDay(String date, String day);
//
//    String loadDay(String date);
}
