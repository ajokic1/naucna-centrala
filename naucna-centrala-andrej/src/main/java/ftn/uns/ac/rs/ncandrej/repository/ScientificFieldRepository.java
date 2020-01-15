package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.ScientificField;

@Repository
public interface ScientificFieldRepository extends JpaRepository<ScientificField, Long> {
	public ScientificField findByCode (String code);
}
