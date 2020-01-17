package ftn.uns.ac.rs.ncandrej.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.EditorDto;
import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;

@RestController
@RequestMapping("/editors")
public class EditorController {
	
	@Autowired
	EditorRepository editorRepo;
	
	@GetMapping
	public List<EditorDto> getEditors() {
		List<Editor> editors = editorRepo.findAll();
		List<EditorDto> editorDtos = new ArrayList<>();
		
		for(Editor editor: editors) {
			editorDtos.add(new EditorDto(editor));
		}
		return editorDtos;
	}
	
}
