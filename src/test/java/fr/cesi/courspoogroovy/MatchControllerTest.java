package fr.cesi.courspoogroovy;

import fr.cesi.courspoogroovy.controllers.match.model.CreateMatch;
import fr.cesi.courspoogroovy.controllers.match.model.Match;
import fr.cesi.courspoogroovy.controllers.person.model.NewPerson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MatchControllerTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetMatches() {
        ResponseEntity<Match[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/v1/matches", Match[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Match[] matches = responseEntity.getBody();
        assertNotNull(matches);
    }

    @Test
    public void testCreateMatch() {
        CreateMatch match = new CreateMatch(
                "Paris",
                2021,
                new NewPerson("John", "Doe", 25, "FR"),
                new NewPerson("Jane", "Doe", 25, "FR")
        );

        ResponseEntity<Match> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/v1/matches", match, Match.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Match matchResponse = responseEntity.getBody();
        assertNotNull(matchResponse);
        assertNotNull(matchResponse.idWinner());
        assertNotNull(matchResponse.idLoser());
        assertEquals("Paris", matchResponse.location());
    }

    @Test
    public void testGetMatch() {
        ResponseEntity<Match> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/v1/matches/1", Match.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Match match = responseEntity.getBody();
        assertNotNull(match);
    }
//
//    @Test
//    public void testDeleteMatch() {
//        restTemplate.delete("http://localhost:8080/api/v1/matches/1");
//    }

}
