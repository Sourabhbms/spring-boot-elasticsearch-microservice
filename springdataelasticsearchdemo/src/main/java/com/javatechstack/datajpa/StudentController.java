package com.javatechstack.datajpa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository repository;
	
	
	
	
	@PostMapping("/student/add")
	public Student addStudent(@RequestBody Student student ){
		return repository.save(student);
	}
	
	@GetMapping("/student/all")
	public List<Student> getStudents(){
		 Iterator<Student> iterator= repository.findAll().iterator();
		 List<Student> students=new ArrayList<Student>();
		 while(iterator.hasNext()){
			 students.add(iterator.next());
		 }
		 return students;
	}
	

	@GetMapping("/student/{id}")
	public Optional<Student> getStudent(@PathVariable Integer id){
		return repository.findById(id);
	}
	
	
	@PutMapping("/student/{id}")
	   public Student updateStudent(@PathVariable Integer id,@RequestBody Student student){
		   Optional<Student> std= repository.findById(id);
		   if(std.isPresent()){
			   Student s=std.get();
			   s.setName(student.getName());
		   return repository.save(s);
		   }
		   else
			   return null;
	   }
	
	@DeleteMapping("/student/{id}")
	   public String deleteStudent(@PathVariable Integer id){
		  repository.deleteById(id);
		  return "Document Deleted";
	   }

	


		

}
