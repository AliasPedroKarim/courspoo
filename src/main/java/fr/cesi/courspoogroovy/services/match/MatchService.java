package fr.cesi.courspoogroovy.services.match;

import fr.cesi.courspoogroovy.controllers.match.model.Match;
import fr.cesi.courspoogroovy.controllers.person.model.NewPerson;
import fr.cesi.courspoogroovy.dao.match.MatchDAO;
import fr.cesi.courspoogroovy.dao.meet.MeetDAO;
import fr.cesi.courspoogroovy.dao.meet.model.MeetDTO;
import fr.cesi.courspoogroovy.dao.person.PersonDAO;
import fr.cesi.courspoogroovy.dao.person.model.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class MatchService {

    private final MatchDAO matchDAO;
    private final PersonDAO personDAO;
    private final MeetDAO meetDAO;

    @Autowired
    public MatchService(MatchDAO matchDAO, PersonDAO personDAO, MeetDAO meetDAO) {
        this.matchDAO = matchDAO;
        this.personDAO = personDAO;
        this.meetDAO = meetDAO;
    }

    private List<Match> mapMeetDTOToMatch(Integer id) {
        return mapMeetDTOToMatch(id, null);
    }

    private List<Match> mapMeetDTOToMatch(Integer id, Map<String, String> params) {
        List<PersonDTO> persons = personDAO.findAll();
        List<MeetDTO> meets = meetDAO.findAll();

        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        Stream<MeetDTO> meetStream = meets.stream();

        if (id != null) {
            meetStream = meetStream.filter(
                    meet -> meet.numPerdant().equals(id) || meet.numGagnant().equals(id)
            );

            if (params != null) {
                if (params.containsKey("result")) {
                    String result = params.get("result");
                    if (result.equals("win")) {
                        meetStream = meetStream.filter(
                                meet -> meet.numGagnant().equals(id)
                        );
                    } else if (result.equals("lose")) {
                        meetStream = meetStream.filter(
                                meet -> meet.numPerdant().equals(id)
                        );
                    }
                }
            }
        }

        Stream<Match> matches = meetStream.map(meet -> {
            PersonDTO person1 = persons.stream().filter(person -> person.id().equals(meet.numGagnant())).findFirst().orElse(null);
            PersonDTO person2 = persons.stream().filter(person -> person.id().equals(meet.numPerdant())).findFirst().orElse(null);

            if (person1 == null || person2 == null) {
                return null;
            }

            return new Match(
                    meet.numGagnant(),
                    meet.numPerdant(),
                    meet.lieuTournoi() + " " + meet.annee(),
                    yearNow - person1.anneeNaissance() + yearNow - person2.anneeNaissance()
            );
        }).filter(
                Objects::nonNull
        );

        return matches.toList();
    }

    // get all matches
    public List<Match> getAllMatches() {
        return mapMeetDTOToMatch(null);
    }

    // get matches by person id
    public List<Match> getMatchesByPersonId(Integer id, Map<String, String> params) {
        return mapMeetDTOToMatch(id, params);
    }

    public RuntimeException testThrowError() {
        throw new RuntimeException("This is a test exception");
    }

    public int testThrowError2() {
        return matchDAO.testThrowError();
    }

    public Match createMatch(
            NewPerson winner,
            NewPerson loser,
            String location
    ) {
        PersonDTO winnerDTO = personDAO.createPersonIfNoExist(new PersonDTO(
                null,
                winner.name(),
                winner.lastname(),
                winner.birthYear(),
                winner.nationality()
        ));

        PersonDTO loserDTO = personDAO.createPersonIfNoExist(new PersonDTO(
                null,
                loser.name(),
                loser.lastname(),
                loser.birthYear(),
                winner.nationality()
        ));

       MeetDTO meetDTO = meetDAO.createMeet(new MeetDTO(
                winnerDTO.id(),
                loserDTO.id(),
                location,
                Calendar.getInstance().get(Calendar.YEAR)
        ));

        return new Match(
                meetDTO.numGagnant(),
                meetDTO.numPerdant(),
                meetDTO.lieuTournoi(),
                meetDTO.annee()
        );
    }

    public Match deleteMatch(Integer id) {
        MeetDTO meetDTO = meetDAO.deleteMeet(id);
        return new Match(
                meetDTO.numGagnant(),
                meetDTO.numPerdant(),
                meetDTO.lieuTournoi(),
                meetDTO.annee()
        );
    }
}
