package com.quartoTentativo.Prova;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DeveloperRepository extends JpaRepository<Developer, Long>{
	
}
