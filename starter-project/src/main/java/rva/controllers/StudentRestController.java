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

import rva.jpa.Status;
import rva.jpa.Student;
import rva.repository.DepartmanRepository;

import rva.repository.StatusRepository;
import rva.repository.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD operacije"})
public class StudentRestController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private DepartmanRepository departmanRepository;
	
	@Autowired 
	private StatusRepository statusRepository;
	
	@GetMapping("student")
	@ApiOperation(value = "Vraća sve studente iz baze podataka")
	public Collection<Student> getStudenti(){
		return studentRepository.findAll();
	}
	
	
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraća studenta na osnovu ID vrednosti prosledjene kao path variable")
	public Student getStudent(@PathVariable ("id") Integer id) {
		return studentRepository.getOne(id);
	}
	
	@GetMapping("studentNaziv/{ime}")
	@ApiOperation(value = "Vraća kolekciju studenata po nazivu studenta prosledjenog kao path variable")
	public Collection<Student> getStudentByNaziv(@PathVariable("ime") String ime){
		return studentRepository.findByImeContainingIgnoreCase(ime);
	}
	
	@PostMapping("student")
	@ApiOperation(value = "Dodaje novog studenta kog dobija u okviru request body")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			
			return new ResponseEntity<Student>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Student>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("student")
	@ApiOperation(value = "Ažurira postojećeg studenta kog dobija u okviru request body")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student){
if(!studentRepository.existsById(student.getId())) {
			
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}else {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
	}
	
	//@Transactional zakomentarisano jer nije radilo sa ovom anotacijom
	@DeleteMapping("student/{id}")
	@ApiOperation(value = "Briše postojećeg studenta čiju ID vrednost uzima iz path variable")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id){
if(!studentRepository.existsById(id)) {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}else {
			
			studentRepository.deleteById(id);
			
			if(id == -100) {
				
				jdbcTemplate.execute("INSERT INTO \"student\"(\"id\", \"ime\", \"prezime\",\"broj_indeksa\",\"status\",\"departman\") VALUES (-100, 'TestIme','TestPrez','777669',4,3)");
			
			}
			
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
	}
	
	@GetMapping("studentByDepartman/{id}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu departmana kom pripadaju, prosledjenog kao path variable")
	public Collection<Student> findStudentsByDepartman(@PathVariable("id") Integer id){
		Departman d = departmanRepository.getOne(id);
		
		return studentRepository.findByDepartman(d);
	}
	
	@GetMapping("studentByStatus/{id}")
	@ApiOperation(value = "Vraća kolekciju studenata na osnovu statusa koji imaju, prosledjenog u okviru path variable")
	public Collection<Student> findStudentsByStatus(@PathVariable("id") Integer id){
		Status s = statusRepository.getOne(id);
		
		return studentRepository.findByStatus(s);
	}
	
	@GetMapping("studentBrInd/{brojindeksa}")
	@ApiOperation(value = "Vraća studenta na osnovu broja indeksa studenta, prosledjenog u okviru path variable")
	public Collection<Student> getStudentByBrInd(@PathVariable("brojindeksa") String brojindeksa){
		return studentRepository.findByBrojIndeksa(brojindeksa);
	}
}
