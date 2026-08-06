package com.honeytrace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.honeytrace.entity.Attacker;
import com.honeytrace.repository.AttackerRepository;

@Service
public class AttackerService {

    private final AttackerRepository attackerRepository;

    public AttackerService(AttackerRepository attackerRepository) {
        this.attackerRepository = attackerRepository;
    }

    public List<Attacker> getAllAttackers() {
        return attackerRepository.findAll();
    }

    public Attacker saveAttacker(Attacker attacker) {
        return attackerRepository.save(attacker);
    }
}