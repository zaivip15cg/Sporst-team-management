package com.example.sportsteammanagement.repository;

import com.example.sportsteammanagement.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Integer> {

    Optional<Tokens> findByToken(String token);
}
