package com.honeytrace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.honeytrace.entity.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    List<Credential> findTop10ByOrderByIdDesc();

    @Query("""
            SELECT c.username
            FROM Credential c
            GROUP BY c.username
            ORDER BY COUNT(c) DESC
            LIMIT 1
            """)
    String getTopUsername();

    @Query("""
            SELECT c.password
            FROM Credential c
            GROUP BY c.password
            ORDER BY COUNT(c) DESC
            LIMIT 1
            """)
    String getTopPassword();

}