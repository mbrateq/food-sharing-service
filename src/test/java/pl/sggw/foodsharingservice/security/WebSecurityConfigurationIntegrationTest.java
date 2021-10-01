package pl.sggw.foodsharingservice.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(SpringRunner.class)
public class WebSecurityConfigurationIntegrationTest {
    TestRestTemplate restTemplate;
    URL base;
//    @LocalServerPort int port;

//    @Before
    public void setUp() throws MalformedURLException{
        restTemplate = new TestRestTemplate("user","password");
        base = new URL("http://localhost:" + 8080);
    }

//    @Test
//    public void whenLoggedUserRequestsHomePage_ThenSuccess()
//            throws IllegalStateException, IOException {
//        ResponseEntity<String> response =
//                restTemplate.getForEntity(base.toString(), String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody().contains("Baeldung"));
//    }
//
//    @Test
//    public void whenUserWithWrongCredentials_thenUnauthorizedPage()
//            throws Exception {
//
//        restTemplate = new TestRestTemplate("user", "wrongpassword");
//        ResponseEntity<String> response =
//                restTemplate.getForEntity(base.toString(), String.class);
//
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        assertTrue(response.getBody().contains("Unauthorized"));
//    }

}