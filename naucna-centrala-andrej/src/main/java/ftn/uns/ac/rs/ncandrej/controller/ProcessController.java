package ftn.uns.ac.rs.ncandrej.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.FormDataDto;
import ftn.uns.ac.rs.ncandrej.dto.FormFieldDto;
import ftn.uns.ac.rs.ncandrej.dto.ProcessDefinitionDto;
import ftn.uns.ac.rs.ncandrej.dto.TaskDto;
import ftn.uns.ac.rs.ncandrej.service.ProcessService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController {
	
	@Autowired
	private ProcessService processService;
	
	@GetMapping("/definitions")
	public ResponseEntity<List<ProcessDefinitionDto>> getProcessDefinitions() {
		return ResponseEntity.ok(processService.getProcessDefinitions());
	}
	
	@GetMapping("/start/{key}/user/{username}")
	public ResponseEntity<String> startProcess(@PathVariable String key, @PathVariable String username) {
		// auth - Principal user u argumente controllera
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
	
	@GetMapping("/tasks/next/user/{username}/process/{processId}")
	public ResponseEntity<FormDataDto> getNextTask(@PathVariable String username, @PathVariable String processId) {
		TaskDto nextTask = processService.getTasks(username, processId).get(0);
		return ResponseEntity.ok(processService.getFormData(nextTask.getId()));
	}
	
	@GetMapping("/tasks/{taskId}")
	public ResponseEntity<FormDataDto> getFormData(@PathVariable String taskId) {
		return ResponseEntity.ok(processService.getFormData(taskId));
	}
	
	@PostMapping("/tasks/{taskId}")
	public ResponseEntity<String> getFormData(@PathVariable String taskId, @RequestBody List<FormFieldDto> fields) {
		return ResponseEntity.ok(processService.submitForm(taskId, fields));
	}
	@PostMapping("/message/{processId}")
	public ResponseEntity<Boolean> dispatchMessage(@PathVariable String processId, @RequestBody String message) {
		return ResponseEntity.ok(processService.dispatchMessage(processId, message));
	}
	
	@DeleteMapping("/{processId}")
	public ResponseEntity<String> deleteProcessInstance(@PathVariable String processId) throws Exception {
		processService.deleteProcessInstance(processId);
		return ResponseEntity.ok("Deleted");
	}
}
