package edu.clarkson.cosi.fsuvius.user;

import java.io.IOException;
import java.util.*;
import java.lang.Integer;
import java.lang.System;

import edu.clarkson.cosi.fsuvius.FsuviusMap;

/**
 * User represents an account in the system with an ID, name, and balance.
 */
@SuppressWarnings("unused")
public class User {
    /* Regex used to remove unsafe characters from strings */
    private final String SANITIZER_REGEX = "[^a-zA-Z0-9¿-ÿ° !.,?:;'#$%^*()/_+-]";

    private String id;
    private String name;
    private float balance;
    private long lastActive;
    private List<String> fx;

    /**
     * Constructs a User with the name "" and a balance of 0.
     */
    public User() {
        this.id = generateID();
        this.name = "";
        this.balance = 0.0F;
        this.lastActive = System.currentTimeMillis();
        this.fx = new ArrayList<>();
    }

    /**
     * Constructs a User with the given name and a balance of 0.
     * @param name The User's name
     */
    public User(String name) {
        this.id = generateID();
        this.name = name.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
        this.balance = 0.0F;
        this.lastActive = System.currentTimeMillis();
        this.fx = new ArrayList<>();
    }

    /**
     * Constructs a User with the given name and balance.
     * @param name The User's name
     * @param balance The User's balance
     */
    public User(String name, float balance) {
        this.id = generateID();
        this.name = name.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
        this.balance = balance;
        this.lastActive = System.currentTimeMillis();
        this.fx = new ArrayList<>();
    }

    /**
     * Constructs a User with the given name, balance, and lastActive time.
     * @param name The User's name
     * @param balance The User's balance
     * @param lastActive The last time this user was active
     */
    public User(String name, float balance, long lastActive) {
        this.id = generateID();
        this.name = name.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
        this.balance = balance;
        this.lastActive = lastActive;
        this.fx = new ArrayList<>();
    }

    /**
     * Constructs a User with the given name, balance, and lastActive time.
     * @param name The User's name
     * @param balance The User's balance
     * @param lastActive The last time this user was active
     * @param fx The fx list for this user
     */
    public User(String name, float balance, long lastActive, List<String> fx) {
        this.id = generateID();
        this.name = name.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
        this.balance = balance;
        this.lastActive = lastActive;
        fx.forEach(f -> f.replaceAll(FsuviusMap.SANITIZER_REGEX,""));
        this.fx = fx;
    }

    /**
     * Constructs a User from a list of lines.
     * @param lines Lines to read user parameters from
     * @throws IOException If parameters are not valid
     */
    public User(List<String> lines) throws IOException {
        try {
            this.id = (lines.get(0).split("id=", 2)[1]);
            this.name = (lines.get(1).split("name=", 2)[1]);
            this.balance = (Float.parseFloat(lines.get(2).split("balance=", 2)[1]));
        } catch(IndexOutOfBoundsException e) {
            throw new IOException("Failed to read user (Bad syntax). Lines:\n" + Arrays.toString(lines.toArray()));
        } catch(NumberFormatException e) {
            throw new IOException("Failed to read user (Bad balance). Lines:\n" + Arrays.toString(lines.toArray()));
        }

        // new (optional) schema separately
        try {
            this.lastActive = (Long.parseLong(lines.get(3).split("lastActive=", 2)[1]));
        } catch(IndexOutOfBoundsException e) {
            this.lastActive = 0;
        }

        try {
            this.fx = Arrays.asList(lines.get(4).split("profileFx=", 2)[1].split(";"));
        } catch(IndexOutOfBoundsException e) {
            this.fx = new ArrayList<>();
        }
    }

    /**
     * Returns this User's ID.
     * @return This User's ID
     */
    public String getID() {
        return this.id;
    }

    /**
     * Returns this User's name.
     * @return this User's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns this User's balance.
     * @return this User's balance
     */
    public float getBalance() {
        return this.balance;
    }

    /**
     * Returns this User's lastActive time.
     * @return this User's lastActive time
     */
    public long getLastActive() {
        return this.lastActive;
    }

    /**
     * Returns this User's fx list.
     * @return this User's fx list
     */
    public List<String> getFx() {
        return this.fx;
    }

    /**
     * Sets this User's ID.
     * @param id this User's ID
     */
    public void setID(String id) {
        this.id = id.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
    }

    /**
     * Sets this User's name.
     * @param name this User's name
     */
    public void setName(String name) {
        this.name = name.replaceAll(FsuviusMap.SANITIZER_REGEX,"");
    }

    /**
     * Sets this User's balance.
     * @param balance this User's balance
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    /**
     * Sets this User's fx list.
     * @param balance this User's fx list
     */
    public void setFx(List<String> fx) {
        fx.forEach(f -> f.replaceAll(FsuviusMap.SANITIZER_REGEX,""));
        this.fx = fx;
    }

    /**
     * Generates an ID.
     * @return id The generated ID.
     */
    public String generateID() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 32; i++) {
            sb.append(Integer.toHexString(r.nextInt(256)));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof User)) { return false; }
        return Objects.equals(this.id, ((User) other).getID()) &&
                Objects.equals(this.name, ((User) other).getName()) &&
                Objects.equals(this.balance, ((User) other).getBalance()) &&
                Objects.equals(this.lastActive, ((User) other).getLastActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.balance, this.lastActive, this.fx);
    }

    @Override
    public String toString() {
        return String.format("id=%s\nname=%s\nbalance=%s\nlastActive=%s\nprofileFx=%s\n\n", this.id, this.name, this.balance, this.lastActive, String.join(";",this.fx));
    }
}
