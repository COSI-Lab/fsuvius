package org.jmeifert.fsuvius.user;

import org.jmeifert.fsuvius.error.NotFoundException;
import org.jmeifert.fsuvius.util.Log;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * UserRegistry handles the storage and retrieval of users.
 */
public class UserRegistry {
    private final Log log;
    private final String STORE_FILE = "users.dat";
    private Vector<User> users;

    /**
     * Instantiates a UserRegistry.
     */
    public UserRegistry() {
        log = new Log("UserRegistry");
        log.print("Loading users into cache...");
        users = loadUsersFromFile();
        if(users.size() < 1) { // if no users could be loaded
            users.add(new User("New User", 0.0F));
            saveUsersToFile(users);
        }
        log.print("Finished loading users into cache.");
    }

    /**
     * Loads users from a file.
     * @return Users loaded from the file
     */
    private synchronized Vector<User> loadUsersFromFile() {
        try {
            FileInputStream f = new FileInputStream(STORE_FILE);
            ObjectInputStream o = new ObjectInputStream(f);
            Vector<User> output = (Vector<User>) o.readObject();
            o.close();
            f.close();
            return output;
        } catch(FileNotFoundException e) {
            log.print(1, "UserRegistry: FileNotFoundException upon reading " + STORE_FILE);
            return new Vector<>();
        } catch(IOException e) {
            log.print(2, "UserRegistry: IOException upon reading " + STORE_FILE);
            throw new RuntimeException("Failed to read users!");
        } catch(ClassNotFoundException e) {
            log.print(2, "UserRegistry: ClassNotFoundException upon reading " + STORE_FILE);
            throw new RuntimeException("Failed to read users!");
        }

    }

    /**
     * Saves users to a file.
     * @param users Users to save to the file
     */
    private synchronized void saveUsersToFile(Vector<User> users) {
        try {
            log.print("Syncing writes to " + STORE_FILE + "...");
            FileOutputStream f = new FileOutputStream(STORE_FILE);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(users);
            o.close();
            f.close();
        } catch(FileNotFoundException e) {
            log.print(1, "UserRegistry: FileNotFoundException upon writing " + STORE_FILE);
        } catch(IOException e) {
            log.print(1, "UserRegistry: IOException upon writing " + STORE_FILE);
        }
    }

    /**
     * Gets all registered users.
     * @return All users as a list
     */
    public synchronized List<User> getAll() {
        users = loadUsersFromFile();
        return Collections.list(users.elements());
    }

    /**
     * Gets a single user by ID
     * @param id User's ID
     * @return The user with the specified ID
     */
    public synchronized User getUser(String id) {
        for(User i : users) {
            if(i.getID().equals(id)) {
                return i;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Creates a user with the specified name
     * @param name Name of the user to be created
     * @return The user with the specified name
     */
    public synchronized User createUser(String name) {
        User userToAdd = new User(name);
        users.add(userToAdd);
        saveUsersToFile(users);
        return userToAdd;
    }

    /**
     * Updates a user.
     * @param id The user ID to update
     * @param user The user to replace with
     * @return The updated user
     */
    public synchronized User editUser(String id, User user) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.set(i, user);
                saveUsersToFile(users);
                return user;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Deletes a user.
     * @param id User ID to delete
     */
    public synchronized void deleteUser(String id) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.remove(i);
                saveUsersToFile(users);
                return;
            }
        }
        throw new NotFoundException();
    }
}
