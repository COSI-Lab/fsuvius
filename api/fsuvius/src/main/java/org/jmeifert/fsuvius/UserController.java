package org.jmeifert.fsuvius;

import java.util.List;

import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
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
    User newEntry(@RequestBody String name) {
        System.out.println("CREATED USER");
        return userRegistry.createUser(name);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        return userRegistry.getUser(id);
    }

    @PutMapping("/users/{id}")
    User replaceEntry(@RequestBody User newUser, @PathVariable String id) {
        return userRegistry.editUser(id, newUser);
    }

    @DeleteMapping("/users/{id}")
    void deleteEntry(@PathVariable String id) {
        userRegistry.deleteUser(id);
    }
}
