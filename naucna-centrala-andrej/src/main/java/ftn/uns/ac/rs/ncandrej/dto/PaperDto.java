package ftn.uns.ac.rs.ncandrej.dto;

import java.util.ArrayList;
import java.util.List;

import ftn.uns.ac.rs.ncandrej.model.CoAuthor;
import ftn.uns.ac.rs.ncandrej.model.Paper;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDto {
	private long id;
	private String keywords;
	private String pAbstract;
	private String title;
	private ScientificFieldDto field;
	private String authorName;
	private List<String> coAuthorNames;
	private String DOI;
	
	public PaperDto(Paper paper) {
		id = paper.getId();
		keywords = paper.getKeywords();
		pAbstract = paper.getPAbstract();
		title = paper.getTitle();
		ScientificField field = paper.getField();
		this.field = new ScientificFieldDto(field.getCode(), field.getName());
		User author = paper.getAuthor();
		authorName = author.getFirstName() + author.getLastName();
		coAuthorNames = new ArrayList<>();
		List<CoAuthor> coAuthors = paper.getCoAuthors();
		for(CoAuthor coAuthor: coAuthors) {
			coAuthorNames.add(coAuthor.getFirstName() + coAuthor.getLastName());
		}
		DOI = paper.getDOI();
 	}
}
