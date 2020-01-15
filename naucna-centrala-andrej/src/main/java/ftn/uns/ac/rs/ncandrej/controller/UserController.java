package ftn.uns.ac.rs.ncandrej.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.FormDataDto;
import ftn.uns.ac.rs.ncandrej.dto.FormFieldDto;
import ftn.uns.ac.rs.ncandrej.dto.LoginRequest;
import ftn.uns.ac.rs.ncandrej.dto.UserDto;
import ftn.uns.ac.rs.ncandrej.service.ProcessService;
import ftn.uns.ac.rs.ncandrej.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProcessService processService;
	
	@PostMapping("/login")
	public UserDto login(@RequestBody LoginRequest loginRequest) throws Exception{
		return userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
	}
	
	@GetMapping("/register")
	public ResponseEntity<String> startRegistrationProcess() {
		String processId = processService.startProcess("UserRegistration", null);
		log.info("Process " + processId + " started.");
		return ResponseEntity.ok(processId);				
	}

	@GetMapping("/register/process/{processId}")
	public ResponseEntity<FormDataDto> getForm(@PathVariable String processId) {
		String taskId = processService
				.getTasks(processId, "guest")
				.stream()
				.findFirst()
				.get()
				.getId();		
		return ResponseEntity.ok(processService.getFormData(taskId));
	}
	
	@PostMapping("/register/{taskId}")
	public ResponseEntity<String> submitForm(@PathVariable String taskId, @RequestBody List<FormFieldDto> fields) {
		processService.submitForm(taskId, fields);
		return ResponseEntity.ok("Form submitted.");
	}
}
