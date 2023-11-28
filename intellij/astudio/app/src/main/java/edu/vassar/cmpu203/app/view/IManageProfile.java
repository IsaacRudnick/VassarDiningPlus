package edu.vassar.cmpu203.app.view;

import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.User;

public interface IManageProfile {

    void setUserRestrictions();

    interface Listener{
        void onRemoveFavorite(Dish favorite, User user);
    }
}
