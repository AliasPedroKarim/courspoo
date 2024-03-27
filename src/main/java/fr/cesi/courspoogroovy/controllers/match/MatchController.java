package fr.cesi.courspoogroovy.controllers.match;

import fr.cesi.courspoogroovy.controllers.match.model.CreateMatch;
import fr.cesi.courspoogroovy.controllers.match.model.Match;
import fr.cesi.courspoogroovy.services.match.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMatch(Long id) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<Match> createMatch(@Valid @RequestBody CreateMatch matchData) {
        return new ResponseEntity<>(matchService.createMatch(
                matchData.winner(),
                matchData.loser(),
                matchData.location()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Match> deleteMatch(Integer id) {
        return new ResponseEntity<>(matchService.deleteMatch(id), HttpStatus.OK);
    }

    // query string sort=asc or sort=desc
    // query string sort_property=age or sort_property=name
    @GetMapping("/")
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getAllMatches();

        if (matches == null) {
            matches = new ArrayList<>();
        }
        // response body is a list of matches (json)
        return ResponseEntity.ok(matches);
    }

    // query string result=win or result=lose
    @GetMapping("/person/{id}")
    public ResponseEntity<List<Match>> getMatchesByPersonId(
            @PathVariable Integer id,
            @RequestParam(required = false) String result

    ) {
        List<Match> matches = matchService.getMatchesByPersonId(
                id,
                Map.of("result", result)
        );

        if (matches == null) {
            matches = new ArrayList<>();
        }
        // response body is a list of matches (json)
        return ResponseEntity.ok(matches);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.badRequest().body(errors.toString());
    }
}
