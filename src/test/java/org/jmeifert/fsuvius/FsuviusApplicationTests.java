package org.jmeifert.fsuvius;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.jmeifert.fsuvius.user.User;

@SpringBootTest
class FsuviusApplicationTests {

	@Test
	void testUserInstantiation() {
		System.out.println("===== Testing user instantiation: =====");

		System.out.println("Default constructor: ");
		System.out.println(new User());

		System.out.println("With name \"joe\":");
		System.out.println(new User("joe"));

		System.out.println("With name \"joe\", balance 4.0:");
		System.out.println(new User("joe", 4.0F));
	}
}
