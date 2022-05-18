package vttp2022.miniproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.services.SearchService;
import vttp2022.miniproject.services.ShowService;
import vttp2022.miniproject.services.UserService;

@SpringBootTest
public class ServicesTests {

    @Autowired
	private SearchService searchSvc;

	@Autowired
	private UserService userSvc;

    @Autowired
    private ShowService showSvc;

    private Show testShow;

    @BeforeEach
    public void createTestUser() {

        userSvc.createNewUser("testuser", "testuser");
    }

    @AfterEach
    public void deleteTestUser() {

        userSvc.deleteUser("testuser");
    }

	@Test
	public void shouldListFiveResults() {

		List<Show> shows = searchSvc.getTitlesByNameFromDb("Breaking Bad");
		assertEquals(5, shows.size(), "Number of results");
		
	}

	@Test
	public void shouldVerifyBob() {

		boolean result = userSvc.verifyUsername("bob", "bob");
		assertTrue(result);

	}

	@Test
	public void shouldNotVerifyWilma() {

		boolean result = userSvc.verifyUsername("wilma", "wilma");
		assertFalse(result);
	}

	@Test
	public void shouldNotCreateBob() {

		boolean result = userSvc.createNewUser("bob", "bob");
		assertFalse(result);
	}

	@Test
    public void shouldEqualOne() {

		final String username = "testuser";
		final String password = "testuser";

		testShow = new Show();
		testShow.setShowName("The Dark Knight Rises");
		testShow.setType("movie");
		testShow.setId(1386163);
		testShow.setYear(2012);
		testShow.setImageUrl("https://cdn.watchmode.com/posters/01386163_poster_w185.jpg");

		// boolean success = showSvc.saveShow(username, password, testShow);
		// System.out.println(">>>>>Success: " + success);

        showSvc.saveShow(username, password, testShow);

		List<Show> results = showSvc.getShowsByUsername(username);
		// System.out.println(">>>>>Results: " + results);
		assertEquals(1, results.size());
    }
    
}
