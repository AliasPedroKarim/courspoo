package fr.cesi.courspoogroovy.services.match.model;

public record Match(
        Long idWinner, Long idLoser, String location, Long sumAge) {
}