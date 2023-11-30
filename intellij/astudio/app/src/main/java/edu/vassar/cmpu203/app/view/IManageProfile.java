package edu.vassar.cmpu203.app.view;

import java.util.List;

import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Restriction;

public interface IManageProfile {

    void updateFavoritesDisplay();

    void setUserRestrictions(List<Restriction> restrictions);

    interface Listener{

        /**
         * Called when the user updates the restrictions.
         * @param restrictions The list of restrictions that the user has checked.
         */
        void onUpdateRestrictions(List<Restriction> restrictions);

        /**
         * Called when the user toggles (favorites/unfavorites) a dish.
         * @param dish The dish that was toggled.
         */
        void toggleDishFavorited(Dish dish);

        boolean isDishFavorited(Dish dish);

        /**
         * Called when the user requests to view the favorites.
         * @param manageProfile The view that called this method.
         */
        void onFavoritesRequested(IManageProfile manageProfile);

        void checkSavedRestrictions(IManageProfile manageProfile);
    }
}
