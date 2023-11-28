package edu.vassar.cmpu203.app.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.vassar.cmpu203.app.model.User;

public class LocalStorageFacade implements IPersistenceFacade{
    private final File directory;
    private static final String FILENAME = "user";

    public LocalStorageFacade(File directory){ this.directory = directory; }

    /**
     * Saves the user to the local storage
     * @param user - the user to save
     */
    @Override
    public void saveUser(User user) {
        File outputFile = new File(this.directory, FILENAME);
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
        User user = null;

        File inputFile = new File(this.directory, FILENAME);
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
}
