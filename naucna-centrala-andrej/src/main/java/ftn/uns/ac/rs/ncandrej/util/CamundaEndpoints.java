package ftn.uns.ac.rs.ncandrej.util;

public class CamundaEndpoints {
	public static final String BASE_PATH = "https://localhost:8102/rest";
	
	public static final String CREATE_USER = "/user/create";
	
	public static final String TASK = "/task";
    public static final String SUBMIT_FORM = "/task/%s/submit-form";
    public static final String FORM_VARIABLES = "/task/%s/form-variables";
    public static final String GET_TASK = "/task/%s";
    public static final String COMPLETE = "/task/%s/complete";
    
    public static final String GET_PROCESS = "/history/process-instance/%s";
	public static final String START_PROCESS = "/process-definition/key/%s/start";
	public static final String GET_VARIABLE = "/process-instance/%s/variables/%s";    
}