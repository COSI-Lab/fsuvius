package org.jmeifert.fsuvius;

import java.time.Duration;
import java.util.List;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jmeifert.fsuvius.error.RateLimitException;
import org.jmeifert.fsuvius.user.User;
import org.jmeifert.fsuvius.user.UserRegistry;
import org.jmeifert.fsuvius.util.Log;
import org.springframework.web.bind.annotation.*;

/**
 * FsuviusController defines and implements the program's API mappings.
 */
@RestController
public class FsuviusController {
    private final Log log;
    private final int MAX_REQUESTS_PER_5S = 100;
    private final Bucket bucket;
    private UserRegistry userRegistry;

    /**
     * Instantiates a FsuviusController.
     */
    public FsuviusController() {
        log = new Log("FsuviusController");
        log.print("Starting up...");
        userRegistry = new UserRegistry();
        Bandwidth limit= Bandwidth.classic(MAX_REQUESTS_PER_5S,
                Refill.greedy(MAX_REQUESTS_PER_5S, Duration.ofSeconds(5)));
        this.bucket = Bucket.builder().addLimit(limit).build();
        log.print("Initialization complete. Welcome to Mount Fsuvius.");
    }

    /**
     * Gets the total amount of FSU in the bank.
     * @return The total amount of FSU in the bank
     */
    @GetMapping("/api/bank_balance")
    public float getBankBalance() {
        if(bucket.tryConsume(1)) {
            float bal = 0.0F;
            for(User i : userRegistry.getAll()) {
                bal += i.getBalance();
            }
            return bal;
        }
        throw new RateLimitException();
    }

    /**
     * Gets all registered Users.
     * @return All Users
     */
    @GetMapping("/api/users")
    public List<User> getUsers() {
        if(bucket.tryConsume(1)) {
            return userRegistry.getAll();
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
            return userRegistry.createUser(name);
        }
        throw new RateLimitException();

    }

    /**
     * Gets a single user.
     * @param id The ID of the user to get
     * @return The User with the specified ID
     */
    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.getUser(id);
        }
        throw new RateLimitException();
    }

    /**
     * Edits a User with the given parameters.
     * @param newUser New User parameters
     * @param id ID of the user to edit
     * @return
     */
    @PutMapping("/api/user/{id}")
    public User editUser(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            log.print("Handling request to edit user at ID \"" + id + "\".");
            return userRegistry.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    /**
     * Deletes a User with the given ID.
     * @param id The ID of the user to delete
     */
    @DeleteMapping("/api/user/{id}")
    public void deleteUser(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            log.print("Handling request to delete user at ID \"" + id + "\".");
            userRegistry.deleteUser(id);
        } else {
            throw new RateLimitException();
        }
    }
}
