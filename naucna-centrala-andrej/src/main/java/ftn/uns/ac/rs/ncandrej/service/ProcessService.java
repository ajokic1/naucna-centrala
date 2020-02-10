package ftn.uns.ac.rs.ncandrej.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.dto.FormDataDto;
import ftn.uns.ac.rs.ncandrej.dto.FormFieldDto;
import ftn.uns.ac.rs.ncandrej.dto.FormFieldRequestDto;
import ftn.uns.ac.rs.ncandrej.dto.ProcessDefinitionDto;
import ftn.uns.ac.rs.ncandrej.dto.RedirectFieldDto;
import ftn.uns.ac.rs.ncandrej.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessService {
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private IdentityService identityService;
	
	public List<ProcessDefinitionDto> getProcessDefinitions() {
		List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		List<ProcessDefinitionDto> definitionDtos = new ArrayList<>();
		for(ProcessDefinition definition: definitions) {
			definitionDtos.add(new ProcessDefinitionDto(definition.getKey(), definition.getName(), definition.getDescription()));
		}
		return definitionDtos;
	}
	
	public String startProcess(String key, String username) {
		identityService.setAuthenticatedUserId(username);
		String processId = runtimeService.startProcessInstanceByKey(key).getId();
		return processId;
	}
	
	public List<TaskDto> getTasks(String username) {
		List<Task> tasks = taskService
				.createTaskQuery()
				.taskAssignee(username)
				.list();
		return mapTasksToDtos(tasks);
	}
	
	public List<TaskDto> getTasks(String username, String processId) {
		List<Task> tasks = taskService
				.createTaskQuery()
				.processInstanceId(processId)
				.taskAssignee(username)
				.list();		
		return mapTasksToDtos(tasks);
	}
	
	private List<TaskDto> mapTasksToDtos(List<Task> tasks) {
		List<TaskDto> taskDtos = new ArrayList<>();
		for(Task task: tasks) {
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			taskDtos.add(new TaskDto(
					task.getId(), task.getProcessInstanceId(), task.getName(), task.getAssignee(), pd.getName()));
		}
		return taskDtos;
	}
	
	public FormDataDto getFormData(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		List<FormFieldRequestDto> formFieldRequests = new ArrayList<>();
		for(FormField formField: formData.getFormFields()) {
			formFieldRequests.add(new FormFieldRequestDto(formField));
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return new FormDataDto(task.getName(),task.getId(), formFieldRequests, task.getDescription());
	}
	
	public String submitForm(String taskId, List<FormFieldDto> fields) {
		formService.submitTaskForm(taskId, fieldsToHashMap(fields));
		return "Form submitted.";
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> fieldsToHashMap(List<FormFieldDto> fields) {
		HashMap<String, Object> map = new HashMap<>();
		for(FormFieldDto field: fields) {
			if(field.getType().equals("List")) 
				map.put(field.getName(), (String)((LinkedHashMap<String, Object>) field.getValue()).get("selectedValue"));
			else 
				map.put(field.getName(), field.getValue());
		}
		return map;
	}
	
	public Boolean dispatchMessage(String processId, String message) {
        runtimeService.createMessageCorrelation(message)
        	.processInstanceId(processId)
        	.setVariables(null)
        	.correlate();
        return true;
	}
	
	public void deleteProcessInstance(String processId) throws Exception {
		runtimeService.deleteProcessInstance(processId, null);
	}
	
}
