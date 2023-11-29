package edu.vassar.cmpu203.app.view;

import java.util.List;

import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.User;

public interface IManageProfile {

    void updateFavoritesDisplay();

    void setUserRestrictions();

    interface Listener{

        void onRestrictionCheck(String restriction);

        void onUserUpdate(List<String> restrictions);

        void onDishToggle(Dish dish);

        void onFavoritesRequested(IManageProfile manageProfile);
    }
}
