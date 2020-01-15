package ftn.uns.ac.rs.ncandrej.dto;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private long id;
	private String username;	
	private String password;	
	private String email;
	private String firstName;	
	private String lastName;	
	private String address;	
	private String city;	
	private String country;	
	private boolean verified;	
	private  UserRole role;
	private String token;
	
	public UserDto(User user, String token) {
		id=user.getId();
		username=user.getUsername();
		password=user.getPassword();
		email=user.getEmail();
		firstName=user.getFirstName();
		lastName=user.getLastName();
		address=user.getAddress();
		city=user.getCity();
		country=user.getCountry();
		verified=user.isVerified();
		role=user.getRole();
		this.token=token;
	}
}
