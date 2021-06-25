package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;


import rva.jpa.Status;


public interface StatusRepository extends JpaRepository<Status,Integer> {

	Collection<Status> findByNazivContainingIgnoreCase(String naziv);
	
	Collection<Status> findByOznakaContainingIgnoreCase(String oznaka);
	
}
