package ftn.uns.ac.rs.ncandrej;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.Paper;
import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.model.UserRole;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.PaperRepository;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;

@Component
public class CreateTestData implements ApplicationRunner {
	
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
	JournalRepository journalRepo;
	
	@Autowired
	PaperRepository paperRepo;
	
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
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin"));
			admin.setEmail("aj.jokic+admin@gmail.com");
			admin.setFirstName("Administrator");
			admin.setLastName("");
			admin.setVerified(true);
			admin.setMember(true);
			admin.setRole(UserRole.ADMIN);
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
		
		if(journalRepo.count()==0) {
			Journal j = new Journal();
			j.setActive(true);
			j.setName("Journal of Signal Processing");
			j.setIssn("ijdfsajflskdfjk");
			j.setOpenAccess(false);
			j.setMainEditor(editorRepo.findByUsername("editor"));
			journalRepo.save(j);
			
			Paper p = new Paper();
			p.setAuthor(userRepo.findByUsername("admin"));
			p.setTitle("Methods for interpreting and understanding deep neural networks");
			p.setPAbstract("This paper provides an entry point to the problem of interpreting a deep neural network model and explaining its predictions. It is based on a tutorial given at ICASSP 2017. As a tutorial paper, the set of methods covered here is not exhaustive, but sufficiently representative to discuss a number of questions in interpretability, technical challenges, and possible applications. The second part of the tutorial focuses on the recently proposed layer-wise relevance propagation (LRP) technique, for which we provide theory, recommendations, and tricks, to make most efficient use of it on real data.");
			p.setDOI("10.1016/j.dsp.2017.10.011");
			p.setField(fieldRepo.findByCode("SIG"));
			p.setJournal(j);
			paperRepo.save(p);
			
			p = new Paper();
			p.setAuthor(userRepo.findByUsername("admin"));
			p.setTitle("Waveform design for communicating radar systems using Fractional Fourier Transform");
			p.setPAbstract("A novel waveform design technique for enabling a communication channel within a pulse radar is presented. The proposed waveform is composed of quasi-orthogonal chirp sub-carriers generated by means of the Fractional Fourier Transform (FrFT), with the aim of preserving the radar performance of a typical Linear Frequency Modulated (LFM) pulse while embedding data to be sent to a cooperative system. Waveform generation and demodulation are described, together with techniques aimed at optimising the design parameters and mitigating the Inter-Carrier Interference (ICI) caused by the quasi-orthogonality of the chirp sub-carriers. The proposed FrFT based communicating-radar (CoRadar) waveform design is compared with Orthogonal Frequency Division Multiplexing (OFDM) based CoRadar with respect to both radar and communication operations.");
			p.setDOI("10.1016/j.dsp.2018.05.002");
			p.setField(fieldRepo.findByCode("SIG"));
			p.setJournal(j);
			paperRepo.save(p);
			
			j = new Journal();
			j.setActive(true);
			j.setName("Journal of Number Theory");
			j.setIssn("ijdfsajfsddfsafk");
			j.setMainEditor(editorRepo.findByUsername("editor2"));
			j.setOpenAccess(true);
			journalRepo.save(j);
			
			p = new Paper();
			p.setAuthor(userRepo.findByUsername("admin"));
			p.setTitle("Methods for interpreting and understanding deep neural networks");
			p.setPAbstract("This paper provides an entry point to the problem of interpreting a deep neural network model and explaining its predictions. It is based on a tutorial given at ICASSP 2017. As a tutorial paper, the set of methods covered here is not exhaustive, but sufficiently representative to discuss a number of questions in interpretability, technical challenges, and possible applications. The second part of the tutorial focuses on the recently proposed layer-wise relevance propagation (LRP) technique, for which we provide theory, recommendations, and tricks, to make most efficient use of it on real data.");
			p.setDOI("10.1016/j.dsp.2017.10.011");
			p.setField(fieldRepo.findByCode("MAT"));
			p.setJournal(j);
			paperRepo.save(p);
		}
	}
	private void createCamundaUser(User user) {
		//org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(user.getUsername());
		//camundaUser.setPassword(user.getPassword());
		//camundaUser.setEmail(user.getEmail());
		//identityService.saveUser(camundaUser);
	}

}
