package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
}
