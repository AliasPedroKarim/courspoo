package fr.cesi.courspoogroovy.controllers.match.model;

import fr.cesi.courspoogroovy.controllers.person.model.NewPerson;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateMatch(
        @Size(min = 1, max = 16) String location, Integer year, NewPerson winner, NewPerson loser) {
}