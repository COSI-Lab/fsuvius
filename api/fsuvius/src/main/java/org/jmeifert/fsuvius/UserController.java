package org.jmeifert.fsuvius;

import java.time.Duration;
import java.util.List;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jmeifert.fsuvius.security.RateLimitException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final int MAX_REQUESTS_PER_MINUTE = 60;

    private final Bucket bucket;

    private UserRegistry userRegistry;

    UserController() {
        userRegistry = new UserRegistry();
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE,
                Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    };

    @GetMapping("/users")
    List<User> all() {
        if(bucket.tryConsume(1)) {
            return userRegistry.getAll();
        }
        throw new RateLimitException();

    }

    @PostMapping("/users")
    User newEntry(@RequestBody String name) {
        if(bucket.tryConsume(1)) {
            return userRegistry.createUser(name);
        }
        throw new RateLimitException();

    }

    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.getUser(id);
        }
        throw new RateLimitException();
    }

    @PutMapping("/users/{id}")
    User replaceEntry(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    @DeleteMapping("/users/{id}")
    void deleteEntry(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            userRegistry.deleteUser(id);
        }
        throw new RateLimitException();
    }
}
