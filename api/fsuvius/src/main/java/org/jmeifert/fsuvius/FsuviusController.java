package org.jmeifert.fsuvius;

import java.time.Duration;
import java.util.List;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jmeifert.fsuvius.security.RateLimitException;
import org.jmeifert.fsuvius.user.User;
import org.jmeifert.fsuvius.user.UserRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
public class FsuviusController {

    private final int MAX_REQUESTS_PER_MINUTE = 60;

    private final Bucket bucket;

    private UserRegistry userRegistry;

    FsuviusController() {
        userRegistry = new UserRegistry();
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE,
                Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    };

    @GetMapping("/api/v1/users")
    List<User> all() {
        if(bucket.tryConsume(1)) {
            return userRegistry.getAll();
        }
        throw new RateLimitException();

    }

    @PostMapping("/api/v1/users")
    User newEntry(@RequestBody String name) {
        if(bucket.tryConsume(1)) {
            return userRegistry.createUser(name);
        }
        throw new RateLimitException();

    }

    @GetMapping("/api/v1/users/{id}")
    User one(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.getUser(id);
        }
        throw new RateLimitException();
    }

    @PutMapping("/api/v1/users/{id}")
    User replaceEntry(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    @DeleteMapping("/api/v1/users/{id}")
    void deleteEntry(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            userRegistry.deleteUser(id);
        }
        throw new RateLimitException();
    }
}
