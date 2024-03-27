package fr.cesi.courspoogroovy;

import fr.cesi.courspoogroovy.controllers.match.model.CreateMatch;
import fr.cesi.courspoogroovy.controllers.match.model.Match;
import fr.cesi.courspoogroovy.controllers.person.model.NewPerson;
import fr.cesi.courspoogroovy.services.match.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Test
    public void testCreateMatch() {
        CreateMatch createMatch = new CreateMatch(
                "Paris",
                2021,
                new NewPerson("John", "Doe", 25, "FR"),
                new NewPerson("Jane", "Doe", 25, "FR")
        );
        Match match = matchService.createMatch(
                createMatch.winner(),
                createMatch.loser(),
                createMatch.location()
        );
        assertNotNull(match);
        assertNotNull(match.idWinner());
        assertNotNull(match.idLoser());
        assertEquals("Paris", match.location());
    }

    // @Test
    public void testGetAllMatches() {
        // List<Match> matches = matchService.getAllMatches();
        // assertNotNull(matches);
        // assertEquals(0, matches.size());
    }

    // @Test
    public void testGetMatch() {
        // Match match = matchService.getMatch(1L);
        // assertNull(match);
    }

    // @Test
    public void testDeleteMatch() {
        // matchService.deleteMatch(1L);
    }

    // @Test
    public void testGetMatchesByPersonId() {
        // List<Match> matches = matchService.getMatchesByPersonId(1L);
        // assertNotNull(matches);
        // assertEquals(0, matches.size());
    }
}
