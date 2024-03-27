package fr.cesi.courspoogroovy.dao.person.model;

public record PersonDTO(
        Integer id, String nom, String prenom, Integer anneeNaissance, String nationalite) {
}
