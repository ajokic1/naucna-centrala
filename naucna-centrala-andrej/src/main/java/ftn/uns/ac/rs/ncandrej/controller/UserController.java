package ftn.uns.ac.rs.ncandrej.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
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
import ftn.uns.ac.rs.ncandrej.dto.FormFieldRequestDto;
import ftn.uns.ac.rs.ncandrej.dto.LoginRequest;
import ftn.uns.ac.rs.ncandrej.dto.UserDto;
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
	private FormService formService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@PostMapping("/login")
	public UserDto login(@RequestBody LoginRequest loginRequest) throws Exception{
		return userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
	}
	
	@GetMapping("/register")
	public ResponseEntity<String> startRegistrationProcess() {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("UserRegistration");
		//String processId = processService.startProcess("UserRegistration", null);
		log.info("Process " + pi.getId() + " started.");
		return ResponseEntity.ok(pi.getId());				
	}

	@GetMapping("/register/process/{processId}")
	public ResponseEntity<FormDataDto> getForm(@PathVariable String processId) {
		//Check if process ended
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processId).singleResult();
		if(pi==null) {
			FormDataDto response = new FormDataDto();
			response.setTaskId("process_ended");
			return ResponseEntity.ok(response);
		}
		//Get list of tasks
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
		Task task;
		if(tasks.size()>0)
			task = tasks.get(0);
		else
			return ResponseEntity.ok().build();
		TaskFormData formData = formService.getTaskFormData(task.getId());
		List<FormFieldRequestDto> formFieldRequests = new ArrayList<>();
		for(FormField formField: formData.getFormFields()) {
			formFieldRequests.add(new FormFieldRequestDto(formField));
		}
		return ResponseEntity.ok(new FormDataDto(task.getName(),task.getId(), formFieldRequests));
	}
	
	@PostMapping("/register/{taskId}")
	public ResponseEntity<String> submitForm(@PathVariable String taskId, @RequestBody List<FormFieldDto> fields) {
		formService.submitTaskForm(taskId, fieldsToHashMap(fields));		
		//processService.submitForm(taskId, fields);
		return ResponseEntity.ok("Form submitted.");
	}
	
	private HashMap<String, Object> fieldsToHashMap(List<FormFieldDto> fields) {
		HashMap<String, Object> map = new HashMap<>();
		for(FormFieldDto field: fields) {
			map.put(field.getName(), field.getValue());
		}
		return map;
	}
}
