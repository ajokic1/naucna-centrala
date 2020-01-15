package ftn.uns.ac.rs.ncandrej.service;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.auth.JwtTokenUtil;
import ftn.uns.ac.rs.ncandrej.auth.JwtUserDetailsService;
import ftn.uns.ac.rs.ncandrej.dto.UserDto;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
public class UserService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	public void registerUser(String username, String password, String email, String firstname, String lastname, 
			String address, String city, String country) {
		User user = new User(0, username, password, email, firstname, lastname, address, city, 
				country, false, UserRole.USER);
		userRepo.save(user);
		
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser("username");
		camundaUser.setFirstName(firstname);
		camundaUser.setFirstName(lastname);
		camundaUser.setPassword(password);
		camundaUser.setEmail(email);
		identityService.saveUser(camundaUser);
		
	}
	
	public UserDto loginUser (String username, String password) throws Exception{
		authenticate(username,password);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		User user = userRepo.findByUsernameAndPassword(username, password);		
		if(user==null) throw new RuntimeException("Wrong username and/or password");
		
		return new UserDto(user, token);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
