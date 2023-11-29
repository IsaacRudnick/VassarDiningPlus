package edu.vassar.cmpu203.app.view;

import java.util.List;

import edu.vassar.cmpu203.app.model.Dish;

public interface IManageProfile {

    void updateFavoritesDisplay();

    void setUserRestrictions();

    interface Listener{

        void onUserUpdate(List<String> restrictions);

        void onDishToggle(Dish dish);

        void onFavoritesRequested(IManageProfile manageProfile);
    }
}
