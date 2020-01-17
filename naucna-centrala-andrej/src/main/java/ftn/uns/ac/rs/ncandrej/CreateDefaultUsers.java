package ftn.uns.ac.rs.ncandrej;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;

@Component
public class CreateDefaultUsers implements ApplicationRunner {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@Autowired
	EditorRepository editorRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	IdentityService identityService; 
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		//Naucne oblasti
		if(fieldRepo.count()==0) {
			String[] codes = {"MAT", "PHY", "AI", "CS", "SIG"};
			String[] names = {"Matematika", "Fizika", "Umjetna inteligencija", "Racunarske nauke", "Obrada signala"};
			ScientificField field;
			for(int i=0; i<codes.length; i++) {
				field = new ScientificField();
				field.setCode(codes[i]);
				field.setName(names[i]);
				fieldRepo.save(field);				
			}			
		}
		
		//Admin user - kao camunda
		if(userRepo.findByUsername("admin") == null) {
			User admin = new User(0, 
					"admin", 
					passwordEncoder.encode("admin"), 
					"aj.jokic+admin@gmail.com",
					"Administrator", 
					"", 
					"", 
					"", 
					"", 
					true, 
					UserRole.ADMIN, 
					true);
			userRepo.save(admin);
		}
		//Reviewer
		if(reviewerRepo.findByUsername("reviewer") == null) {
			Reviewer reviewer = new Reviewer();
			reviewer.setUsername("reviewer");
			reviewer.setEmail("aj.jokic+reviewer@gmail.com");
			reviewer.setPassword(passwordEncoder.encode("reviewer"));
			reviewer.setFirstName("Reviewer");
			reviewer.setVerified(true);
			reviewer.setRole(UserRole.REVIEWER);
			reviewer.setMember(true);
			reviewerRepo.save(reviewer);
			createCamundaUser(reviewer);
		}
		if(reviewerRepo.findByUsername("reviewer2") == null) {
			Reviewer reviewer = new Reviewer();
			reviewer.setUsername("reviewer2");
			reviewer.setEmail("aj.jokic+reviewer2@gmail.com");
			reviewer.setPassword(passwordEncoder.encode("reviewer2"));
			reviewer.setFirstName("Reviewer");
			reviewer.setLastName("2");
			reviewer.setVerified(true);
			reviewer.setRole(UserRole.REVIEWER);
			reviewer.setMember(true);
			reviewerRepo.save(reviewer);
			createCamundaUser(reviewer);
		}
		//Editor
		if(editorRepo.findByUsername("editor") == null) {
			Editor editor = new Editor();
			editor.setUsername("editor");
			editor.setEmail("aj.jokic+editor@gmail.com");
			editor.setPassword(passwordEncoder.encode("editor"));
			editor.setFirstName("Editor");
			editor.setVerified(true);
			editor.setRole(UserRole.EDITOR);
			editor.setMember(true);
			editorRepo.save(editor);
			createCamundaUser(editor);
		}
		if(editorRepo.findByUsername("editor2") == null) {
			Editor editor = new Editor();
			editor.setUsername("editor2");
			editor.setEmail("aj.jokic+editor2@gmail.com");
			editor.setPassword(passwordEncoder.encode("editor2"));
			editor.setFirstName("Editor");
			editor.setLastName("2");
			editor.setVerified(true);
			editor.setRole(UserRole.EDITOR);
			editor.setMember(true);
			editorRepo.save(editor);
			createCamundaUser(editor);
		}
	}
	private void createCamundaUser(User user) {
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(user.getUsername());
		camundaUser.setPassword(user.getPassword());
		camundaUser.setEmail(user.getEmail());
		identityService.saveUser(camundaUser);
	}

}
