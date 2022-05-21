package vttp2022.miniproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.models.User;
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

		User user = new User();
		user.setUsername("wilma");
		user.setPassword("wilma");

		boolean result = userSvc.verifyUsername(user.getUsername(), user.getPassword());
		assertFalse(result);
	}

	@Test
	public void shouldNotCreateBob() {

		boolean result = userSvc.createNewUser("bob", "bob");
		assertFalse(result);
	}

	@Test
    public void shouldEqualsOneAndAbleToDelete() {

		final String username = "testuser";
		// final String password = "testuser";

		testShow = new Show();
		testShow.setShowName("The Dark Knight Rises");
		testShow.setType("movie");
		testShow.setId(1386163);
		testShow.setYear(2012);
		testShow.setImageUrl("https://cdn.watchmode.com/posters/01386163_poster_w185.jpg");

        showSvc.saveShow(username, testShow);

		List<Show> results = showSvc.getAllShowsByUsername(username);
		// System.out.println(">>>>> Results: " + results);
		assertEquals(1, results.size());

		Integer titleId = testShow.getId();

		boolean delete = showSvc.deleteShow(username, titleId);
		assertTrue(delete);
    }

	@Test
	public void shouldRetrieveShowName() {

		Integer titleId = 1386163;

		Show showDetails = searchSvc.getDetailsByIdFromDb(titleId);
		assertEquals(showDetails.getShowName(), "The Dark Knight Rises");
		assertEquals(showDetails.getType(), "movie");
		// System.out.println(showDetails.getShowName());
		// System.out.println(showDetails.getType());
	}
    
}
