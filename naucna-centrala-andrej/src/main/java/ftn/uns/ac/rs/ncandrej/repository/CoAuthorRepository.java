package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.CoAuthor;

@Repository
public interface CoAuthorRepository extends JpaRepository<CoAuthor, Long>{

}
