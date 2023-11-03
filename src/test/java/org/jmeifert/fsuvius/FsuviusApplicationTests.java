package org.jmeifert.fsuvius;

import org.jmeifert.fsuvius.util.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
class FsuviusApplicationTests {

	@Test
	void testFsuviusController() throws IOException {
		Log log = new Log("FsuviusApplicationTests");
		log.print("Starting test of FsuviusController...");

		log.print("Creating a FsuviusController...");
		FsuviusController fc = new FsuviusController();

		log.print("Test of FsuviusController completed.");
	}
}
