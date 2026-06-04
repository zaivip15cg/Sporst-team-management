package com.example.sportsteammanagement.repository;

import com.example.sportsteammanagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByTeamName(String teamName);
}
