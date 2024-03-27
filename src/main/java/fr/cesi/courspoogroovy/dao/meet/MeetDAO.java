package fr.cesi.courspoogroovy.dao.meet;

import fr.cesi.courspoogroovy.controllers.match.model.Match;
import fr.cesi.courspoogroovy.dao.meet.model.MeetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MeetDAO {
    // find all meet
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeetDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MeetDTO> findAll() {
        String sql = "SELECT * FROM RENCONTRE";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new MeetDTO(
//                rs.getLong("id"),
                rs.getInt("NuGAGNANT"),
                rs.getInt("NuPERDANT"),
                rs.getString("LIEUTOURNOI"),
                rs.getInt("ANNEE")
        ));
    }

    public MeetDTO createMeet(MeetDTO meetDTO) {
        // insert meet into database and return new meet with id from database
        String sql = "INSERT INTO RENCONTRE (NuGAGNANT, NuPERDANT, LIEUTOURNOI, ANNEE) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, meetDTO.numGagnant());
            ps.setInt(2, meetDTO.numPerdant());
            ps.setString(3, meetDTO.lieuTournoi());
            ps.setInt(4, meetDTO.annee());
            return ps;
        }, keyHolder);

        return new MeetDTO(
                meetDTO.numGagnant(),
                meetDTO.numPerdant(),
                meetDTO.lieuTournoi(),
                meetDTO.annee()
        );
    }

    public MeetDTO deleteMeet(Integer id) {
        String sql = "DELETE FROM RENCONTRE WHERE id = ?";

        MeetDTO meetDTO = jdbcTemplate.queryForObject("SELECT * FROM RENCONTRE WHERE id = ?", new Object[]{id}, (rs, rowNum) -> new MeetDTO(
//                rs.getLong("id"),
                rs.getInt("NuGAGNANT"),
                rs.getInt("NuPERDANT"),
                rs.getString("LIEUTOURNOI"),
                rs.getInt("ANNEE")
        ));

        jdbcTemplate.update(sql, id);

        return meetDTO;
    }
}
