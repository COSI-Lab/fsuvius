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

@RestController
public class FsuviusController {
    private final int MAX_REQUESTS_BURST = 100;
    private final Bucket bucket;
    private UserRegistry userRegistry;

    FsuviusController() {
        userRegistry = new UserRegistry();
        Bandwidth limit= Bandwidth.classic(MAX_REQUESTS_BURST,
                Refill.greedy(MAX_REQUESTS_BURST, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @GetMapping("/api/v1/bank_balance")
    float getBankBalance() {
        float bal = 0.0F;
        for(User i : userRegistry.getAll()) {
            bal += i.getBalance();
        }
        return bal;
    }

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

    @GetMapping("/api/v1/user/{id}")
    User one(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.getUser(id);
        }
        throw new RateLimitException();
    }

    @PutMapping("/api/v1/user/{id}")
    User replaceEntry(@RequestBody User newUser, @PathVariable String id) {
        if(bucket.tryConsume(1)) {
            return userRegistry.editUser(id, newUser);
        }
        throw new RateLimitException();
    }

    @DeleteMapping("/api/v1/user/{id}")
    void deleteEntry(@PathVariable String id) {
        if(bucket.tryConsume(1)) {
            userRegistry.deleteUser(id);
        } else {
            throw new RateLimitException();
        }
    }
}
