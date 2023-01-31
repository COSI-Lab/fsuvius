package org.jmeifert.fsuvius;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class UserRegistry {
    private Vector<User> users;
    public UserRegistry() {
        users = loadUsersFromFile();
        if(users.size() < 1) { // if no users could be loaded
            users.add(new User("Test User", 0.0F));
            saveUsersToFile(users);
        }
    }

    private Vector<User> loadUsersFromFile() {
        try {
            FileInputStream f = new FileInputStream("users.dat");
            ObjectInputStream o = new ObjectInputStream(f);
            Vector<User> output = (Vector<User>) o.readObject();
            o.close();
            f.close();
            return output;
        } catch(FileNotFoundException e) {
            System.err.println("UserRegistry: FileNotFoundException upon reading users.dat");
            return new Vector<>();
        } catch(IOException e) {
            System.err.println("UserRegistry: IOException upon reading users.dat");
            return new Vector<>();
        } catch(ClassNotFoundException e) {
            System.err.println("UserRegistry: ClassNotFoundException upon reading users.dat");
            return new Vector<>();
        }

    }

    private void saveUsersToFile(Vector<User> users) {
        try {
            FileOutputStream f = new FileOutputStream("users.dat");
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(users);
            o.close();
            f.close();
        } catch(FileNotFoundException e) {
            System.err.println("UserRegistry: FileNotFoundException upon writing users.dat");
        } catch(IOException e) {
            System.err.println("UserRegistry: IOException upon writing users.dat");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        users = loadUsersFromFile();
        return Collections.list(users.elements());
    }

    public User getUser(String id) {
        users = loadUsersFromFile();
        for(User i : users) {
            if(i.getID().equals(id)) {
                return i;
            }
        }
        return new User("INVALID",-1.0F);
    }

    public User createUser(String name) {
        users = loadUsersFromFile();
        User userToAdd = new User(name);
        users.add(userToAdd);
        saveUsersToFile(users);
        return userToAdd;
    }

    public User editUser(String id, User user) {
        users = loadUsersFromFile();
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.set(i, user);
                saveUsersToFile(users);
                return user;
            }
        }
        System.out.println("Not found");
        return new User("INVALID", -1.0F);
    }

    public void deleteUser(String id) {
        users = loadUsersFromFile();
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getID().equals(id)) {
                users.remove(i);
                saveUsersToFile(users);
            }
        }
    }
}
