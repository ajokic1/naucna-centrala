package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.Editor;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long>{
	public Editor findByUsername(String username);
}
