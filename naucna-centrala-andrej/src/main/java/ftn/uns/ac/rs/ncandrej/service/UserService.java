package ftn.uns.ac.rs.ncandrej.service;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.auth.JwtTokenUtil;
import ftn.uns.ac.rs.ncandrej.auth.JwtUserDetailsService;
import ftn.uns.ac.rs.ncandrej.dto.UserDto;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void registerUser(String username, String password, String email, String firstname, String lastname, 
			String address, String city, String country) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setAddress(address);
		user.setCity(city);
		user.setCountry(country);
		user.setMember(false);
		user.setRole(UserRole.USER);
		user.setVerified(false);
		userRepo.save(user);
		
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(username);
		camundaUser.setFirstName(firstname);
		camundaUser.setLastName(lastname);
		camundaUser.setPassword(passwordEncoder.encode(password));
		camundaUser.setEmail(email);
		identityService.saveUser(camundaUser);
		
	}
	
	public UserDto loginUser (String username, String password) throws Exception{
		authenticate(username,password);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		log.info(passwordEncoder.encode(password));
		User user = userRepo.findByUsername(username);		
		
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
