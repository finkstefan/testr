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
import rva.jpa.Departman;
import rva.jpa.Fakultet;
import rva.repository.DepartmanRepository;
import rva.repository.FakultetRepository;


@CrossOrigin
@RestController
@Api(tags = {"Departman CRUD operacije"})
public class DepartmanRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DepartmanRepository departmanRepository;
	
	@Autowired
	private FakultetRepository fakultetRepository;
	
	@GetMapping("departman")
	@ApiOperation(value = "Vraća sve departmane iz baze podataka")
	public Collection<Departman> getDepartmani(){
		return departmanRepository.findAll();
	}
	
	
	@GetMapping("departman/{id}")
	@ApiOperation(value = "Vraća departman na osnovu ID vrednosti prosledjene kao path variable")
	public Departman getDepartman(@PathVariable ("id") Integer id) {
		return departmanRepository.getOne(id);
	}
	
	@GetMapping("departmanNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju departmana na osnovu naziva departmana prosledjenog kao path variable")
	public Collection<Departman> getDepartmanByNaziv(@PathVariable("naziv") String naziv){
		return departmanRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("departman")
	@ApiOperation(value = "Dodaje novi departman koji dobija u okviru request body")
	public ResponseEntity<Departman> insertDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Departman>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("departman")
	@ApiOperation(value = "Ažurira postojeći departman koji dobija u okviru request body")
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) {
			
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
		}
		else {
			departmanRepository.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
	}
	
	//@Transactional zakomentarisano jer nije radilo sa ovom anotacijom
	@DeleteMapping("departman/{id}")
	@ApiOperation(value = "Briše postojeći departman sa ID vrednošću iz path variable")
	public ResponseEntity<Departman> deleteDepartman(@PathVariable ("id") Integer id){
if(departmanRepository.existsById(id)) {
			
	
			departmanRepository.deleteById(id);
			jdbcTemplate.execute("delete from student where departman="+id);
			
			if(id==-100) {
				jdbcTemplate.execute("insert into \"departman\"(\"id\",\"naziv\",\"oznaka\",\"fakultet\") values(-100,'testdep','testozn',1) ");
			}
			
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("departmanByFakultet/{id}")
	@ApiOperation(value = "Vraća kolekciju departmana na osnovu fakulteta kom pripadaju")
	public Collection<Departman> findDepartmanByFakultet(@PathVariable("id") Integer id){
		Fakultet f = fakultetRepository.getOne(id);
		
		return departmanRepository.findByFakultet(f);
	}
	
	@GetMapping("departmanByOznaka/{oznaka}")
	@ApiOperation(value = "Vraća departman na osnovu oznake departmana koja je prosledjena kao path variable")
	public Collection<Departman> getDepartmanByOznaka(@PathVariable("oznaka") String oznaka){
		return departmanRepository.findByOznakaContainingIgnoreCase(oznaka);
	}
}
