package fr.cesi.courspoogroovy.dao.match;

import fr.cesi.courspoogroovy.services.match.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MatchDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Match> findAll() {
        return null;
    }

    public int testThrowError() {
        throw new RuntimeException("Test error");
    }
}
