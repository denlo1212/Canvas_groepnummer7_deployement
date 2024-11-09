package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Team;

import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findByTeamID(Long teamId);
}