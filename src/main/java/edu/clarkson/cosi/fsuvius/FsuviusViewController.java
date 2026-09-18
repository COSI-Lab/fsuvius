package edu.clarkson.cosi.fsuvius;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * FsuviusViewController handles requests for HTML views.
 */
@Controller
public class FsuviusViewController {

	/**
	 * Sends user to the home page.
	 */
	@GetMapping("/")
	public String homepage() {
		return "index";
	}

	/**
	 * Handles requests to /index.html to also send users to the home page.
	 */
	@GetMapping("/index.html")
	public String homepageIndex() {
		return homepage();
	}

	/**
	 * Sends user to the about page.
	 */
	@RequestMapping("/about.html")
	public String aboutPage() {
		return "about";
	}

	/**
	 * Sends user to the user editor page.
	 */
	@RequestMapping("/editor.html")
	public String editorPage() {
		return "editor";
	}
}