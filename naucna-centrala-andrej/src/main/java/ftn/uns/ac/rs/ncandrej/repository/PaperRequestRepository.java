package ftn.uns.ac.rs.ncandrej.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.Paper;
import ftn.uns.ac.rs.ncandrej.model.PaperRequest;
import ftn.uns.ac.rs.ncandrej.model.User;



@Repository
public interface PaperRequestRepository extends JpaRepository<PaperRequest, Long> {
	public Optional<PaperRequest> findByToken(String token);
	public Optional<PaperRequest> findByUserAndPaper(User user, Paper paper);
}
