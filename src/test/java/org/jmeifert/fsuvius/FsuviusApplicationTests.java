package org.jmeifert.fsuvius;

import org.jmeifert.fsuvius.user.UserRegistry;
import org.jmeifert.fsuvius.util.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.jmeifert.fsuvius.user.User;

@SpringBootTest
class FsuviusApplicationTests {

	@Test
	void testUserRegistry() {
		Log log = new Log("FsuviusApplicationTests");
		log.print("Starting test of UserRegistry...");

		log.print("Resetting UserRegistry...");
		UserRegistry ur = new UserRegistry();
		ur.reset();

		log.print("Confirming proper reset of UserRegistry...");
		assert ur.getAll().size() == 0;

		log.print("Creating a user...");
		User a = ur.createUser("User");

		log.print("Checking for proper creation of this User...");
		assert ur.getAll().get(0) == a && ur.getUser(a.getID()) == a;

		log.print("Editing the user...");
		a.setName("New Name");
		a.setBalance(5.0F);
		ur.editUser(a.getID(), a);

		log.print("Checking for proper edit of this User...");
		assert ur.getAll().get(0) == a && ur.getUser(a.getID()) == a;

		log.print("Deleting the user...");
		ur.deleteUser(a.getID());

		log.print("Checking for proper deletion of this User...");
		assert ur.getAll().size() == 0;

		log.print("Test of UserRegistry completed.");
	}

	@Test
	void testFsuviusController() {
		Log log = new Log("FsuviusApplicationTests");
		log.print("Starting test of FsuviusController...");

		log.print("Resetting UserRegistry...");
		UserRegistry u = new UserRegistry();
		u.reset();

		log.print("Creating a FsuviusController...");
		FsuviusController fc = new FsuviusController();

		log.print("Confirming that there are no users...");
		assert fc.getUsers().size() == 0;

		log.print("Creating a user...");
		User a = fc.newUser("User");

		log.print("Checking for proper creation of this User...");
		assert fc.getUsers().get(0) == a && fc.getUser(a.getID()) == a;

		log.print("Editing the user...");
		a.setName("New Name");
		a.setBalance(5.0F);
		fc.editUser(a, a.getID());

		log.print("Checking for proper edit of this User...");
		assert fc.getUsers().get(0) == a && fc.getUser(a.getID()) == a;

		log.print("Deleting the user...");
		fc.deleteUser(a.getID());

		log.print("Checking for proper deletion of this User...");
		assert fc.getUsers().size() == 0;

		log.print("Test of FsuviusController completed.");
	}
}
