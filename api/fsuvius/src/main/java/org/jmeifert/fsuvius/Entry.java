package org.jmeifert.fsuvius;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Entry represents an account in the system with an ID, name, and balance.
 */
@Entity
public class Entry {
    private @Id @GeneratedValue Long id;
    private String name;
    private float balance;

    /**
     * Constructs an Entry with the name "" and a balance of 0.
     */
    public Entry() {
        name = "";
        balance = 0.0F;
    }

    /**
     * Constructs an Entry with the given name and a balance of 0.
     * @param name The entry's name
     */
    public Entry(String name) {
        this.name = name;
        balance = 0.0F;
    }

    /**
     * Constructs an Entry with the given name and balance.
     * @param name The entry's name
     * @param balance The entry's balance
     */
    public Entry(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Returns this Entry's ID.
     * @return This Entry's ID
     */
    public Long getID() {
        return this.id;
    }

    /**
     * Returns this Entry's name.
     * @return this Entry's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns this Entry's balance.
     * @return this Entry's balance
     */
    public float getBalance() {
        return this.balance;
    }

    /**
     * Sets this Entry's ID.
     * @param id this Entry's ID
     */
    public void setID(Long id) {
        this.id = id;
    }

    /**
     * Sets this Entry's name.
     * @param name this Entry's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets this Entry's balance.
     * @param balance this Entry's balance
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Entry)) {
            return false;
        }
        return Objects.equals(this.id, ((Entry) other).getID()) &&
                Objects.equals(this.name, ((Entry) other).getName()) &&
                Objects.equals(this.balance, ((Entry) other).getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.balance);
    }

    @Override
    public String toString() {
        return "Entry{" + "id=" + this.id + ", name='" + this.name + "', balance=" + this.balance + "}";
    }
}
