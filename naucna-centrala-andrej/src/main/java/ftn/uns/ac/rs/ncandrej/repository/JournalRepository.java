package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
	Journal findByIssn(String issn);
}
