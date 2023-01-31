package org.jmeifert.fsuvius;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * User represents an account in the system with an ID, name, and balance.
 */
public class User implements Serializable {

    private long id;
    private String name;
    private float balance;
    private String url;

    /**
     * Constructs a User with the name "" and a balance of 0.
     */
    public User() {
        this.id = new Random().nextLong();
        this.name = "";
        this.balance = 0.0F;
        this.url = "/users/" + id;
    }

    /**
     * Constructs a User with the given name and a balance of 0.
     * @param name The User's name
     */
    public User(String name) {
        this.id = new Random().nextLong();
        this.name = name;
        this.balance = 0.0F;
        this.url = "/users/" + id;
    }

    /**
     * Constructs a User with the given name and balance.
     * @param name The User's name
     * @param balance The User's balance
     */
    public User(String name, float balance) {
        this.id = new Random().nextLong();
        this.name = name;
        this.balance = balance;
        this.url = "/users/" + id;
    }

    /**
     * Returns this User's ID.
     * @return This User's ID
     */
    public Long getID() {
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

    public String getURL() {
        return this.url;
    }
    /**
     * Sets this User's ID.
     * @param id this User's ID
     */
    public void setID(Long id) {
        this.id = id;
        this.url = "/users/" + id;
    }

    /**
     * Sets this User's name.
     * @param name this User's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets this User's balance.
     * @param balance this User's balance
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof User)) {
            return false;
        }
        return Objects.equals(this.id, ((User) other).getID()) &&
                Objects.equals(this.name, ((User) other).getName()) &&
                Objects.equals(this.balance, ((User) other).getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.balance);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", name='" + this.name + "', balance=" +
                this.balance + ", url='" + this.url + "'}";
    }
}
