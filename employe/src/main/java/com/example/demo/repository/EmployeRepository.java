package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modele.Employe;



public interface EmployeRepository extends JpaRepository<Employe,Long> {
	Employe findByEmail(String email);
}
