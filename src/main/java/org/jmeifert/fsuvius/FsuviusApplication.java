package org.jmeifert.fsuvius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FsuviusApplication tells Spring how to configure and start the application.
 */
@SpringBootApplication
public class FsuviusApplication {
	/**
	 * Starts the application.
	 * @param args Unused as of now
	 */
	public static void main(String[] args) {
		SpringApplication.run(FsuviusApplication.class, args);
	}
}
