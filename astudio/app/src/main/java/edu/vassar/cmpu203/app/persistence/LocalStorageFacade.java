package edu.vassar.cmpu203.app.persistence;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.User;

public class LocalStorageFacade implements IPersistenceFacade{
    private final File directory;
    private static final String USER_FILENAME = "user";
    private static final String DAYLIBRARY_FILENAME = "daylibrary";

    public LocalStorageFacade(File directory){ this.directory = directory; }

    /**
     * Saves the user to the local storage
     * @param user - the user to save
     */
    @Override
    public void saveUser(User user) {
        Log.d("LocalStorageFacade", "Saving user");
        File outputFile = new File(this.directory, this.USER_FILENAME);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the user from the local storage
     * @return the user
     */
    @Override
    public User loadUser() {
        Log.d("LocalStorageFacade", "Loading user");
        User user = null;

        File inputFile = new File(this.directory, this.USER_FILENAME);
        if (inputFile.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(inputFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                user = (User) ois.readObject();
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * Saves the day library to the local storage
     * @param days - the day library to save
     */
    @Override
    public void saveDayLibrary(DayLibrary days) {
        File outputFile = new File(this.directory, this.DAYLIBRARY_FILENAME);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(days);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the day library from the local storage
     * @return
     */
    @Override
    public DayLibrary loadDayLibrary() {
        DayLibrary days = null;

        File inputFile = new File(this.directory, this.DAYLIBRARY_FILENAME);
        if (inputFile.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(inputFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                days = (DayLibrary) ois.readObject();
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return days;
    }
}
