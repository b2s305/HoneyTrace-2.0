package com.honeytrace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honeytrace.entity.Session;

public interface SessionRepository extends JpaRepository<Session,Long>{

}