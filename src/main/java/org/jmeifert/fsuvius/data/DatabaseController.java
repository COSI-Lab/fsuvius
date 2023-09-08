package org.jmeifert.fsuvius.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.jmeifert.fsuvius.FsuviusMap;
import org.jmeifert.fsuvius.error.NotFoundException;
import org.jmeifert.fsuvius.util.Log;
import org.jmeifert.fsuvius.user.User;


/**
 * UserRegistry handles the storage and retrieval of users.
 */
public class DatabaseController {
    private final Log log;
    private final String USERS_STORE_FILE = "data/users.dat";
    private Vector<User> users;

    /**
     * Instantiates a UserRegistry.
     */
    public DatabaseController() {
        log = new Log("UserRegistry");
        try {
            Files.createDirectories(Paths.get("data/photos/"));
        } catch(IOException e) {
            log.print(2, "Failed to ensure presence of data directories.");
        }
        log.print("Loading users...");
        this.users = loadUsersFromFile();
        log.print("Done.");
    }

    /**
     * Gets all users.
     * @return All users as a list
     */
    public synchronized List<User> getUsers() {
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
        writePhoto(FsuviusMap.DEFAULT_PHOTO, userToAdd.getID());
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
                deletePhoto(id);
                return;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Deletes everything. (used by tests)
     */
    public synchronized void reset() {
        log.print(1, "Resetting database.");
        users = new Vector<>();
        saveUsersToFile(users);
        cleanupPhotos();
        log.print("Database reset complete.");
    }

    /**
     * Loads users from a file.
     * @return Users loaded from the file
     */
    private synchronized Vector<User> loadUsersFromFile() {
        try {
            FileInputStream f = new FileInputStream(USERS_STORE_FILE);
            ObjectInputStream o = new ObjectInputStream(f);
            Vector<User> output = (Vector<User>) o.readObject();
            o.close();
            f.close();
            return output;
        } catch(FileNotFoundException e) {
            log.print(1, "UserRegistry: FileNotFoundException upon reading " + USERS_STORE_FILE);
            return new Vector<>();
        } catch(IOException e) {
            log.print(2, "UserRegistry: IOException upon reading " + USERS_STORE_FILE);
            throw new RuntimeException("Failed to read users!");
        } catch(ClassNotFoundException e) {
            log.print(2, "UserRegistry: ClassNotFoundException upon reading " + USERS_STORE_FILE);
            throw new RuntimeException("Failed to read users!");
        }

    }

    /**
     * Saves users to a file.
     * @param users Users to save to the file
     */
    private synchronized void saveUsersToFile(Vector<User> users) {
        try {
            log.print("Syncing writes to " + USERS_STORE_FILE + "...");
            FileOutputStream f = new FileOutputStream(USERS_STORE_FILE);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(users);
            o.close();
            f.close();
        } catch(FileNotFoundException e) {
            log.print(2, "UserRegistry: FileNotFoundException upon writing " + USERS_STORE_FILE);
        } catch(IOException e) {
            log.print(2, "UserRegistry: IOException upon writing " + USERS_STORE_FILE);
        }
    }

    /**
     * Reads a single photo with a given ID
     * @param id The ID of the photo to read
     * @return The photo as bytes
     */
    public synchronized byte[] readPhoto(String id) {
        return loadBytesFromFile("data/photos/"+id);
    }

    /**
     * Writes a single photo with a given ID
     * @param item The photo as bytes
     * @param id The ID of the photo to write
     */
    public synchronized void writePhoto(String item, String id) {
        try {
            /* if user bypasses frontend upload size limit just don't do anything */
            if(item.length() < FsuviusMap.MAX_PHOTO_SIZE * 1.33 + 24) {
                byte[] image = Base64.getDecoder().decode(item.split(",")[1]);
                saveBytesToFile(image, "data/photos/"+id);
            }
        } catch(RuntimeException e) {
            log.print(2, "Failed to write image.");
            throw new RuntimeException("Failed to write image.");
        }
    }

    /**
     * Deletes a single photo with a given ID
     * @param id The ID of the photo to delete
     */
    public synchronized void deletePhoto(String id) {
        String filename = "data/photos/" + id;
        File f = new File(filename);
        if(f.delete()) { return; }
        log.print(2, "I/O error deleting " + filename + ".");
    }

    /**
     * Loads bytes from a file.
     * @param STORE_FILE Filename to load bytes from
     * @return The loaded bytes
     */
    private synchronized byte[] loadBytesFromFile(String STORE_FILE) {
        try {
            File f = new File(STORE_FILE);
            FileInputStream fis = new FileInputStream(f);
            byte[] fb = new byte[(int) f.length()];
            fis.read(fb);
            fis.close();
            return fb;
        } catch(FileNotFoundException e) {
            log.print(1, "Couldn't find " + STORE_FILE + ". Will attempt to create it on write.");
            throw new NotFoundException();
        } catch(IOException e) {
            log.print(2, "Error reading " + STORE_FILE + ".");
            throw new RuntimeException("Failed to read " + STORE_FILE + ".");
        }
    }

    /**
     * Saves bytes to a file.
     * @param item Bytes to save
     * @param filename Filename to save to
     */
    private synchronized void saveBytesToFile(byte[] item, String filename) {
        try {
            log.print("Writing " + filename + ".");
            FileOutputStream f = new FileOutputStream(filename);
            f.write(item);
            f.close();
        } catch(FileNotFoundException e) {
            log.print(2, "Couldn't find " + filename + " on write.");
            throw new RuntimeException("Couldn't find " + filename + "on write.");
        } catch(IOException e) {
            log.print(2, "I/O error writing " + filename + ".");
            throw new RuntimeException("I/O error writing " + filename + ".");
        }
    }

    /**
     * Cleans up unused photos from disk.
     */
    private synchronized void cleanupPhotos() {
        log.print(0, "Cleaning up photo database...");
        Vector<String> user_ids = new Vector<>();
        for(User i : users) {
            user_ids.add(i.getID());
        }
        File[] files = (new File("data/photos/")).listFiles();
        if(files == null || files.length == 0) { return; }
        int n_photos = 0;
        for(File i : files) { if(i.isFile()) { n_photos++; } }
        if(n_photos <= user_ids.size()) { return; }
        log.print(1, "Found unused photos.");
        for(File i : files) {
            if(i.isFile()) {
                boolean hasParentPhoto = false;
                for(String j : user_ids) {
                    if(i.getName().equals(j)) {
                        hasParentPhoto = true;
                        user_ids.remove(j);
                        break;
                    }
                }
                if(!hasParentPhoto) {
                    log.print(0, "Deleting unused photo " + i.getName());
                    if(!i.delete()) {
                        log.print(1, "Failed to delete unused photo " + i.getName());
                    }
                }
            }
        }
    }
}
