package org.jmeifert.fsuvius;

import java.time.Duration;
import java.util.List;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jmeifert.fsuvius.data.DatabaseController;
import org.jmeifert.fsuvius.error.NotFoundException;
import org.jmeifert.fsuvius.error.RateLimitException;
import org.jmeifert.fsuvius.user.User;
import org.jmeifert.fsuvius.util.Log;
import org.springframework.web.bind.annotation.*;

/**
 * FsuviusController defines and implements the program's API mappings.
 */
@RestController
public class FsuviusController {
    private final Log log;

    private final Bucket bucket;
    private DatabaseController databaseController;

    /**
     * Instantiates a FsuviusController.
     */
    public FsuviusController() {
        log = new Log("FsuviusController");
        log.print("Starting up...");
        databaseController = new DatabaseController();
        Bandwidth limit= Bandwidth.classic(FsuviusMap.MAX_REQUESTS_PER_SECOND,
                Refill.greedy(FsuviusMap.MAX_REQUESTS_PER_SECOND, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
        log.print("===== Init complete. Welcome to Mount Fsuvius. =====");
    }

    /* ===== BANK TOTALS ===== */

    /**
     * Gets the total amount of FSU in the bank.
     * @return The total amount of FSU in the bank
     */
    @GetMapping("/api/bank_balance")
    public float getBankBalance() {
        if(bucket.tryConsume(1)) {
            float bal = 0.0F;
            for(User i : databaseController.getUsers()) {
                bal += i.getBalance();
            }
            return bal;
        }
        throw new RateLimitException();
    }

    /* ===== USERS ===== */

    /**
     * Gets all registered Users.
     * @return All Users
     */
    @GetMapping("/api/users")
    public List<User> getUsers() {
        if(bucket.tryConsume(1)) {
            return databaseController.getUsers();
        }
        throw new RateLimitException();
    }

    /**
     * Creates a new User with the given name.
     * @param name the new User's name
     * @return the new User
     */
    @PostMapping("/api/users")
    public User newUser(@RequestBody String name) {
        if(bucket.tryConsume(1)) {
            log.print("Handling request to create new user with name \"" + name + "\".");
            return databaseController.createUser(name);
        }
        throw new RateLimitException();
    }

    /**
     * Gets a single user.
     * @param id The ID of the user to get
     * @return The User with the specified ID
     */
    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return databaseController.getUser(id);
        }
        throw new RateLimitException();
    }

    /**
     * Edits a User with the given parameters.
     * @param newUser New User parameters
     * @param id ID of the User to edit
     * @return The edited User
     */
    @PutMapping("/api/users/{id}")
    public User editUser(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            log.print("Handling request to edit user at ID \"" + id + "\".");
            return databaseController.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    /**
     * Deletes a User with the given ID.
     * @param id The ID of the user to delete
     */
    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            log.print("Handling request to delete user at ID \"" + id + "\".");
            databaseController.deleteUser(id);
        } else {
            throw new RateLimitException();
        }
    }

    /* ===== PHOTOS ===== */

    /**
     * Gets a photo by ID.
     * Will result in an HTTP 404 if the photo cannot be found.
     * @param id ID of the photo to get
     * @return The photo with the specified ID
     */
    @GetMapping(value = "api/photos/{id}")
    public byte[] getPhoto(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            try {
                return databaseController.readPhoto(id);
            } catch(NotFoundException e) {
                log.print(1, "Couldn't find photo " + id + " in database.");
                throw new NotFoundException();
            }
        }
        throw new RateLimitException();
    }

    /**
     * Updates a photo by ID. Will create it if it does not already exist.
     * @param item New content of the photo
     * @param id ID of the photo to update
     */
    @PostMapping("api/photos/{id}")
    public void putPhoto(@RequestBody String item, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            databaseController.writePhoto(item, id);
            return;
        }
        throw new RateLimitException();
    }
}
