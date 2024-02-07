package edu.clarkson.cosi.fsuvius;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

import edu.clarkson.cosi.fsuvius.data.DatabaseController;
import edu.clarkson.cosi.fsuvius.error.ForbiddenException;
import edu.clarkson.cosi.fsuvius.error.NotFoundException;
import edu.clarkson.cosi.fsuvius.user.User;
import edu.clarkson.cosi.fsuvius.util.IPFilter;
import edu.clarkson.cosi.fsuvius.util.Log;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;

import edu.clarkson.cosi.fsuvius.error.BadRequestException;
import edu.clarkson.cosi.fsuvius.error.RateLimitException;
import org.springframework.web.bind.annotation.*;

/**
 * FsuviusController defines and implements the program's API mappings.
 */
@RestController
@SuppressWarnings("unused")
public class FsuviusController {
    private final Log log;
    private final Bucket bucket;
    private final DatabaseController databaseController;

    /**
     * Instantiates a FsuviusController.
     * @param test_mode Whether to use a different data directory to run tests without overwriting existing data
     * @throws IOException if the database controller cannot start
     */
    public FsuviusController(boolean test_mode) throws IOException {
        log = new Log("FsuviusController");
        log.print("Starting up...");
        if(test_mode) {
            databaseController = new DatabaseController("test/users.dat", "test/photos/");
        } else {
            databaseController = new DatabaseController("data/users.dat", "data/photos/");
        }
        Bandwidth limit= Bandwidth.classic(FsuviusMap.MAX_REQUESTS_PER_SECOND,
                Refill.greedy(FsuviusMap.MAX_REQUESTS_PER_SECOND, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
        log.print("=== Startup complete. Welcome to Mount Fsuvius. ===");
    }

    /**
     * Instantiates a FsuviusController.
     * @throws IOException if the database controller cannot start
     */
    public FsuviusController() throws IOException { this(false); }

    /* ===== USERS ===== */

    /**
     * Gets all registered Users.
     * @return All Users
     */
    @GetMapping("/api/users")
    public Collection<User> getUsers() {
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
    public User newUser(@RequestBody String name, HttpServletRequest request) throws IOException {
        if(IPFilter.isFiltered(request)) {
            log.print(1, "Create request from " + IPFilter.getAddress(request) + " filtered.");
            throw new ForbiddenException(); // reject requests from outside the labs
        }
        if(name.replaceAll(FsuviusMap.SANITIZER_REGEX, "").isEmpty()) {
            throw new BadRequestException(); // reject empty names
        }
        if(bucket.tryConsume(1)) {
            log.print("Creating new user with name \"" + name + "\".");
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
    public User editUser(@RequestBody User newUser,
                         @PathVariable String id, HttpServletRequest request) throws IOException {
        if(IPFilter.isFiltered(request)) {
            log.print(1, "Edit request from " + IPFilter.getAddress(request) + " filtered.");
            throw new ForbiddenException(); // reject requests from outside the labs
        }
        if(newUser.getName().isEmpty()) {
            throw new BadRequestException(); // reject requests for empty names
        }
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
    public void deleteUser(@PathVariable String id, HttpServletRequest request) throws IOException {
        if(IPFilter.isFiltered(request)) {
            log.print(1, "Delete request from " + IPFilter.getAddress(request) + " filtered.");
            throw new ForbiddenException(); // reject requests from outside the labs
        }
        if(bucket.tryConsume(1)) {
            log.print("Deleting user at ID \"" + id + "\".");
            databaseController.deleteUser(id);
        } else {
            throw new RateLimitException();
        }
    }

    /* ===== PHOTOS ===== */

    /**
     * Gets a photo by user ID.
     * Will result in an HTTP 404 if the photo cannot be found.
     * @param id ID of the photo to get
     * @return The photo with the specified ID
     */
    @GetMapping(value = "api/photos/{id}")
    public byte[] getPhoto(@PathVariable String id) throws IOException {
        if(bucket.tryConsume(1)) {
            try {
                return databaseController.readPhoto(id);
            } catch(NotFoundException e) {
                log.print(1, "Photo for user " + id + " not found.");
                throw new NotFoundException();
            }
        }
        throw new RateLimitException();
    }

    /**
     * Updates a photo by user ID. Will create it if it does not already exist.
     * @param item New content of the photo
     * @param id ID of the photo to update
     */
    @PostMapping("api/photos/{id}")
    public void putPhoto(@RequestBody String item,
                         @PathVariable String id, HttpServletRequest request) throws IOException {
        if(IPFilter.isFiltered(request)) {
            log.print(1, "Photo upload from " + IPFilter.getAddress(request) + " filtered.");
            throw new ForbiddenException(); // reject requests from outside the labs
        }
        if(bucket.tryConsume(1)) {
            log.print("Setting photo for user " + id + ".");
            if(item.length() > FsuviusMap.MAX_PHOTO_SIZE * 1.33 + 24) { /* account for base64 and headers */
                throw new BadRequestException(); /* refuse photos that are too large */
            }
            databaseController.writePhoto(item, id);
            return;
        }
        throw new RateLimitException();
    }

    /* ===== OTHER USEFUL STUFF ===== */

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
}
