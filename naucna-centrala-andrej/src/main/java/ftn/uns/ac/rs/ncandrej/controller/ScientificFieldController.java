package ftn.uns.ac.rs.ncandrej.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.ScientificFieldDto;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@RestController
@RequestMapping("/fields")
public class ScientificFieldController {
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@GetMapping
	public List<ScientificFieldDto> getFields() {
		List<ScientificField> fields = fieldRepo.findAll();
		List<ScientificFieldDto> fieldDtos = new ArrayList<>();
		for(ScientificField field: fields) {
			fieldDtos.add(new ScientificFieldDto(field));
		}
		return fieldDtos;
	}
}
