package ftn.uns.ac.rs.ncandrej.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
public class JwtUserDetailsService implements UserDetailsService {	
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user==null) throw new UsernameNotFoundException("Username not found");
		
		//TODO: dodaj GrantedAuthority
		return new org.springframework.security.core.userdetails
				.User(user.getUsername(), user.getPassword(), new ArrayList<>());
		
	}
}
