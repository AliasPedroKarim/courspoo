package fr.cesi.courspoogroovy.dao.person;

import fr.cesi.courspoogroovy.dao.person.model.PersonDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class PersonDAO {

    private final String apiUrl = "https://8443-cesi2022-spring3-nk8oef9q6ta.ws-eu110.gitpod.io/api/v1/persons";
    private final RestTemplate restTemplate;

    public PersonDAO() {
        this.restTemplate = new RestTemplate();
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // custom headers
        headers.set("X-CESI-For", "AHMED Karim");

        HttpEntity<PersonDTO> requestEntity = new HttpEntity<>(personDTO, headers);
        ResponseEntity<PersonDTO> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, PersonDTO.class);

        return responseEntity.getBody();
    }

    public PersonDTO createPersonIfNoExist(PersonDTO personDTO) {
        if (personDTO.id() == null || findPersonById(personDTO.id()) == null) {
            return createPerson(personDTO);
        }
        return null;
    }

    public List<PersonDTO> findAll() {
        ResponseEntity<PersonDTO[]> responseEntity = restTemplate.getForEntity(apiUrl, PersonDTO[].class);
        PersonDTO[] personsArray = responseEntity.getBody();
        return personsArray != null ? List.of(personsArray) : List.of();
    }

    public PersonDTO findPersonById(Integer id) {
        String personUrl = apiUrl + "/" + id;
        ResponseEntity<PersonDTO> responseEntity = restTemplate.getForEntity(personUrl, PersonDTO.class);
        return responseEntity.getBody();
    }
}
