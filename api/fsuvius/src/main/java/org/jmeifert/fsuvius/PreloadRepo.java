package org.jmeifert.fsuvius;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PreloadRepo {

    private static final Logger log = LoggerFactory.getLogger(PreloadRepo.class);

    @Bean
    CommandLineRunner initDatabase(EntryRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Entry("Alice", 1.0F)));
            log.info("Preloading " + repository.save(new Entry("Bob", 1.0F)));
        };
    }
}