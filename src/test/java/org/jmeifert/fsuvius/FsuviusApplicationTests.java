package org.jmeifert.fsuvius;

import org.jmeifert.fsuvius.util.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class FsuviusApplicationTests {

	@Test
	void testFsuviusController() {
		Log log = new Log("FsuviusApplicationTests");
		log.print("Starting test of FsuviusController...");

		log.print("Creating a FsuviusController...");
		FsuviusController fc = new FsuviusController();

		log.print("Test of FsuviusController completed.");
	}

	// TODO write proper tests
}
