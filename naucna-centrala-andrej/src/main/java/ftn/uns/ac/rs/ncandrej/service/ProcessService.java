package ftn.uns.ac.rs.ncandrej.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.cockpit.impl.plugin.base.dto.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ftn.uns.ac.rs.ncandrej.dto.FormDataDto;
import ftn.uns.ac.rs.ncandrej.dto.FormFieldDto;
import ftn.uns.ac.rs.ncandrej.util.CamundaEndpoints;
import lombok.RequiredArgsConstructor;

@Service
public class ProcessService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String startProcess(String processKey, Map<String, VariableValueDto> variables) {
        final StartProcessInstanceDto startProcessInstance = new StartProcessInstanceDto();
        startProcessInstance.setVariables(variables);

        ProcessInstanceDto processInstance = restTemplate
        		.postForObject(
        				String.format(CamundaEndpoints.BASE_PATH + CamundaEndpoints.START_PROCESS, processKey), 
        				startProcessInstance, ProcessInstanceDto.class);
        return processInstance.getId();
    }
	
	public List<TaskDto> getTasks(String processId, String assignee) {
        final UriComponentsBuilder builder = UriComponentsBuilder
        		.fromHttpUrl(CamundaEndpoints.BASE_PATH + CamundaEndpoints.TASK);

        if (processId != null) {
            builder.queryParam("processInstanceId", processId);
        }

        if (assignee != null) {
            builder.queryParam("assignee", assignee);
        }
        
        builder.queryParam("active", "true");

        ParameterizedTypeReference<List<TaskDto>> returnType = 
        		new ParameterizedTypeReference<List<TaskDto>>() {};
        
        final List<TaskDto> tasks = restTemplate
        		.exchange(builder.build().toUri(), HttpMethod.GET, null, returnType)
        		.getBody();
        return tasks;
    }
	
	public TaskDto getTask(String taskId) {
        String url = String.format(CamundaEndpoints.BASE_PATH + CamundaEndpoints.GET_TASK, taskId);
        return restTemplate.getForObject(url, TaskDto.class);
    }
	
	public FormDataDto getFormData(String taskId) {

        String url = String.format(CamundaEndpoints.BASE_PATH + CamundaEndpoints.FORM_VARIABLES, taskId);

        ParameterizedTypeReference<Map<String, VariableValueDto>> returnType = 
        		new ParameterizedTypeReference<Map<String, VariableValueDto>>() {};
        
  		final List<FormFieldDto> fields = restTemplate
        		.exchange(url, HttpMethod.GET, null, returnType)
        		.getBody()
        		.entrySet()
        		.stream()
        		.map(field -> new FormFieldDto(field.getKey(), field.getValue()))
        		.collect(Collectors.toList());

        return new FormDataDto(taskId, fields);
    }
    public Object getVariable(String processId, String key) {
        String url = String.format(CamundaEndpoints.BASE_PATH + CamundaEndpoints.GET_VARIABLE, processId, key);
        return restTemplate.getForObject(url, VariableValueDto.class).getValue();
    }

	public void submitForm(String taskId, List<FormFieldDto> formFields) {
        String url = String.format(CamundaEndpoints.BASE_PATH + CamundaEndpoints.SUBMIT_FORM, taskId);

        CompleteTaskDto completeTask = new CompleteTaskDto();
        completeTask.setVariables(
        		formFields
        			.stream()
        			.collect(Collectors.toMap(FormFieldDto::getName, FormFieldDto::getValue)));

        restTemplate.postForLocation(url, completeTask);
    }
	public void createUser(UserDto user) {
		String url = CamundaEndpoints.BASE_PATH + CamundaEndpoints.CREATE_USER;
        restTemplate.postForLocation(url, user);
    }
}
