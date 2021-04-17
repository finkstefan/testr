package rva.controllers;

import java.util.Collection;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Fakultet;
import rva.repository.FakultetRepository;

@CrossOrigin
@RestController
@Api(tags = {"Fakultet CRUD operacije"})
public class FakultetRestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FakultetRepository fakultetRepository;
	
	@GetMapping("fakultet")
	@ApiOperation(value = "Vraća sve fakultete iz baze podataka")
	public Collection<Fakultet> getFakulteti(){
		return fakultetRepository.findAll();
	}
	
	
	@GetMapping("fakultet/{id}")
	@ApiOperation(value = "Vraća fakultet na osnovu ID vrednosti prosledjene kao path variable")
	public Fakultet getFakultet(@PathVariable ("id") Integer id) {
		return fakultetRepository.getOne(id);
	}
	
	@GetMapping("fakultetNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju fakulteta na osnovu naziva fakulteta prosledjenog kao path variable")
	public Collection<Fakultet> getFakultetByNaziv(@PathVariable("naziv") String naziv){
		return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("fakultet")
	@ApiOperation(value = "Dodaje novi fakultet kog dobija u okviru request body")
	public ResponseEntity<Fakultet> insertFakultet(@RequestBody Fakultet fakultet){
		if(!fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Fakultet>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("fakultet")
	@ApiOperation(value = "Ažurira postojeći fakultet kog dobija u okviru request body")
	public ResponseEntity<Fakultet> updateFakultet(@RequestBody Fakultet fakultet){
		if(!fakultetRepository.existsById(fakultet.getId())) {
			
			return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
		}else {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
	}
	
	//@Transactional zakomentarisano jer nije radilo sa ovom anotacijom
	@DeleteMapping("fakultet/{id}")
	@ApiOperation(value = "Briše postojeći fakultet čiji ID uzima iz prosledjene path variable")
	public ResponseEntity<Fakultet> deleteFakultet(@PathVariable("id") Integer id){
if(!fakultetRepository.existsById(id)) {
			return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
		}else {
			
			jdbcTemplate.execute("delete from departman where fakultet = " + id);
			fakultetRepository.deleteById(id);
			
			if(id == -100) {
				jdbcTemplate.execute("insert into \"fakultet\"(\"id\",\"naziv\",\"sediste\") values(-100,'Naziv test','Sediste test')");
			}
			
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
	}

	@GetMapping("fakultetSediste/{sediste}")
	@ApiOperation(value = "Vraća kolekciju fakulteta na osnovu sedišta fakulteta prosledjenog kao path variable")
	public Collection<Fakultet> getFakultetBySediste(@PathVariable("sediste") String sediste){
		return fakultetRepository.findBySedisteContainingIgnoreCase(sediste);
	}
	
}
