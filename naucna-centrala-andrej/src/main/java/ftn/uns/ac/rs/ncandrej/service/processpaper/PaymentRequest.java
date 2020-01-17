package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("paymentRequest")
public class PaymentRequest implements JavaDelegate{
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
	}
}
