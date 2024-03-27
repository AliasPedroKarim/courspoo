package fr.cesi.courspoogroovy.controllers.test;

import fr.cesi.courspoogroovy.services.match.MatchService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    private final MatchService matchService;

    @Autowired
    TestController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/404")
    @Hidden
    public ResponseEntity<?> test404() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/500")
    public ResponseEntity<?> test500() {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/400")
    public ResponseEntity<?> test400() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/throw")
    public ResponseEntity<?> testThrow() {
        throw matchService.testThrowError();
    }

    @GetMapping("/dao")
    public ResponseEntity<?> testDao() {
        try {
            return ResponseEntity.ok(matchService.testThrowError2());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
