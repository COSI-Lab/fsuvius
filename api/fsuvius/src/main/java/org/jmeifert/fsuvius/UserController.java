package org.jmeifert.fsuvius;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    private UserRegistry userRegistry;

    UserController() {
        userRegistry = new UserRegistry();
    };

    @GetMapping("/users")
    List<User> all() {
        return userRegistry.getAll();
    }

    @PostMapping("/users")
    User newEntry(@RequestBody User newUser) {
        return userRegistry.createUser(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return userRegistry.getUser(id);
    }

    @PutMapping("/users/{id}")
    User replaceEntry(@RequestBody User newUser, @PathVariable Long id) {
        return userRegistry.editUser(id, newUser);
    }

    @DeleteMapping("/users/{id}")
    void deleteEntry(@PathVariable Long id) {
        userRegistry.deleteUser(id);
    }
}
