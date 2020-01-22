package ftn.uns.ac.rs.ncandrej.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.Files;

import ftn.uns.ac.rs.ncandrej.config.FileConfiguration;
import ftn.uns.ac.rs.ncandrej.config.KpConfiguration;
import ftn.uns.ac.rs.ncandrej.dto.OrderDto;
import ftn.uns.ac.rs.ncandrej.dto.OrderResponseDto;
import ftn.uns.ac.rs.ncandrej.dto.PaperDto;
import ftn.uns.ac.rs.ncandrej.dto.PaperResponseDto;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.Paper;
import ftn.uns.ac.rs.ncandrej.model.PaperRequest;
import ftn.uns.ac.rs.ncandrej.model.PaperStatus;
import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.PaperRepository;
import ftn.uns.ac.rs.ncandrej.repository.PaperRequestRepository;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;

@Service
public class PaperService {

	@Autowired
	private PaperRepository paperRepo;
	
	@Autowired
	private JournalRepository journalRepo;
	
	@Autowired
	private FileConfiguration fileConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PaperRequestRepository paperRequestRepo;
	
	@Autowired
	private KpConfiguration kpConfig;
	
	@Autowired
	private JournalService journalService;
	
	@Value("baseUrl")
	private String baseUrl;
	
	@Value("frontBaseUrl")
	private String frontBaseUrl;
	
	
	
	public List<PaperDto> getAll(long journalId) {
		Journal journal = journalRepo.findById(journalId).get();
		List<PaperDto> dtos = new ArrayList<>();
		List<Paper> papers = journal.getPapers();
		for(Paper paper: papers) {
			dtos.add(new PaperDto(paper));
		}
		return dtos;
	}
	
	public ResponseEntity<PaperResponseDto> checkDocument(long paperId, String username) {
		Optional<Paper> p = paperRepo.findById(paperId);
		if(!p.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		Paper paper = p.get();
		Journal journal = paper.getJournal();
		User user = userRepo.findByUsername(username);
		
		if(journal.isOpenAccess())
			return ResponseEntity.ok(new PaperResponseDto(PaperStatus.OPEN_ACCESS, 
					baseUrl + "/papers/" + paperId + "/open"));
		
		if(journalService.checkSubscription(journal.getSellerUuid(), username))
			return ResponseEntity.ok(new PaperResponseDto(PaperStatus.SUBSCRIBED, 
					baseUrl + "/papers/" + paperId + "/subscribed"));
		
		Optional<PaperRequest> paperRequest = paperRequestRepo.findByUserAndPaper(user, paper);
		if(paperRequest.isPresent() && paperRequest.get().isPaid())
			return ResponseEntity.ok(new PaperResponseDto(PaperStatus.PAID,
					baseUrl + "/papers/paid" + paperRequest.get().getToken()));
		
		return ResponseEntity.ok(new PaperResponseDto(PaperStatus.MUST_PAY, 
				baseUrl + "/papers/" + paperId + "/pay"));
	}
	
	public ResponseEntity<String> requestDocumentPayment(long paperId, String username) {
		Optional<Paper> p = paperRepo.findById(paperId);
		if(p.isPresent()) {
			Paper paper = p.get();
			Journal journal = paper.getJournal();
			
			PaperRequest paperRequest = new PaperRequest();
			paperRequest.setPaper(paper);
			paperRequest.setUser(userRepo.findByUsername(username));
			paperRequest.setToken(RandomStringUtils.randomAlphanumeric(32));
			
			OrderDto order = new OrderDto();
			order.setDescription("ncandrej-"+paper.getId());
			order.setSellerUuid(journal.getSellerUuid());
			order.setSuccessUrl(frontBaseUrl + "/success/" + paperRequest.getToken());
			order.setFailUrl(frontBaseUrl + "/fail");
			//TODO: Handle bad response
			OrderResponseDto response = restTemplate.postForEntity(
					kpConfig.getUrls().get("payment"), 
					order, 
					OrderResponseDto.class).getBody();
			if(response == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KP error");
			
			String redirectUrl = kpConfig.getUrls().get("payment_front") + "/"
					+ response.getTransactionId() + "/"
					+ response.getUuid() + "/"
					+ response.getAmount();
			return ResponseEntity.ok(redirectUrl);			
		} else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	public ResponseEntity<Resource> getDocumentSubscribed(long paperId, String username) throws IOException{
		Paper paper = paperRepo.findById(paperId).get();
		Journal journal = paper.getJournal();
		
		if(journalService.checkSubscription(journal.getSellerUuid(), username)) {			
			return getDocument(paper);
		}
		else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	public ResponseEntity<Resource> getDocumentPaid(String token) throws IOException {
		Optional<PaperRequest> paperRequest = paperRequestRepo.findByToken(token);
		if(paperRequest.isPresent()) {
			Paper paper = paperRequest.get().getPaper();
			if(token.equals(paperRequest.get().getToken())) {
				paperRequest.get().setPaid(true);
				paperRequestRepo.save(paperRequest.get());
				return getDocument(paper);
			} else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	private ResponseEntity<Resource> getDocument(Paper paper) throws IOException {
		String filename = paper.getDocumentPath();
		
		Path path = Paths.get(fileConfig.getUploadDir()).resolve(filename);
		File file = path.toFile();
		ByteArrayResource resource = new ByteArrayResource(Files.asByteSource(file).read());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+paper.getDocumentPath()+"\"");
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}
