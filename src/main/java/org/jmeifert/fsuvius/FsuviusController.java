package org.jmeifert.fsuvius;

import java.time.Duration;
import java.util.List;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jmeifert.fsuvius.error.RateLimitException;
import org.jmeifert.fsuvius.user.User;
import org.jmeifert.fsuvius.user.UserRegistry;
import org.springframework.web.bind.annotation.*;

/**
 * FsuviusController defines and implements the program's API mappings.
 */
@RestController
public class FsuviusController {
    private final int MAX_REQUESTS_PER_MINUTE = 3000;
    private final Bucket bucket;
    private UserRegistry userRegistry;

    /**
     * Instantiates a FsuviusController.
     */
    FsuviusController() {
        userRegistry = new UserRegistry();
        Bandwidth limit= Bandwidth.classic(MAX_REQUESTS_PER_MINUTE,
                Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    /**
     * Gets the total amount of FSU in the bank.
     * @return The total amount of FSU in the bank
     */
    @GetMapping("/api/bank_balance")
    float getBankBalance() {
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
    List<User> getUsers() {
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
    User newUser(@RequestBody String name) {
        if(bucket.tryConsume(1)) {
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
    User getUser(@PathVariable String id) {
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
    User editUser(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    /**
     * Deletes a User with the given ID.
     * @param id The ID of the user to delete
     */
    @DeleteMapping("/api/user/{id}")
    void deleteUser(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            userRegistry.deleteUser(id);
        } else {
            throw new RateLimitException();
        }
    }
}
