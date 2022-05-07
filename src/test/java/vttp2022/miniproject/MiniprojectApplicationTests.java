package vttp2022.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.services.SearchService;

@SpringBootTest
class MiniprojectApplicationTests {

	@Autowired
	private SearchService searchSvc;

	@Test
	void shouldListFiveResults() {
		List<Show> shows = searchSvc.getTitlesByName("Breaking Bad");
		assertEquals(5, shows.size(), "Number of results");
		
		// System.out.println(">>>>>>>> List of shows: " + shows);
	}

}
