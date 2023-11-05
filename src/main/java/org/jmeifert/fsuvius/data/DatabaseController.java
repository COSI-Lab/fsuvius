package org.jmeifert.fsuvius.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.jmeifert.fsuvius.FsuviusMap;
import org.jmeifert.fsuvius.error.NotFoundException;
import org.jmeifert.fsuvius.util.Log;
import org.jmeifert.fsuvius.user.User;


/**
 * UserRegistry handles the storage and retrieval of users.
 */
public class DatabaseController {
    private final Log log;
    private final String USERS_FILE;
    private final String PHOTOS_DIR;
    private Vector<User> users;

    /**
     * Instantiates a UserRegistry.
     */
    public DatabaseController(String users_file, String photos_dir) throws IOException {
        this.USERS_FILE = users_file;
        this.PHOTOS_DIR = photos_dir;
        this.log = new Log("DatabaseController");
        try {
            Files.createDirectories(Paths.get(PHOTOS_DIR));
        } catch(IOException e) {
            log.print(2, "Failed to ensure presence of data directories.");
        }
        log.print("Loading users...");
        this.users = loadUsers();
        log.print("Finished loading users.");
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
    public synchronized User getUser(String id) throws NotFoundException {
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
    public synchronized User createUser(String name) throws IOException {
        User userToAdd = new User(name);
        users.add(userToAdd);
        writePhoto(FsuviusMap.DEFAULT_PHOTO, userToAdd.getID());
        saveUsers(users);
        return userToAdd;
    }

    /**
     * Updates a user.
     * @param id The user ID to update
     * @param user The user to replace with
     * @return The updated user
     */
    public synchronized User editUser(String id, User user) throws IOException, NotFoundException {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.set(i, user);
                saveUsers(users);
                return user;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Deletes a user.
     * @param id User ID to delete
     */
    public synchronized void deleteUser(String id) throws IOException, NotFoundException {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.remove(i);
                saveUsers(users);
                deletePhoto(id);
                return;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Deletes everything. (used by tests)
     */
    public synchronized void reset() throws IOException {
        log.print(1, "Resetting database.");
        users = new Vector<>();
        saveUsers(users);
        cleanupPhotos();
        log.print("Database reset complete.");
    }

    /**
     * Loads users from a file.
     * @return Users loaded from the file
     */
    private synchronized Vector<User> loadUsers() throws IOException {
        try {
            Scanner file = new Scanner(new FileInputStream(USERS_FILE));
            Vector<User> loaded_users = new Vector<>();
            Vector<String> line_buf = new Vector<>();
            String current_line;
            while(file.hasNextLine()) {
                current_line = file.nextLine();
                if(current_line.isBlank()) {
                    loaded_users.add(new User(line_buf));
                    line_buf.clear();
                } else {
                    line_buf.add(current_line);
                }
            }
            file.close();
            return loaded_users;
        } catch(FileNotFoundException e) {
            log.print(1, "File \"" + USERS_FILE + "\" not found.");
            return new Vector<>();
        }
    }

    /**
     * Saves users to a file.
     * @param users Users to save to the file
     */
    private synchronized void saveUsers(Vector<User> users) throws IOException {
        log.print("Saving \"" + USERS_FILE + "\".");
        PrintWriter file = new PrintWriter(new FileOutputStream(USERS_FILE));
        for(User i : users) { file.print(i); }
        file.flush();
        file.close();
    }

    /**
     * Reads a single photo with a given ID
     * @param id The ID of the photo to read
     * @return The photo as bytes
     */
    public synchronized byte[] readPhoto(String id) throws IOException {
        return loadBytesFromFile(PHOTOS_DIR + id);
    }

    /**
     * Writes a single photo with a given ID
     * @param item The photo as bytes
     * @param id The ID of the photo to write
     */
    public synchronized void writePhoto(String item, String id) throws IOException {
        /* if user bypasses frontend upload size limit just don't do anything */
        if(item.length() < FsuviusMap.MAX_PHOTO_SIZE * 1.33 + 24) {
            byte[] image = Base64.getDecoder().decode(item.split(",")[1]);
            saveBytesToFile(image, PHOTOS_DIR + id);
        }
    }

    /**
     * Deletes a single photo with a given ID
     * @param id The ID of the photo to delete
     */
    public synchronized void deletePhoto(String id) {
        String filename = PHOTOS_DIR + id;
        File f = new File(filename);
        if(f.delete()) { return; }
        log.print(2, "Could not delete file \"" + filename + "\".");
    }

    /**
     * Loads bytes from a file.
     * @param filename Filename to load bytes from
     * @return The loaded bytes
     */

    private synchronized byte[] loadBytesFromFile(String filename) throws IOException, NotFoundException {
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            byte[] fb = new byte[(int) f.length()];
            int ignored = fis.read(fb);
            fis.close();
            return fb;
        } catch(FileNotFoundException e) {
            log.print(1, "File \"" + filename + "\" not found.");
            throw new NotFoundException();
        }
    }

    /**
     * Saves bytes to a file.
     * @param item Bytes to save
     * @param filename Filename to save to
     */
    private synchronized void saveBytesToFile(byte[] item, String filename) throws IOException {
        log.print("Saving \"" + filename + "\".");
        FileOutputStream f = new FileOutputStream(filename);
        f.write(item);
        f.close();
    }

    /**
     * Cleans up unused photos from disk.
     */
    private synchronized void cleanupPhotos() {
        log.print(0, "Scanning photo database...");
        Vector<String> user_ids = new Vector<>();
        for(User i : users) { user_ids.add(i.getID()); }
        File[] files = (new File(PHOTOS_DIR)).listFiles();
        if(files == null || files.length == 0) { return; }
        int n_photos = 0;
        for(File i : files) {
            if(i.isFile()) { n_photos++; }
        }
        if(n_photos <= user_ids.size()) { return; }
        log.print(1, "Found unused photos.");
        for(File i : files) { /* TODO fix O(n^2) algorithm */
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
                    log.print(0, "Deleting unused photo \"" + i.getName() + "\".");
                    if(!i.delete()) {
                        log.print(1, "Failed to delete unused photo \"" + i.getName() + "\".");
                    }
                }
            }
        }
    }
}
