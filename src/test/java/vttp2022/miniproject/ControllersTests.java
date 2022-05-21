package vttp2022.miniproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import vttp2022.miniproject.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTests {

    @Autowired
    private UserService userSvc;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void createTestUser() {

		userSvc.createNewUser("testuser", "testuser");
	}

	@AfterEach
	public void deleteTestUser() {

		userSvc.deleteUser("testuser");
	}

    @Test
	public void testIndexPage() {

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            String url = httpResp.getRedirectedUrl();
            Integer status = httpResp.getStatus();
            assertEquals("/index.html", url);
            assertEquals(302, status);   
        } catch (Exception ex) {
            fail("Response for index page not 302", ex);
            return;
        }
    }

    @Test
	public void testNewUserPage() {

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/newuser");

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(302, status);       
        } catch (Exception ex) {
            fail("Response for new user page not 302", ex);
            return;
        }
    }

    @Test
	public void shouldNotCreateAdmin() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "admin");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/newuser").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("username", "admin").param("password", "admin").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(400, status); 
        } catch (Exception ex) {
            fail("Response for failed creation not 400", ex);
            return;
        }
    }

    @Test
	public void shouldLoginSuccessfully() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "admin");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("username", "admin").param("password", "admin").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(200, status); 
        } catch (Exception ex) {
            fail("Response for login not 200", ex);
            return;
        }
    }

    @Test
	public void wrongLoginShouldReturn401() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "admin");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("username", "admin").param("password", "123").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(401, status); 
        } catch (Exception ex) {
            fail("Response for wrong login not 401", ex);
            return;
        }
    }

    @Test
	public void testSearchPage() {

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/searchpage");

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(302, status);       
        } catch (Exception ex) {
            fail("Response for search page not 302", ex);
            return;
        }
    }

    @Test
	public void searchShouldReturn200() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "testuser");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/search").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("search_name", "Breaking Bad").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(200, status);        
        } catch (Exception ex) {
            fail("Response for search not 200", ex);
            return;
        }
    }

    @Test
	public void savedShouldReturn201AndDeleteFromFavouritesShouldReturn200() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "testuser");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/saved").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("selected", "1386163").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(201, status); 
        } catch (Exception ex) {
            fail("Response for login not 201", ex);
            return;
        }

        RequestBuilder reqBuilder2 = MockMvcRequestBuilders.post("/favourites").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("selected", "1386163").session(httpSession);

        MvcResult mvcResult2 = null;

        try {
            mvcResult2 = mockMvc.perform(reqBuilder2).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp2 = mvcResult2.getResponse();

        try {
            Integer status2 = httpResp2.getStatus();
            assertEquals(200, status2); 
        } catch (Exception ex) {
            fail("Response for login not 201", ex);
            return;
        }
    }

    @Test
    public void getFavouritesShouldReturn200() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "testuser");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/favourites").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(200, status);      
        } catch (Exception ex) {
            fail("Response for favourites not 200", ex);
            return;
        }
    }

    @Test
	public void testLogout() {

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("username", "testuser");

        RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/logout").session(httpSession);

        MvcResult mvcResult = null;

        try {
            mvcResult = mockMvc.perform(reqBuilder).andReturn();
        } catch(Exception ex) {
            fail("Mvc invocation failed", ex);
            return;
        }

        MockHttpServletResponse httpResp = mvcResult.getResponse();

        try {
            Integer status = httpResp.getStatus();
            assertEquals(200, status);      
        } catch (Exception ex) {
            fail("Response for logout not 200", ex);
            return;
        }
    }

}
