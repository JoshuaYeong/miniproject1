package vttp2022.miniproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.services.SearchService;
import vttp2022.miniproject.services.UserService;

@SpringBootTest
class MiniprojectApplicationTests {

	@Autowired
	private SearchService searchSvc;

	@Autowired
	private UserService userSvc;

	@Test
	void shouldListFiveResults() {

		List<Show> shows = searchSvc.getTitlesByNameFromDb("Breaking Bad");
		assertEquals(5, shows.size(), "Number of results");
		
	}

	@Test
	void shouldVerifyBob() {

		Boolean result = userSvc.verifyUsername("bob", "bob");
		assertTrue(result);

	}

	@Test
	void shouldNotVerifyWilma() {

		Boolean result = userSvc.verifyUsername("wilma", "wilma");
		assertFalse(result);;
	}

}
