package ftn.uns.ac.rs.ncandrej.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.FormDataDto;
import ftn.uns.ac.rs.ncandrej.dto.ProcessDefinitionDto;
import ftn.uns.ac.rs.ncandrej.dto.TaskDto;
import ftn.uns.ac.rs.ncandrej.service.ProcessService;

@RestController
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@GetMapping("/definitions")
	public ResponseEntity<List<ProcessDefinitionDto>> getProcessDefinitions() {
		return ResponseEntity.ok(processService.getProcessDefinitions());
	}
	
	@GetMapping("/start/{key}/user/{username}")
	public ResponseEntity<String> startProcess(@PathVariable String key, @PathVariable String username) {
		return ResponseEntity.ok(processService.startProcess(key, username));
	}
	
	@GetMapping("/tasks/user/{username}")
	public ResponseEntity<List<TaskDto>> getTasks(@PathVariable String username) {
		return ResponseEntity.ok(processService.getTasks(username));
	}
	
	@GetMapping("/tasks/user/{username}/process/{processId}")
	public ResponseEntity<List<TaskDto>> getTasks(@PathVariable String username, @PathVariable String processId) {
		return ResponseEntity.ok(processService.getTasks(username, processId));
	}
	
	@GetMapping("/tasks/{taskId}")
	public ResponseEntity<FormDataDto> getFormData(@PathVariable String taskId) {
		return ResponseEntity.ok(processService.getFormData(taskId));
	}
}
