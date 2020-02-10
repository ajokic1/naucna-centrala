package ftn.uns.ac.rs.ncandrej.config;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ftn.uns.ac.rs.ncandrej.dto.ListFieldDto;

@Component
@Order(Ordering.DEFAULT_ORDER + 1)
public class CamundaCustomConfig extends AbstractCamundaConfiguration {

	@Override
	public void preInit(SpringProcessEngineConfiguration processEngineConfiguration) {
		if (processEngineConfiguration.getCustomFormTypes() == null) {
			processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
		}
		List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
		formTypes.add(new ListFieldDto());
		super.preInit(processEngineConfiguration);
	}
	
	
}
