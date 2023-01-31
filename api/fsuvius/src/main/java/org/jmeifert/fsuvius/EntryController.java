package org.jmeifert.fsuvius;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class EntryController {
    private final EntryRepository repository;

    EntryController(EntryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/entries")
    List<Entry> all() {
        return repository.findAll();
    }

    @PostMapping("/entries")
    Entry newEntry(@RequestBody Entry newEntry) {
        return repository.save(newEntry);
    }

    @GetMapping("/entries/{id}")
    Entry one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntryNotFoundException(id));
    }

    @PutMapping("/entries/{id}")
    Entry replaceEntry(@RequestBody Entry newEntry, @PathVariable Long id) {
        return repository.findById(id)
                .map(entry -> {
                    entry.setName(newEntry.getName());
                    entry.setBalance(newEntry.getBalance());
                    return repository.save(entry);
                })
                .orElseGet(() -> {
                    newEntry.setID(id);
                    return repository.save(newEntry);
                });
    }

    @DeleteMapping("/entries/{id}")
    void deleteEntry(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
