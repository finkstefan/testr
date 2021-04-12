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
//import rva.jpa.Fakultet;
import rva.jpa.Status;
//import rva.repository.FakultetRepository;
import rva.repository.StatusRepository;

@CrossOrigin
@RestController
@Api(tags = {"Status CRUD operacije"})
public class StatusRestController {

	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("status")
	@ApiOperation(value = "Vraća sve statuse iz baze podataka")
	public Collection<Status> getStatusi(){
		return statusRepository.findAll();
	}
	
	
	@GetMapping("status/{id}")
	@ApiOperation(value = "Vraća status na osnovu ID vrednosti prosledjene kao path variable")
	public Status getStatus(@PathVariable ("id") Integer id) {
		return statusRepository.getOne(id);
	}
	
	@GetMapping("statusNaziv/{naziv}")
	@ApiOperation(value = "Vraća status na osnovu naziva statusa prosledjenog kao path variable")
	public Collection<Status> getStatusByNaziv(@PathVariable("naziv") String naziv){
		return statusRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("status")
	@ApiOperation(value = "Dodaje novi status kog dobija u okviru request body")
	public ResponseEntity<Status> insertStatus(@RequestBody Status status){
		if(!statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			
			return new ResponseEntity<Status>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("status")
	@ApiOperation(value = "Ažurira postojeći status kog dobija u okviru request body")
	public ResponseEntity<Status> updateStatus(@RequestBody Status status){
if(!statusRepository.existsById(status.getId())) {
			
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
		}else {
			statusRepository.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
		}
	}
	
	//@Transactional zakomentarisano jer nije radilo sa ovom anotacijom
	@DeleteMapping("status/{id}")
	@ApiOperation(value = "Briše postojeći status čiju ID vrednost uzima iz path variable")
	public ResponseEntity<Status> deleteStatus(@PathVariable("id") Integer id){
if(!statusRepository.existsById(id)) {
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
		}else {
			
			jdbcTemplate.execute("delete from student where status = " + id);
			statusRepository.deleteById(id);
			
			if(id == -100) {
				jdbcTemplate.execute("insert into \"status\"(\"id\",\"naziv\",\"oznaka\") values(-100,'NazTest','OznTest')");
			}
			
			return new ResponseEntity<Status>(HttpStatus.OK);
		}
	}
	@GetMapping("statusOznaka/{oznaka}")
	@ApiOperation(value = "Vraća kolekciju statusa na osnovu oznake statusa koju uzima iz path variable")
	public Collection<Status> getStatusByOznaka(@PathVariable("oznaka") String oznaka){
		return statusRepository.findByOznakaContainingIgnoreCase(oznaka);
	}
}
