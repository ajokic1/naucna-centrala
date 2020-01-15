package ftn.uns.ac.rs.ncandrej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.uns.ac.rs.ncandrej.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findByUsernameAndPassword(String username, String password);
}
